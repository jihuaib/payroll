package com.muping.payroll.task;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Message;
import com.muping.payroll.domain.Timecard;
import com.muping.payroll.mapper.TimecardMapper;
import com.muping.payroll.service.IMessageService;
import com.muping.payroll.utils.MupingConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 小时员工定时任务
 */
@Service
public class HourTask {

    @Autowired
    private TimecardMapper timecardMapper;
    @Autowired
    private IMessageService messageService;

    public void sendSalary(){
        //查询所有小时员工
        List<Timecard> timecardList=timecardMapper.findEnableTimecard(Timecard.STATE_CUR);
        for (Timecard timecard : timecardList) {
            //发送站内信
            Message message = new Message();
            message.setDate(new Date());
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserName(timecard.getEmployee().getUserName());
            loginInfo.setId(timecard.getEmployee().getId());
            message.setReceive(loginInfo);
            LoginInfo admin = new LoginInfo();
            admin.setUserName(MupingConst.DEFAULT_ADMIN_USERNAME);
            admin.setId(3L);
            message.setSend(admin);
            message.setContent("工资已经发送！请查收！");
            message.setTitle("发送工资");
            //messageService.send(message);
            if(timecard.getEmployee().getUserType()== LoginInfo.USERTYPE_HOUR){
                if(timecard.getEmployee().getPayMethod()== Employee.PAY_FACE){
                    System.out.println("发工资喽！"+"直接交易给"+timecard.getEmployee().getUserName());
                }
                if(timecard.getEmployee().getPayMethod()== Employee.PAY_BANK){
                    System.out.println("发工资喽！"+"银行交易给"+timecard.getEmployee().getUserName());
                }
                if(timecard.getEmployee().getPayMethod()== Employee.PAY_EMAIL){
                    System.out.println("发工资喽！"+"邮寄交易给"+timecard.getEmployee().getUserName());
                }
            }
        }

    }
}
