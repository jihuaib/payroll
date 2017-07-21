package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 邮箱验证码
 */
@Getter
@Setter
public class VerificationEmailCode {
    private Long id;
    private Long user;//所属用户
    private String key;
    private String email;//绑定邮箱
    private Date date;
}
