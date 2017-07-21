package com.muping.payroll.vo;

import com.muping.payroll.domain.LoginInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReportVo {

    private String userName;
    private int userType;
    private BigDecimal totalSalary=BigDecimal.ZERO;//总计薪水
    private int totalOrders;//总计订单数
    private int totalCount;//总计工作小时数

    public String getUserTypeShow(){
        if(this.userType== LoginInfo.USERTYPE_ADMIN){
            return "管理员";
        }
        if(this.userType== LoginInfo.USERTYPE_HOUR){
            return "小时员工";
        }
        if(this.userType== LoginInfo.USERTYPE_ENTRUST){
            return "委托员工";
        }
        if(this.userType== LoginInfo.USERTYPE_SALARY){
            return "受薪员工";
        }
        return null;
    }
}
