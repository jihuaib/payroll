package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录信息
 */
@Getter
@Setter
public class LoginInfo {

    public static final int STATE_DELETE=0;//删除状态
    public static final int STATE_NORMAL=1;//正常状态

    public static final int USERTYPE_ADMIN=0;//管理员
    public static final int USERTYPE_SALARY=1;//受薪员工
    public static final int USERTYPE_HOUR=2;//小时员工
    public static final int USERTYPE_ENTRUST=3;//委托员工

    private Long id;
    private String userName;
    private String password;

    private int state;//状态
    private int userType;//用户类型
}
