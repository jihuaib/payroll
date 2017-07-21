package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Project;
import com.muping.payroll.domain.Timecard;
import com.muping.payroll.mapper.EmployeeMapper;
import com.muping.payroll.mapper.ProjectMapper;
import com.muping.payroll.mapper.TimecardMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.ProjectQueryObject;
import com.muping.payroll.service.IProjectService;
import com.muping.payroll.utils.DateUtil;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private TimecardMapper timecardMapper;

    public PageResult<Project> list(ProjectQueryObject qo) {
        //查询总数
        Long count=projectMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Project> projects=projectMapper.queryList(qo);
        PageResult<Project> result = new PageResult<Project>(qo.getCurPage(),qo.getPageSize(),projects,count.intValue());
        return result;
    }

    public void save(Project project) {
        projectMapper.insert(project);
    }

    public void delete(Long id) {
        projectMapper.deleteByPrimaryKey(id);
    }

    public Project selectById(Long id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    public void update(Project project) {
        //获取employee
        if(UserContext.getCurrentUser().getUserType()!= LoginInfo.USERTYPE_ADMIN){
            //非管理员
            //获取输入的时间间隔
            long poor = DateUtil.getDatePoor(project.getEndDate(), project.getBeginDate());
            //获取项目需要的真实时间
            Project project1 = this.projectMapper.selectByPrimaryKey(project.getId());
            //获取当前用户的时间卡
            Timecard currentTime = this.timecardMapper.findExsistTimecardCurrentTime(new Date(), UserContext.getCurrentUser().getId());
            //判断写入的日渐在不在timecard中
            if(project.getBeginDate().getTime()<currentTime.getBeginDate().getTime()||
                    project.getEndDate().getTime()>currentTime.getEndDate().getTime()){
                throw new RuntimeException("输入时间不再时间卡的范围之内！");
            }
            if(poor>project1.getCount()){
                throw new RuntimeException("不能超时完成项目！");
            }
            project.setState(Project.STATE_USE);
            Employee employee = new Employee();
            employee.setId(UserContext.getCurrentUser().getId());
            project.setEmployee(employee);
            project.setCount(project1.getCount());
            project.setName(project1.getName());
        }
        projectMapper.updateByPrimaryKey(project);
    }

}
