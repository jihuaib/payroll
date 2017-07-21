package com.muping.payroll.controller;

import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.SystemMenu;
import com.muping.payroll.service.ILoginInfoService;
import com.muping.payroll.service.ISystemMenuService;
import com.muping.payroll.utils.JSONResult;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginInfoController extends BaseController{

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private ISystemMenuService systemMenuService;

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String userName,String password){
        JSONResult result = new JSONResult();
        try{
            loginInfoService.login(userName,password, LoginInfo.STATE_NORMAL);
        }catch (RuntimeException ex){
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    /**
     * 跳转到index界面
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        //查询所有跟菜单
        List<SystemMenu> roots=systemMenuService.queryAllRootMenus();
        model.addAttribute("roots",roots);
        //查询登录用户的所有课操作的菜单
        List<SystemMenu> menus=systemMenuService.queryAllMenusWithPermission(UserContext.getCurrentUser().getId());
        model.addAttribute("menus",menus);
        return "index";
    }
}
