package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectQueryObject extends QueryObject{

    private String keyword="";
    private String count;
    private int state=-1;
    private Long employeeId=-1L;

    public String getRealKeyword(){
        if(keyword.equals("")){
            return null;
        }
        return keyword;
    }

    public int getRealCount(){
        try {
            return Integer.parseInt(count);
        }catch (Exception e){
            return -1;
        }
    }
}
