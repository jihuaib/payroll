package com.muping.payroll.controller;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.Role;
import com.muping.payroll.query.EmployeeQueryObject;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.service.IEmployeeService;
import com.muping.payroll.service.IRoleService;
import com.muping.payroll.utils.JSONResult;
import com.muping.payroll.utils.RequiredPermission;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EmployeeController extends BaseController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping("employeeList")
    @RequiredPermission("员工列表")
    public String employeeList(@ModelAttribute("qo") EmployeeQueryObject qo, Model model,@ModelAttribute("msg") String msg){
        PageResult<Employee> result = employeeService.list(qo);
        model.addAttribute("result",result);
        return "employee/list";
    }

    @RequestMapping("information")
    @RequiredPermission("进入信息维护界面")
    public String information(Model model){
        //查询出我的所有信息
        Employee employee = this.employeeService.selectById(UserContext.getCurrentUser().getId());
        model.addAttribute("employee",employee);
        return "employee/information";
    }

    @RequestMapping("employeeInput")
    @RequiredPermission("员工编辑")
    public String employeeInput(Long id,Model model){
        List<Role> roles=roleService.selectAll();
        model.addAttribute("roles",roles);
        if(id!=null){
            Employee employee = employeeService.selectById(id);
            model.addAttribute("employee",employee);
        }
        return "employee/input";
    }

    @RequiredPermission("添加/修改员工")
    @RequestMapping("employeeSaveOrUpdate")
    public String employeeSaveOrUpdate(Employee employee, Model model,Long[] roleIds) {
        String msg = "添加成功！";
        try {
            if (employee.getId() == null) {
                //添加
                employeeService.save(employee,roleIds);
                msg+="你刚才添加的员工用户名为"+employee.getUserName();
            }else {
                //修改
                employeeService.update(employee,roleIds);
                msg="修改成功！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "添加失败！" + e.getMessage();
        }
        return "forward:/employeeList.do?msg=" + msg+"&userType=-1";
    }

    @RequiredPermission("改变员工状态")
    @RequestMapping("changeState")
    @ResponseBody
    public JSONResult changeState(Long id) {
        JSONResult result = new JSONResult();
        try {
            employeeService.changeState(id);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequiredPermission("绑定邮箱")
    @RequestMapping("bindEmail")
    @ResponseBody
    public JSONResult bindEmail(String email) {
        JSONResult result = new JSONResult();
        try {
            employeeService.bindEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping("verificationEmailCode")
    public String verificationEmailCode(String key,Model model) {
        String msg="";
        try {
            employeeService.validateCode(key);
            msg="绑定成功！";
        } catch (Exception e) {
            msg=e.getMessage();
        }
        model.addAttribute("msg",msg);
        return "success";
    }

    @ResponseBody
    @RequiredPermission("维护个人信息")
    @RequestMapping("updateInformation")
    public JSONResult updateInformation(Long id,String userName,String realName,String phone,int payMethod,String bank,String address) {
        JSONResult result = new JSONResult();
        try {
            employeeService.updateInformation(id,userName,realName,phone,payMethod,bank,address);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequiredPermission("发送修改密码的验证码")
    @RequestMapping("sendUpdatePasswordCode")
    public JSONResult sendUpdatePasswordCode() {
        JSONResult result = new JSONResult();
        try {
            employeeService.sendUpdatePasswordCode();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    @ResponseBody
    @RequiredPermission("修改密码")
    @RequestMapping("updatePassword")
    public JSONResult updatePassword(String code,String password) {
        JSONResult result = new JSONResult();
        try {
            employeeService.updatePassword(code,password);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


}
