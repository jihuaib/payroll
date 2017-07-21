package com.muping.payroll.utils;

import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Permission;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户上下文
 */
public class UserContext {

    public static final String USER_IN_SESSION="USER_IN_SESSION";
    public static final String USER_PERMISSIONS="USER_PERMISSIONS";
    public static final String USER_CODE="USER_CODE";

    /**
     * 获取session
     * @return
     */
    private static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }

    /**
     * 存储登录用户
     * @param loginInfo
     */
    public static void putCurrentUser(LoginInfo loginInfo){
        HttpSession session = getSession();
        session.setAttribute(USER_IN_SESSION,loginInfo);
    }

    /**
     * 获取当前登录对象
     * @return
     */
    public static LoginInfo getCurrentUser(){
        HttpSession session = getSession();
        return (LoginInfo) session.getAttribute(USER_IN_SESSION);
    }

    /**
     * 存储登录用的权限
     * @param permissions
     */
    public static void putCurrentUserPermissions(List<Permission> permissions){
        getSession().setAttribute(USER_PERMISSIONS,permissions);
    }

    /**
     * 获取当前登录用户的权限
     * @return
     */
    public static List<Permission> getCurrentUserPermissions(){
        HttpSession session = getSession();
        return (List<Permission>) session.getAttribute(USER_PERMISSIONS);
    }

    /**
     * 存储code
     */
    public static void putCode(String code){
        getSession().setAttribute(USER_CODE,code);
    }

    /**
     * 获取code
     * @return
     */
    public static String getCode(){
        HttpSession session = getSession();
        return (String) session.getAttribute(USER_CODE);
    }
}
