package com.brian.normal.common.util;

import java.util.UUID;

/**
 * @author : brian
 * @since 0.1
 */
public class IdUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
