package com.muping.payroll.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * JSON消息提示
 */
@Getter
@Setter
public class JSONResult {
    private boolean success=true;
    private String message;
}
