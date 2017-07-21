package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 时间卡
 */
@Setter
@Getter
public class Timecard {

    private Long id;
    private Date beginDate;//时间卡开始日期
    private Date endDate;//时间卡结束日期
    private Employee employee;//时间卡所属用户

    private int state = STATE_NO;

    public static final int STATE_SUBMIT = 0;//已经提交
    public static final int STATE_CUR = 1;//当前有效时间卡
    public static final int STATE_NO = 2;//当前无时间卡

    public String getStateShow() {
        if (this.state == STATE_NO) {
            return "无时间卡";
        }
        if (this.state == STATE_CUR) {
            return "有效时间卡";
        }
        if (this.state == STATE_SUBMIT) {
            return "已经提交";
        }
        return null;
    }

}
