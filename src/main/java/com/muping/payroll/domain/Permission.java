package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 权限相关
 */
@Setter
@Getter
@ToString
public class Permission {

    private Long id;
    private String expression;//权限表达式
    private String des;//描述

}
