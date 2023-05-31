package com.brian.search.server.vo;

import com.brian.common.core.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchVO extends PageParam {

    private String keyword;


}
