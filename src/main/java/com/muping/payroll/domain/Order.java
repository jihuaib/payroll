package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 订单相关
 */
@Getter
@Setter
public class Order {

    private Long id;
    private Employee employee;//下单员工
    private String address;//地址
    private String productName;//产品姓名
    private String clientName;//客户姓名

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;//下单日期
}
