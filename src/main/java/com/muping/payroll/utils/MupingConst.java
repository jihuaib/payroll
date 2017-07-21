package com.muping.payroll.utils;

import java.math.BigDecimal;

/**
 * 存储项目中的常量
 */
public class MupingConst {

    /**
     * 初始管理员的账号和密码
     */
    public static final String DEFAULT_ADMIN_USERNAME="admin";
    public static final String DEFAULT_ADMIN_PASSWORD="admin";

    /**
     * 受薪用户初始月薪
     */
    public static final BigDecimal INIT_MONTH_SALARY=new BigDecimal("7000.00");

    /**
     * 管理员的初始月薪
     */
    public static final BigDecimal INIT_MONTH_ADMIN_SALARY=new BigDecimal("10000.00");

    /**
     * 小时工每小时的初始工资
     */
    public static final BigDecimal INIT_HOUR_SALARY=new BigDecimal("100.00");

    /**
     * 委托员工的每个订单的钱
     */
    public static final BigDecimal INIT_ENTRUST_SALARY=new BigDecimal("400.00");

    /**
     * 邮件失效时间
     * 为了测试方便 60s
     */
    public static final int EMAIL_LOSE=600;

    /**
     * 每周必须工作多少小时才有工资
     */
    public static int MIN_WORK_HOURS=5;

}
