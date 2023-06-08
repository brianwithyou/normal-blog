package com.brian.user.api.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Brian
 * @date 2023/6/8
 **/
@Data
public class UserStatisticDTO {

    /**
     * contribute count
     */
    private Long count;

    private LocalDate date;

}
