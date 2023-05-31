package com.brian.user.api.dto;

import lombok.Data;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class FollowerDTO {

    private Long id;

    private Long userId;

    private Long followerId;
}
