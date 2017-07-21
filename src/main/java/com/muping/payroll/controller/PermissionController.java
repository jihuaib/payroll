package com.muping.payroll.controller;

import com.muping.payroll.domain.Permission;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.PermissionQueryObject;
import com.muping.payroll.service.IPermissionService;
import com.muping.payroll.utils.JSONResult;
import com.muping.payroll.utils.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PermissionController extends BaseController{

    @Autowired
    private IPermissionService permissionService;

    @RequiredPermission("系统权限列表")
    @RequestMapping("permissionList")
    public String permissionList(@ModelAttribute("qo") PermissionQueryObject qo, Model model){
        //获取所有权限
        PageResult<Permission> result=permissionService.list(qo);
        model.addAttribute("result",result);
        return "permission/list";
    }

    @RequiredPermission("加载系统权限")
    @RequestMapping("loadPermissionList")
    @ResponseBody
    public JSONResult loadPermissionList(){
        //获取所有权限
        JSONResult jsonResult = new JSONResult();
        try {
            permissionService.loadPermissionList();
        } catch (Exception e) {
            jsonResult.setMessage(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}
