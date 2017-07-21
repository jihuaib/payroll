package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderQueryObject extends QueryObject{

    private Long employeeId=-1L;//查询用户的订单列表（-1查询所有用户）
}
