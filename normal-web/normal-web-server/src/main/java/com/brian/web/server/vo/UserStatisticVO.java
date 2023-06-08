package com.brian.web.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Brian
 * @date 2023/6/8
 **/
@Data
public class UserStatisticVO {

    private Long count;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private LocalDateTime date;
}
