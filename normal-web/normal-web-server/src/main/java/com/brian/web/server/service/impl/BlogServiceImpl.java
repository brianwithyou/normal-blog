package com.brian.web.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.common.constant.exception.GlobalErrorCodeEnum;
import com.brian.common.core.Result;
import com.brian.common.core.ScrollResult;
import com.brian.normal.common.util.RedisUtil;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.convert.BlogConvert;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.entity.Collect;
import com.brian.web.server.mapper.BlogMapper;
import com.brian.web.server.service.BlogService;
import com.brian.web.server.service.CollectService;
import com.brian.web.server.util.ContextUtil;
import com.brian.web.server.vo.BlogVO;
import com.brian.web.server.vo.UserVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.brian.common.constant.Const.*;


/**
* @author brian
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    private CollectService collectService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result<?> saveBlog(BlogVO blog) {
        UserDTO currentUser = ContextUtil.getCurrentUser();
        // 更新
        if (blog.getId() == null) {
            return Result.fail(GlobalErrorCodeEnum.BLOG_NOT_FOUND);
        }

        Blog blogEntity = BlogConvert.INSTANCE.convert(blog);
        if (StrUtil.isNotBlank(blog.getCover())) {
            blogEntity.setCover(blog.getCover());
        }
        // 新增
        LocalDateTime now = LocalDateTime.now();
        if (blog.getId() == null) {
            blogEntity.setCreateTime(now);
            blogEntity.setUpdateTime(now);
        } else {
            blogEntity.setUpdateTime(now);
        }
        boolean save = updateById(blogEntity);

        if (!save) {
            return Result.fail();
        }
        // 更新缓存
        delCache(blog.getId());

        // 发送到粉丝的收件箱 粉丝量不大时，写模式， 某大V粉丝量巨大，则写模式推送到活跃用户的收件箱，也推送到大V的发件箱
        // 非活跃用户上线时可以读取大V的发件箱

        // 1. 查询用户的粉丝
//        List<FollowerDTO> followers = followerFeignClient.listFollowers(currentUser.getId());
//        for (FollowerDTO followerDto : followers) {
//            // 收件箱, redis的zset实现，通过时间作为score排序
//            String feedKey = "feed:" + followerDto.getFollowerId();
//            redisUtil.zset(feedKey, String.valueOf(blogEntity.getId()), System.currentTimeMillis());
//        }

        // 2. 推送到粉丝的收件箱

        return Result.success();
    }

    @Override
    public Result<ScrollResult<BlogVO>> feed(Long max, Integer offset) {

        UserDTO currentUser = ContextUtil.getCurrentUser();
        // 收件箱模式
        String blogFeedKey = REDIS_FEED_PREFIX + currentUser.getId();
        // key -> blogId
        Set<ZSetOperations.TypedTuple<Object>> blogs = redisUtil.getZsetRankByScore(blogFeedKey, 0, max, offset, 2);

        List<Long> ids = new ArrayList<>();
        // 本次查询的最后一篇博客的时间
        long minTime = 0;
        // minTime下，同一时刻有多少博客数量，下次直接偏移过去
        int minTimeBlogCount = 1;
        for (ZSetOperations.TypedTuple<Object> blogId : blogs) {
            long id = Long.parseLong(String.valueOf(blogId.getValue()));
            ids.add(id);

            long publishTime = Objects.requireNonNull(blogId.getScore()).longValue();
            if (publishTime == minTime) {
                minTimeBlogCount++;
            } else {
                minTime = publishTime;
                minTimeBlogCount = 1;
            }

        }
        if (CollectionUtil.isEmpty(ids)) {
            return Result.success();
        }
        List<Blog> blogEntities = listByIds(ids);
        List<BlogVO> blogVOList = BlogConvert.INSTANCE.convertVO(blogEntities);
        ScrollResult<BlogVO> blogScrollResult = new ScrollResult<>();
        blogScrollResult.setList(blogVOList);
        blogScrollResult.setMinTime(minTime);
        blogScrollResult.setOffset(minTimeBlogCount);
        return Result.success(blogScrollResult);
    }

    @Override
    public Result<IPage<Blog>> list(BlogVO blog) {

        Page<Blog> page = new Page<>();
        page.setCurrent(blog.getPageNum());
        page.setSize(blog.getPageSize());

        IPage<Blog> pageData = this.page(page, new QueryWrapper<Blog>()
                .ne("status", 0)
                .orderByDesc("create_time"));

        return Result.success(pageData);
    }
    @Override
    public Result<?> getBlog(Long id) {

        Object cachedBlog = redisUtil.get(REDIS_BLOG_PREFIX + id);
        this.read(id);

        if (ObjectUtil.isNotNull(cachedBlog)) {
            String blogStr = JSONUtil.toJsonStr(cachedBlog);
            BlogVO blogVO = JSONUtil.toBean(blogStr, BlogVO.class);
            blogVO.setReadCount(blogVO.getReadCount() + 1);
            return Result.success(blogVO);
        }
        Blog blogDetail = getById(id);
        BlogVO blogVO = BlogConvert.INSTANCE.convertVO(blogDetail);
        blogVO.setCopyright("本文为原创文章，请注明来源");
        if (blogVO.getUid() != null && StrUtil.isBlank(blogVO.getAuthor())) {
            UserDTO userById = userFeignClient.getUserById(blogVO.getUid());
            blogVO.setAuthor(userById.getUsername());
        }

        redisUtil.set(REDIS_BLOG_PREFIX + id, blogVO, 300);

        return Result.success(blogVO);
    }

    @Override
    public Result<?> read(Long id) {

        // 更新缓存
        Object cachedBlog = redisUtil.get(REDIS_BLOG_PREFIX + id);
        if (ObjectUtil.isNotNull(cachedBlog)) {
            String blogStr = JSONUtil.toJsonStr(cachedBlog);
            BlogVO blogVO = JSONUtil.toBean(blogStr, BlogVO.class);
            Long readCount = blogVO.getReadCount();
            blogVO.setReadCount(++readCount);

            redisUtil.set(REDIS_BLOG_PREFIX + id, blogVO, 300);
        }
        // 更新数据库
        Blog byId = getById(id);
        Long readCount = byId.getReadCount();
        byId.setReadCount(++readCount);
        updateById(byId);

        return Result.success();
    }

    @Override
    public Result<?> delete(BlogVO blog) {

        if (blog.getId() == null) {
            return Result.fail(GlobalErrorCodeEnum.BLOG_NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now();
        Blog blogEntity = new Blog();
        blogEntity.setId(blog.getId());
        blogEntity.setStatus(0);
        blogEntity.setUpdateTime(now);
        // 更新缓存
        delCache(blog.getId());
        return Result.success(updateById(blogEntity));
    }

    @Override
    public boolean create(BlogVO blog) {

        UserDTO currentUser = ContextUtil.getCurrentUser();

        LocalDateTime now = LocalDateTime.now();
        Blog blogEntity = BlogConvert.INSTANCE.convert(blog);
        blogEntity.setUid(currentUser.getId());
        blogEntity.setCover(blog.getCover());
        blogEntity.setAuthor(currentUser.getUsername());
        blogEntity.setUpdateTime(now);
        blogEntity.setCreateTime(now);
        return save(blogEntity);
    }

    @Override
    public List<BlogVO> dailyHotBlogs() {

        // 内存不够，只存id
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(REDIS_RANK_COLLECT, 0, -1);

        if (CollectionUtil.isNotEmpty(typedTuples)) {
            JSONArray blogIds = JSONUtil.parseArray(JSONUtil.toJsonStr(typedTuples));
            List<Blog> list = list(new QueryWrapper<Blog>().in("id", blogIds));
            return BlogConvert.INSTANCE.convertVO(list);
        }
        List<Blog> list = list(new QueryWrapper<Blog>().ne("status", 0).orderByDesc("collect_count"));
        // 加入redis
        list.forEach(item -> {
            redisTemplate.opsForZSet().add(REDIS_RANK_COLLECT, String.valueOf(item.getId()), item.getCollectCount());
        });

        return BlogConvert.INSTANCE.convertVO(list);
    }

    @Override
    public List<BlogVO> totalCollectRank() {

        // 内存不够，只存id
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(REDIS_RANK_COLLECT, 0, 10);

        if (CollectionUtil.isNotEmpty(typedTuples)) {
            JSONArray blogIdsArray = JSONUtil.parseArray(JSONUtil.toJsonStr(typedTuples));
//            long[] longs = blogIdsArray.stream().mapToLong(item -> ((JSONObject) item).getLong("value")).toArray();
            List<Long> blogIds = blogIdsArray.stream().map(item -> ((JSONObject) item).getLong("value")).collect(Collectors.toList());

            List<String> collect = blogIds.stream().map(String::valueOf).collect(Collectors.toList());
            // 根据id自定义排序
            String format = String.format(" field(id,%s) ", String.join(",", collect));
            List<Blog> list = list(new QueryWrapper<Blog>()
                    .in("id", blogIds)
                    .orderByAsc( format));

            return BlogConvert.INSTANCE.convertVO(list);
        }
        List<Blog> list = list(new QueryWrapper<Blog>()
                .ne("status", 0)
                .orderByDesc("collect_count").last(" limit 10"));
        // 加入redis
        list.forEach(item -> {
            redisTemplate.opsForZSet().add(REDIS_RANK_COLLECT, String.valueOf(item.getId()), item.getCollectCount());
        });

        return BlogConvert.INSTANCE.convertVO(list);
    }

    @Override
    public boolean collect(Long blogId, Integer flag) {

        Long userId = ContextUtil.getCurrentUser().getId();
        Blog byId = getById(blogId);

        byId.setCollectCount((flag > 0 ? 1 : -1) + byId.getCollectCount());
        if (byId.getCollectCount() < 0) {
            byId.setCollectCount(0L);
        }
        updateById(byId);

        Collect collect = collectService.getOne(new QueryWrapper<Collect>()
                .eq("user_id", userId)
                .eq("blog_id", blogId));
        if (collect != null) {
            collect.setStatus(flag);
            collectService.updateById(collect);
        } else {
            collect = new Collect();
            collect.setUserId(userId);
            collect.setBlogId(blogId);
            collect.setStatus(flag);
            collectService.saveOrUpdate(collect);
        }

        redisUtil.set(REDIS_BLOG_PREFIX + byId.getId(), BlogConvert.INSTANCE.convertVO(byId), 300);

        // 数据同步方案，1. 双写， 2. 定时任务  3.异步双写(mq) 4. canal读取
        redisUtil.zIncrementScore(REDIS_RANK_COLLECT , blogId, flag > 0 ? 1 : -1);
        return true;
    }

    @Override
    public List<UserVO> totalPayRank() {
        return null;
    }

    private void delCache(Long id) {
        redisUtil.del(REDIS_BLOG_PREFIX + id);
        Long remove = redisTemplate.opsForZSet().remove(REDIS_RANK_COLLECT, id);
    }
}




