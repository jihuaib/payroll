package com.muping.payroll.service;

import com.muping.payroll.domain.Order;
import com.muping.payroll.query.OrderQueryObject;
import com.muping.payroll.query.PageResult;

public interface IOrderService {

    /**
     *高级查询
     * @param qo
     * @return
     */
    PageResult<Order> list(OrderQueryObject qo);

    /**
     * 添加
     * @param order
     */
    void save(Order order);


    /**
     * 删除订单
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    Order selectById(Long id);

    /**
     * 修改订单
     * @param order
     */
    void update(Order order);

}
