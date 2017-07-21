package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Employee {

    public static final int EMAIL_NO=0;//未绑定
    public static final int EMAIL_BIND=1;//已绑定

    public static final int PAY_EMAIL = 0;//邮寄
    public static final int PAY_FACE = 1;//直接交易
    public static final int PAY_BANK = 2;//银行交易

    private Long id;//手动设置，LoginInfo的id
    private int emailState=EMAIL_NO;//邮箱绑定状态

    /**
     * 允许用户修改的字段
     */
    private String realName;//真实姓名
    private String email;//邮箱，用于修改密码或者忘记密码使用。
    private String phone;//手机号码
    private int payMethod = PAY_BANK;//支付方式
    private String userName;//用户名
    private String bank;
    private String address;

    /**
     * 只有管理员可以修改的字段
     */
    private int userType;//用户类型
    private int state = LoginInfo.STATE_NORMAL;//状态
    private BigDecimal monthSalary=BigDecimal.ZERO;//受薪用户的月薪
    private BigDecimal hourSalary=BigDecimal.ZERO;//小时工户的的每小时的工资
    private BigDecimal entrustSalary=BigDecimal.ZERO;//委托员工生效(我这里理解为每个订单给多少钱)
    private List<Role> roles = new ArrayList<Role>();//角色

    public String getStateShow(){
        if(this.state==LoginInfo.STATE_NORMAL){
            return "正常状态";
        }
        if(this.state==LoginInfo.STATE_DELETE){
            return "删除状态";
        }
        return null;
    }

    public String getEmailStateShow(){
        if(this.emailState==EMAIL_BIND){
            return "已绑定";
        }
        if(this.emailState==EMAIL_NO){
            return "未绑定";
        }
        return null;
    }

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

    public String getPayMethodShow(){
        if(this.payMethod== PAY_BANK){
            return "银行支付";
        }
        if(this.payMethod== PAY_EMAIL){
            return "邮寄";
        }
        if(this.payMethod== PAY_FACE){
            return "直接交易";
        }
        return null;
    }

    public String getSalaryShow(){
        if(this.userType== LoginInfo.USERTYPE_ADMIN){
            return "月薪："+this.monthSalary;
        }
        if(this.userType== LoginInfo.USERTYPE_HOUR){
            return "小时支付："+this.hourSalary;
        }
        if(this.userType== LoginInfo.USERTYPE_ENTRUST){
            return "月薪："+this.monthSalary+" 委托："+this.entrustSalary;
        }
        if(this.userType== LoginInfo.USERTYPE_SALARY){
            return "月薪："+this.monthSalary;
        }
        return null;
    }

    public String getRolesShow(){
        StringBuilder sb=new StringBuilder(80);
        for(Role role:this.getRoles()){
            sb.append(role.getSn()+" ");
        }
        return sb.toString();
    }
}
