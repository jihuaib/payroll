package com.muping.payroll.utils;

import java.lang.reflect.Method;

/**
 * 权限工具类
 */
public class PermissionUtil {

    /**
     * 构造权限表达式
     * @param method
     * @return
     */
    public static String buildExpression(Method method){
        //获取该方法的父类名
        StringBuilder sb = new StringBuilder(80);
        String className = method.getDeclaringClass().getName();
        sb.append(className);
        sb.append(":");
        sb.append(method.getName());
        return sb.toString();
    }
}
