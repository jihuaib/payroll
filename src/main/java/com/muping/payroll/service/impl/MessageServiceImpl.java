package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Message;
import com.muping.payroll.mapper.LoginInfoMapper;
import com.muping.payroll.mapper.MessageMapper;
import com.muping.payroll.query.MessageQueryObject;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public void send(Message message) {
        //判断接收人是否存在
        Long count = loginInfoMapper.queryCountByUserName(message.getReceive().getUserName());
        if(count==0L){
            throw new RuntimeException("接收人不存在！请重新输入！");
        }
        messageMapper.insert(message);
    }

    @Override
    public void delete(Long id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult<Message> queryPage(MessageQueryObject qo) {
        //查询总数
        Long count=messageMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Message> roles=messageMapper.queryList(qo);
        PageResult<Message> result = new PageResult<Message>(qo.getCurPage(),qo.getPageSize(),roles,count.intValue());
        return result;
    }

    @Override
    public Message selectById(Long id) {
        return this.messageMapper.selectByPrimaryKey(id);
    }
}
