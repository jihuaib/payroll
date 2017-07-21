package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 站内信
 */
@Getter
@Setter
public class Message {

    private Long id;
    private LoginInfo send;//发送人
    private LoginInfo receive;//接收人
    private String title;//标题
    private String content;//内容
    private Date date;//发送时间
}
