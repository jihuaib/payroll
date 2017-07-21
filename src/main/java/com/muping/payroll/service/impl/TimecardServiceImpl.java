package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Timecard;
import com.muping.payroll.mapper.TimecardMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.TimecardQueryObject;
import com.muping.payroll.service.ITimecardService;
import com.muping.payroll.utils.TimeHWUtil;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TimecardServiceImpl implements ITimecardService {

    @Autowired
    private TimecardMapper timecardMapper;

    public PageResult<Timecard> list(TimecardQueryObject qo) {
        //查询总数
        Long count=timecardMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Timecard> timecards=timecardMapper.queryList(qo);
        PageResult<Timecard> result = new PageResult<Timecard>(qo.getCurPage(),qo.getPageSize(),timecards,count.intValue());
        return result;
    }

    public void save(Timecard timecard) {
        //判断当前时间段有没有时间卡
        Timecard currentTime = this.timecardMapper.findExsistTimecardCurrentTime(new Date(), UserContext.getCurrentUser().getId());
        if(currentTime!=null){
            throw new RuntimeException("当前时间段存在timecard!");
        }
        //根据不同用户创建timecard
        if(UserContext.getCurrentUser().getUserType()== LoginInfo.USERTYPE_HOUR){
            Date date = new Date();
            timecard.setBeginDate(date);
            try {
                timecard.setEndDate(TimeHWUtil.getSunday(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
            timecard.setState(Timecard.STATE_CUR);
            Employee employee = new Employee();
            employee.setId(UserContext.getCurrentUser().getId());
            timecard.setEmployee(employee);
        }else{
            Date date = new Date();
            timecard.setBeginDate(date);
            try {
                timecard.setEndDate(TimeHWUtil.getLastDayOfMonth(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
            timecard.setState(Timecard.STATE_CUR);
            Employee employee = new Employee();
            employee.setId(UserContext.getCurrentUser().getId());
            timecard.setEmployee(employee);
        }
        timecardMapper.insert(timecard);
    }


    public Timecard selectById(Long id) {
        return timecardMapper.selectByPrimaryKey(id);
    }

    public void update(Timecard timecard) {
        timecardMapper.updateByPrimaryKey(timecard);
    }

}
