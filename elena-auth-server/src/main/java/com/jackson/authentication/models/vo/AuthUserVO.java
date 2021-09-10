package com.jackson.authentication.models.vo;

import lombok.Data;

/**
 * @author JieXing
 * @Description: 用户表vo
 * @date 2021/8/13
 * @time 22:33
 */
@Data
public class AuthUserVO {
    private String userId;

    private String accountNum;

    private String password;

    private String name;

    private String phone;
}
