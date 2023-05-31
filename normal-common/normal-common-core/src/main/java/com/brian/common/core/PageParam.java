package com.brian.common.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : brian
 * @since 0.1
 */
@ApiModel("分页参数")
@Data
public class PageParam implements Serializable {

    private static final Integer PAGE_NUM = 1;
    private static final Integer PAGE_SIZE = 10;

    @ApiModelProperty(value = "页码，从 1 开始", required = true,example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum = PAGE_NUM;

    @ApiModelProperty(value = "每页条数，最大值为100", required = true, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为1")
    @Max(value = 1000, message = "每页条数最大值为100")
    private Integer pageSize = PAGE_SIZE;

}