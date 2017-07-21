package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.Order;
import com.muping.payroll.mapper.EmployeeMapper;
import com.muping.payroll.mapper.OrderMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.OrderQueryObject;
import com.muping.payroll.service.IOrderService;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    public PageResult<Order> list(OrderQueryObject qo) {
        //查询总数
        Long count=orderMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Order> orders=orderMapper.queryList(qo);
        PageResult<Order> result = new PageResult<Order>(qo.getCurPage(),qo.getPageSize(),orders,count.intValue());
        return result;
    }

    public void save(Order order) {
        //设置下单人
        Employee employee = new Employee();
        employee.setId(UserContext.getCurrentUser().getId());
        order.setEmployee(employee);
        orderMapper.insert(order);
    }

    public void delete(Long id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    public Order selectById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public void update(Order order) {
        //设置下单人
        Employee employee = new Employee();
        employee.setId(UserContext.getCurrentUser().getId());
        order.setEmployee(employee);
        orderMapper.updateByPrimaryKey(order);
    }

}
