package com.muping.payroll.service;

import com.muping.payroll.domain.LoginInfo;

public interface ILoginInfoService {

    /**
     * 用户登录
     * @param userName
     * @param password
     */
    void login(String userName, String password,int state);

    /**
     * 更具用户名查找LiginInfo
     * @param userName
     * @return
     */
    LoginInfo queryByUserName(String userName);
}
