package com.brian.common.constant;

/**
 * @author : brian
 * @since 0.1
 */
public class Const {

    public static final String FEIGN_API_PREFIX = "/feign";

    public static final String AUTHORIZATION = "Authorization";

    public static final String REDIS_TOKEN_PREFIX = "token:";
    public static final String REDIS_FEED_PREFIX = "feed:";
    /**
     * read:{blogId}
     */
    public static final String REDIS_READ_PREFIX = "read:";
    /**
     * blog:{blogId}
     */
    public static final String REDIS_BLOG_PREFIX = "blog:";
    public static final String REDIS_RANK_COLLECT = "rank:blog:collect";
    public static final String UNKNOWN = "unknown";
    public static final String NORMAL_VISITORS = "normal_visitor";
    public static final String VISITOR_AREA = "visitor_area";
    public static final String BLOG_VIEWS_COUNT = "blog_views_count";
    public static final String UNIQUE_VISITOR = "unique_visitor";


}
