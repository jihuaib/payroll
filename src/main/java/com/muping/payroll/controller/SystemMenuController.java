package com.muping.payroll.controller;

import com.muping.payroll.domain.SystemMenu;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.SystemMenuQueryObject;
import com.muping.payroll.service.ISystemMenuService;
import com.muping.payroll.utils.JSONResult;
import com.muping.payroll.utils.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统菜单相关
 */
@Controller
public class SystemMenuController extends BaseController {

    @Autowired
    private ISystemMenuService systemMenuService;

    @RequiredPermission("系统菜单列表")
    @RequestMapping("systemMenuList")
    public String systemMenuList(@ModelAttribute("qo") SystemMenuQueryObject qo, Model model) {
        //查询当前菜单下的子菜单
        PageResult<SystemMenu> result = systemMenuService.list(qo);
        //获取当前的父菜单名称
        if (qo.getParentId() == -1L) {
            //在父菜单下操作
            model.addAttribute("parentName", "根菜单");
        } else {
            //根据parentId查询父菜单名
            SystemMenu systemMenu = systemMenuService.queryParentNameByParentId(qo.getParentId());
            model.addAttribute("parentName", systemMenu.getName());
        }
        model.addAttribute("result", result);
        return "systemMenu/list";
    }

    @RequiredPermission("添加/修改菜单")
    @RequestMapping("systemMenuSaveOrUpdate")
    @ResponseBody
    public JSONResult systemMenuSaveOrUpdate(SystemMenu systemMenu, Model model) {
        JSONResult result = new JSONResult();
        //如果systemMenu的parent的id为-1，说明在根菜单下更新子菜单，这是数据库不需要parent_id这个值
        try {
            if (systemMenu.getParent().getId() == -1l) {
                systemMenu.getParent().setId(null);
            }
            if (systemMenu.getId() == null) {
                //添加
                systemMenuService.save(systemMenu);
                result.setMessage("添加成功！");
            } else {
                //修改
                systemMenuService.update(systemMenu);
                result.setMessage("修改成功！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }


    @RequiredPermission("删除系统菜单")
    @RequestMapping("systemMenuDelete")
    @ResponseBody
    public JSONResult systemMenuDelete(Long id) {
        JSONResult result = new JSONResult();
        try {
            systemMenuService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }

}
