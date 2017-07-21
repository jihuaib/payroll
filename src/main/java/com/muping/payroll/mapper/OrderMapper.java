package com.muping.payroll.mapper;

import com.muping.payroll.domain.Order;
import com.muping.payroll.query.OrderQueryObject;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    Order selectByPrimaryKey(Long id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    /**
     * 查询总数
     * @param qo
     * @return
     */
    Long queryCount(OrderQueryObject qo);

    /**
     * 查询结果集
     * @param qo
     * @return
     */
    List<Order> queryList(OrderQueryObject qo);

    /**
     * 查询总的订单数
     * @param id
     * @return
     */
    Long queryCountByEmployee(Long id);
}