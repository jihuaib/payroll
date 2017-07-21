package com.muping.payroll.listener;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Role;
import com.muping.payroll.mapper.EmployeeMapper;
import com.muping.payroll.mapper.LoginInfoMapper;
import com.muping.payroll.mapper.RoleMapper;
import com.muping.payroll.utils.MD5;
import com.muping.payroll.utils.MupingConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 用于初始化管理员的监听器
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RoleMapper roleMapper;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        //查询数据库中是否已经存在管理员
        Long count=loginInfoMapper.queryAdminCount(LoginInfo.USERTYPE_ADMIN);
        //不存在管理员
        if(count==0){
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserName(MupingConst.DEFAULT_ADMIN_PASSWORD);
            loginInfo.setPassword(MD5.encode(MupingConst.DEFAULT_ADMIN_PASSWORD));
            loginInfo.setUserType(LoginInfo.USERTYPE_ADMIN);
            loginInfo.setState(LoginInfo.STATE_NORMAL);
            loginInfoMapper.insert(loginInfo);

            //获取管理员的角色
            Role role=roleMapper.queryBySn("ADMIN");
            //创建管理员用户
            Employee employee = new Employee();
            employee.setUserName(MupingConst.DEFAULT_ADMIN_USERNAME);
            employee.setUserType(LoginInfo.USERTYPE_ADMIN);
            employee.setId(loginInfo.getId());
            employee.setState(LoginInfo.STATE_NORMAL);
            employee.setMonthSalary(MupingConst.INIT_MONTH_ADMIN_SALARY);
            employee.setPayMethod(Employee.PAY_BANK);
            //设置管理员默认邮箱
            employee.setEmail(MupingConst.DEFAULT_ADMIN_USERNAME+"@muping.com");
            employee.setEmailState(Employee.EMAIL_BIND);
            employeeMapper.insert(employee);
            //处理员工角色的关系
            employeeMapper.handlerRelationWithRole(employee.getId(),role.getId());
        }
        //存在管理员，不用处理
    }
}
