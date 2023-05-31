package com.brian.common.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class ScrollResult<T> {
    private List<T> list;
    /**
     * 相当于滚动分页的lastId
     */
    private Long minTime;
    /**
     * pageSize
     */
    private Integer offset;
}
