package com.brian.rbac.server.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class UserVO {

    private Long id;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 呢称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String idCardNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 0-男 1-女
     */
    private Integer gender;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 等级
     */
    private Integer vipLevel;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像链接
     */
    private String avatar;

    /**
     * 地址
     */
    private String area;

    /**
     * 地址 provinceCode-cityCode-districtCode-streetCode
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * status
     */
    private Integer status;

    /**
     * deleted 1-YES 0-NO
     */
    private Integer deleted;

    /**
     * 额外信息
     */
    private String extendInfo;

    /**
     * 最后登录时间
     */
    private Date lastLogin;
}
