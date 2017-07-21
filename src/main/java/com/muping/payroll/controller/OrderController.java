package com.muping.payroll.controller;

import com.alibaba.fastjson.JSON;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Order;
import com.muping.payroll.query.OrderQueryObject;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.service.IOrderService;
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

/**
 * 系统菜单相关
 */
@Controller
@SuppressWarnings("all")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @RequiredPermission("订单列表")
    @RequestMapping("orderList")
    public String orderList(@ModelAttribute("qo") OrderQueryObject qo, Model model) {
        //非管理员
        if(UserContext.getCurrentUser().getUserType()!= LoginInfo.USERTYPE_ADMIN){
            qo.setEmployeeId(UserContext.getCurrentUser().getId());
        }
        PageResult<Order> result = orderService.list(qo);
        model.addAttribute("result", result);
        return "order/list";
    }

    @RequiredPermission("添加/修改订单")
    @RequestMapping("orderSaveOrUpdate")
    @ResponseBody
    public JSONResult orderSaveOrUpdate(Order order, Model model) {
        JSONResult result = new JSONResult();
        try {
            if (order.getId() == null) {
                //添加
                orderService.save(order);
                result.setMessage("添加成功！");
            } else {
                //修改
                orderService.update(order);
                result.setMessage("修改成功！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }

    @RequiredPermission("根据主键查询订单")
    @RequestMapping("queryOrderById")
    public void queryOrderById(Long id, HttpServletResponse response) {
        Order order = orderService.selectById(id);
        response.setContentType("text/json;charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONStringWithDateFormat(order, "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiredPermission("删除订单")
    @RequestMapping("orderDelete")
    @ResponseBody
    public JSONResult orderDelete(Long id) {
        JSONResult result = new JSONResult();
        try {
            orderService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }

}
