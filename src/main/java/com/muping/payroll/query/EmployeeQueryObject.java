package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeQueryObject extends QueryObject {

    private int userType=-1;//-1表示查询所有
    private int state=-1;//-1表示查询所有
    private int payMethod=-1;//-1表示查询所有
    private String keyword="";

    public String getRealKeyword(){
        if(keyword.equals("")){
            return null;
        }
        return keyword;
    }
}
