package com.muping.payroll.utils;

import javax.servlet.http.HttpServletRequest;

public class AJAXUtil {

    public static boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }
}
