package com.brian.search.server.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.brian.common.core.Result;
import com.brian.search.api.entity.EsBlog;
import com.brian.search.server.mapper.BlogRepository;
import com.brian.search.server.service.SearchService;
import com.brian.web.api.BlogFeignClient;
import com.brian.web.api.dto.BlogDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : brian
 * @since 0.1
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private BlogRepository blogRepository;

    @Resource
    private BlogFeignClient blogFeignClient;
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Map<String, Object> search(Map<String, String> param) {

//        Iterable<EsBlog> all = blogRepository.findAll();
//        all.forEach(blog -> {
//            System.out.println(blog);
//        });
//        System.out.println(all);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(new MatchAllQueryBuilder()).build();
        searchQuery.addAggregation(AggregationBuilders.terms("blogCategory").field("category"));

        SearchHits<EsBlog> search = elasticsearchTemplate.search(searchQuery, EsBlog.class);
        List<SearchHit<EsBlog>> searchHits = search.getSearchHits();

//        List<SearchHit<BlogDTO>> searchHits = search.getSearchHits();
//        System.out.println(searchHits);

        Object aggregations = search.getAggregations().aggregations();
        System.out.println();


        searchHits.forEach(hit -> {
            EsBlog blog = hit.getContent();
            System.out.println(hit);
        });

        Map<String, Object> res = new HashMap<>();
        res.put("total", searchHits.size());
        res.put("rows", searchHits);

        return res;
    }

    @Override
    public Map<String, Object> search(String keyword, Integer pageNum, Integer pageSize) {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withPageable(PageRequest.of(pageNum, pageSize));

//        List<HighlightBuilder.Field> highlightFields = new ArrayList<>();
//        HighlightBuilder.Field title = new HighlightBuilder.Field("title").preTags("<em style='color:red'>").postTags("</em>");
//        HighlightBuilder.Field summaryField = new HighlightBuilder.Field("summary").preTags("<em style='color:red'>").postTags("</em>");

//        highlightFields.add(titleField);
//        highlightFields.add(summaryField);
//        HighlightBuilder.Field[] highlightFieldsAry = highlightFields.toArray(new HighlightBuilder
//                .Field[highlightFields.size()]);

        QueryStringQueryBuilder queryStrBuilder = new QueryStringQueryBuilder(keyword);
        queryStrBuilder.field("title", 0.75F).field("summary", 0.75F).field("content", 0.1F);

        nativeSearchQueryBuilder.withQuery(queryStrBuilder);
//        nativeSearchQueryBuilder.withHighlightFields(highlightFieldsAry);

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchRequest request = new SearchRequest();
        CountRequest countRequest = new CountRequest();
        countRequest.indices("blog");
        request.indices("blog");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        request.source(searchSourceBuilder);
//        boolQueryBuilder.must(QueryBuilders.matchQuery("base_info_id", baseInfo.getId()));
        int shouldCount = 0;
        if (!StrUtil.isEmpty(keyword)) {
            shouldCount++;
            boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("title", keyword));
            boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("summary", keyword));
            boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("content", keyword));
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", keyword));
            boolQueryBuilder.should(QueryBuilders.matchQuery("summary", keyword));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", keyword));
        }
        boolQueryBuilder.minimumShouldMatch(shouldCount);
//        countRequest.query(boolQueryBuilder);
        //设置分页 from:页码，（当前页-1）*每页条数
        searchSourceBuilder.from(pageSize * (pageNum - 1));
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.query(boolQueryBuilder);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<span style='color: red'>");
//        highlightBuilder.preTags("<span style='background-image: linear-gradient(90deg,#ff0000 0,#2575fc);\n" +
        highlightBuilder.preTags("<span style='background-image: linear-gradient(90deg,#ff0000 0,#6a11cb);\n" +
                "    -webkit-background-clip: text;\n" +
                "    -webkit-text-fill-color: transparent;'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("title");
        highlightBuilder.field("summary");
        highlightBuilder.field("content");
        searchSourceBuilder.highlighter(highlightBuilder);

        SearchResponse searchResponse = null;
        CountResponse countResponse = null;
        List<EsBlog> content = new ArrayList<>();
        long totalCount = 0;

        try {
            countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            totalCount = countResponse.getCount();
            searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            org.elasticsearch.search.SearchHit[] searchHits = searchResponse.getHits().getHits();
//            SearchHit[] searchHits = (SearchHit[]) hits;
            for (org.elasticsearch.search.SearchHit searchHit : searchHits) {
                //原理就是用es自动查找出来的highlight字段值替换正常检索出来的值
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                HighlightField highlightTitle = highlightFields.get("title");//注意是数组
                Map<String, Object> sourceMap = searchHit.getSourceAsMap();
                if(highlightTitle != null){
                    Text[] fragments = highlightTitle.getFragments();
                    if(fragments != null && fragments.length > 0){
                        //替换(fragment[0]是Text类型的)
                        sourceMap.replace("title", fragments[0].toString());
                    }
                }
                HighlightField highlightContent = highlightFields.get("content");
                if(highlightContent != null){
                    Text[] fragments = highlightContent.getFragments();
                    if(fragments != null && fragments.length > 0){
                        //替换(fragment[0]是Text类型的)
                        StringBuilder contentStr = new StringBuilder();
                        for (Text fragment : fragments) {
                            contentStr.append(fragment);
                        }
                        sourceMap.replace("content", contentStr);
                    }
                }
                EsBlog esBlog = JSONUtil.toBean(JSONUtil.toJsonStr(sourceMap), EsBlog.class);
                content.add(esBlog);
            }
        } catch (Exception e) {
            log.error("搜索失败，搜索条件: {}, error: {}", keyword, e.getMessage());
        }

        Map<String, Object> res = new HashMap<>();
        res.put("content", content);
//        res.put("pageNum", searchHits.get);
        res.put("total", totalCount);
//        res.put("pageNum", searchHits.getTotalHits());
        res.put("pageSize", pageSize);
        return res;
    }

    @Override
    public BlogDTO insertById(long blogId) throws IOException {
        BlogDTO blog = blogFeignClient.getById(blogId);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("blog").source(JSONUtil.toJsonStr(JSONUtil.parseObj(blog)), XContentType.JSON));
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return blog;
    }

    @Override
    public void initEsBlogs() throws IOException {

        // 写入数据
        List<BlogDTO> blogs = blogFeignClient.list();
        BulkRequest bulkRequest = new BulkRequest();
        blogs.forEach(blog -> {
            bulkRequest.add(new IndexRequest("blog").source(JSONUtil.toJsonStr(JSONUtil.parseObj(blog)), XContentType.JSON));
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("es data init success.");

    }

    public Result<?> list() {
        Iterable<EsBlog> all = blogRepository.findAll();
        return Result.success(all);
    }
    public Result<?> deleteById(Long id) throws IOException {
        DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest("blog");
        deleteRequest.setQuery(new BoolQueryBuilder().must(new TermQueryBuilder("id", id)));
        BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
        return Result.success(bulkByScrollResponse);
    }

    public Result<?> deleteAll() throws IOException {
        DeleteRequest deleteRequest=new DeleteRequest();
        deleteRequest.index("blog");

        DeleteIndexRequest request = new DeleteIndexRequest("blog");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);


//        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return Result.success(delete);
    }

}
