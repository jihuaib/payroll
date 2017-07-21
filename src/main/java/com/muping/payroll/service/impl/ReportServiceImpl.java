package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.mapper.EmployeeMapper;
import com.muping.payroll.mapper.OrderMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RepoetQueryObject;
import com.muping.payroll.service.IReportService;
import com.muping.payroll.utils.MupingConst;
import com.muping.payroll.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult<ReportVo> queryPage(RepoetQueryObject qo) {
        //查询总数
        Long count = employeeMapper.queryReportCount(qo);
        if (count == 0L) {
            return PageResult.getEmpty();
        }

        //查询总数
        List<ReportVo> reportVos = new ArrayList<ReportVo>();
        List<Employee> employees = employeeMapper.queryReportList(qo);
        handler(reportVos, employees);
        return new PageResult<ReportVo>(qo.getCurPage(), qo.getPageSize(), reportVos, count.intValue());
    }

    @Override
    public List<ReportVo> queryAll() {
        //查询总数
        List<ReportVo> reportVos = new ArrayList<ReportVo>();
        List<Employee> employees = employeeMapper.selectAll();
        handler(reportVos, employees);
        return reportVos;
    }

    private void handler(List<ReportVo> reportVos, List<Employee> employees) {
        for (Employee employee : employees) {
            ReportVo reportVo = new ReportVo();
            //管理员
            if (employee.getUserType() == LoginInfo.USERTYPE_ADMIN) {
                reportVo.setUserType(employee.getUserType());
                reportVo.setUserName(employee.getUserName());
                reportVo.setTotalSalary(employee.getMonthSalary());
            }
            //小时员工
            if (employee.getUserType() == LoginInfo.USERTYPE_HOUR) {
                reportVo.setUserType(employee.getUserType());
                reportVo.setUserName(employee.getUserName());
                //获取总计工作小时数
                Long hours = employeeMapper.queryAllWorkHours(employee.getId());
                if (hours == null) {
                    hours = 0L;
                }
                reportVo.setTotalCount(hours.intValue());
                reportVo.setTotalSalary(employee.getHourSalary().multiply(new BigDecimal(hours)));
            }
            //受薪员工
            if (employee.getUserType() == LoginInfo.USERTYPE_SALARY) {
                reportVo.setUserType(employee.getUserType());
                reportVo.setUserName(employee.getUserName());
                //获取总计工作小时数
                Long hours = employeeMapper.queryAllWorkHours(employee.getId());
                if (hours == null) {
                    hours = 0L;
                }
                reportVo.setTotalCount(hours.intValue());
                if (hours >= MupingConst.MIN_WORK_HOURS) {
                    reportVo.setTotalSalary(employee.getHourSalary().multiply(new BigDecimal(hours)));
                }
            }
            //委托员工
            if (employee.getUserType() == LoginInfo.USERTYPE_ENTRUST) {
                reportVo.setUserType(employee.getUserType());
                reportVo.setUserName(employee.getUserName());
                //获取总计工作小时数
                Long hours = employeeMapper.queryAllWorkHours(employee.getId());
                //获取总的订单数
                Long orderCount = orderMapper.queryCountByEmployee(employee.getId());
                if (hours == null) {
                    hours = 0L;
                }
                if (orderCount == null) {
                    orderCount = 0L;
                }
                reportVo.setTotalCount(hours.intValue());
                reportVo.setTotalOrders(orderCount.intValue());
                if (hours >= MupingConst.MIN_WORK_HOURS) {
                    reportVo.setTotalSalary(employee.getMonthSalary().add(employee.getEntrustSalary().multiply(new BigDecimal(orderCount))));
                } else {
                    reportVo.setTotalSalary(employee.getEntrustSalary().multiply(new BigDecimal(orderCount)));
                }
            }
            reportVos.add(reportVo);
        }
    }
}
