package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 项目
 */
@Getter
@Setter
public class Project {

    public static final int STATE_NO=0;//空闲项目
    public static final int STATE_USE=1;//项目已经被接管
    public static final int STATE_COMPLETE=2;//项目完成

    /**
     * 管理员设置
     */
    private Long id;
    private String name;
    private int count;//项目需要时间

    /**
     * 员工设置
     */
    private int state=STATE_NO;//状态
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;//项目开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;//项目结束时间
    private Employee employee;//项目接管人

    public String getStateShow(){
        if(this.state==STATE_NO){
            return "空闲项目";
        }
        if(this.state==STATE_USE){
            return "接管项目";
        }
        if(this.state==STATE_COMPLETE){
            return "完成项目";
        }
        return null;
    }
}
