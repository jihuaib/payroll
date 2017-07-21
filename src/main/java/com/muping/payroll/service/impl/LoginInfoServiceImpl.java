package com.muping.payroll.service.impl;

import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Permission;
import com.muping.payroll.mapper.LoginInfoMapper;
import com.muping.payroll.mapper.PermissionMapper;
import com.muping.payroll.service.ILoginInfoService;
import com.muping.payroll.utils.MD5;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public void login(String userName, String password,int state) {
        LoginInfo loginInfo=loginInfoMapper.queryByUserNameAndPassword(userName, MD5.encode(password),state);
        //登录失败
        if(loginInfo==null){
            throw new RuntimeException("用户名或者密码错误！");
        }
        //登录成功
        //存储到用户上下文
        UserContext.putCurrentUser(loginInfo);
        //获取登录用户的所有权限
        List<Permission> permissions=permissionMapper.queryAllPermissionsCurUser(loginInfo.getId());
        UserContext.putCurrentUserPermissions(permissions);

    }

    @Override
    public LoginInfo queryByUserName(String userName) {
        LoginInfo loginInfo=loginInfoMapper.queryByUserName(userName);
        if(loginInfo==null){
            //不存在
            throw new RuntimeException("用户名不存在！请重新输入！");
        }
        return loginInfo;
    }
}
