package com.muping.payroll.controller;

import com.alibaba.fastjson.JSON;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Message;
import com.muping.payroll.query.MessageQueryObject;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.service.ILoginInfoService;
import com.muping.payroll.service.IMessageService;
import com.muping.payroll.utils.JSONResult;
import com.muping.payroll.utils.RequiredPermission;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 站内信相关
 */
@Controller
public class MessageController extends BaseController{

    @Autowired
    private IMessageService messageService;
    @Autowired
    private ILoginInfoService loginInfoService;

    @RequiredPermission("查看站内信所有列表")
    @RequestMapping("messageList")
    public String messageList(@ModelAttribute("qo") MessageQueryObject qo, Model model) {
        //防止非管理员查看其他用户的信息
        if(UserContext.getCurrentUser().getUserType()!=LoginInfo.USERTYPE_ADMIN){
            qo.setState(MessageQueryObject.STATE_ALL_MY);
        }
        //不是查询所有列表
        if(qo.getState()!=MessageQueryObject.STATE_ALL){
            qo.setCurrentUserId(UserContext.getCurrentUser().getId());
        }
        PageResult<Message> result = messageService.queryPage(qo);
        model.addAttribute("result", result);
        return "message/list";
    }

    @RequiredPermission("查看我的站内信")
    @RequestMapping("myMessage")
    public String myMessage(@ModelAttribute("qo") MessageQueryObject qo, Model model) {
        qo.setCurrentUserId(UserContext.getCurrentUser().getId());
        qo.setState(MessageQueryObject.STATE_ALL_MY);
        PageResult<Message> result = messageService.queryPage(qo);
        model.addAttribute("result", result);
        return "message/list";
    }


    @RequiredPermission("主键查询站内信")
    @RequestMapping("queryMessageById")
    public void  queryMessageById(Long id, HttpServletResponse response){
        Message message=this.messageService.selectById(id);
        try {
            String s = JSON.toJSONString(message);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiredPermission("删除站内信")
    @RequestMapping("messageDelete")
    @ResponseBody
    public JSONResult messageDelete(Long id) {
        JSONResult result = new JSONResult();
        try {
            messageService.delete(id);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }

    @RequiredPermission("发送站内信")
    @RequestMapping("sendMessage")
    @ResponseBody
    public JSONResult sendMessage(Message message){
        JSONResult result = new JSONResult();
        try {
            //根据用户名站到LoginInfo
            LoginInfo loginInfo=loginInfoService.queryByUserName(message.getReceive().getUserName());
            message.setReceive(loginInfo);
            message.setSend(UserContext.getCurrentUser());
            message.setDate(new Date());
            messageService.send(message);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
