package com.muping.payroll.service;

import com.muping.payroll.domain.Message;
import com.muping.payroll.query.MessageQueryObject;
import com.muping.payroll.query.PageResult;

public interface IMessageService {

    /**
     * 发送站内信
     */
    void send(Message message);

    /**
     * 删除站内信
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageResult<Message> queryPage(MessageQueryObject qo);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Message selectById(Long id);
}
