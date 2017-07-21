package com.muping.payroll.mapper;

import com.muping.payroll.domain.Message;
import com.muping.payroll.query.MessageQueryObject;

import java.util.List;

public interface MessageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    /**
     * 查询总数
     * @param qo
     * @return
     */
    Long queryCount(MessageQueryObject qo);

    /**
     * 查询结果集
     * @param qo
     * @return
     */
    List<Message> queryList(MessageQueryObject qo);
}