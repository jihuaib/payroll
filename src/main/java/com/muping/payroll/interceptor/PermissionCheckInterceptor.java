package com.muping.payroll.interceptor;

import com.alibaba.fastjson.JSON;
import com.muping.payroll.domain.Permission;
import com.muping.payroll.utils.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 权限检查
 */
public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1-----判断是不是登录操作(login.do)
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        //是登录，直接放行
        if(handlerMethod.getMethod().getName().equals("login")||handlerMethod.getMethod().getName().equals("verificationEmailCode")){
            return true;
        }

        //2------判断用户有没有登录
        if(UserContext.getCurrentUser()==null){
            //没有登录,直接重定向的登录界面
            response.sendRedirect("/login.html");
            return false;
        }

        //3------判断是否需要权限
        RequiredPermission methodAnnotation = handlerMethod.getMethodAnnotation(RequiredPermission.class);
        if(methodAnnotation==null){
            //不需要权限，放行
            return true;
        }

        //4------需要权限
        //获取请求的权限表达式
        //获取请求的方法
        Method method = handlerMethod.getMethod();
        //拼接权限表达式
        String expression = PermissionUtil.buildExpression(method);
        //获取登录用户的权限
        List<Permission> currentUserPermissions = UserContext.getCurrentUserPermissions();
        for (Permission currentUserPermission : currentUserPermissions) {
            if(currentUserPermission.getExpression().equals(expression)){
                //有权限，放行
                return true;
            }
        }
        //没有操作权限
        //判断是不是ajax请求
        System.out.println(AJAXUtil.isAjaxRequest(request));
        if(AJAXUtil.isAjaxRequest(request)){
            //是ajax请求
            response.setContentType("text/json;charset=utf-8");
            JSONResult jsonResult = new JSONResult();
            jsonResult.setSuccess(false);
            jsonResult.setMessage("对不起，你没有操作权限！");
            response.getWriter().print(JSON.toJSONString(jsonResult));
            return false;
        }
        //不是ajax请求
        response.sendRedirect("/noPermission.html");
        return false;
    }
}
