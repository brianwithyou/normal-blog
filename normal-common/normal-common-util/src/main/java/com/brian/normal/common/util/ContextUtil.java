package com.brian.normal.common.util;

/**
 * @author : brian
 * @since 0.1
 */
public class ContextUtil {

    public static ThreadLocal<String> requestIdMap = new ThreadLocal<>();

    public static String requestId() {
        if (requestIdMap.get() == null) {
            String requestId = IdUtil.uuid();
            requestIdMap.set(requestId);
            return requestId;
        }
        return requestIdMap.get();
    }

}
