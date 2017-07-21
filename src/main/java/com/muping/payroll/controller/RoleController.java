package com.muping.payroll.controller;

import com.muping.payroll.domain.Permission;
import com.muping.payroll.domain.Role;
import com.muping.payroll.domain.SystemMenu;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RoleQueryObject;
import com.muping.payroll.service.IPermissionService;
import com.muping.payroll.service.IRoleService;
import com.muping.payroll.service.ISystemMenuService;
import com.muping.payroll.utils.JSONResult;
import com.muping.payroll.utils.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统菜单相关
 */
@Controller
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISystemMenuService systemMenuService;
    @Autowired
    private IPermissionService permissionService;

    @RequiredPermission("角色列表")
    @RequestMapping("roleList")
    public String roleList(@ModelAttribute("qo") RoleQueryObject qo, Model model, @ModelAttribute("msg") String msg) {
        PageResult<Role> result = roleService.list(qo);
        model.addAttribute("result", result);
        return "role/list";
    }

    @RequiredPermission("添加/修改角色")
    @RequestMapping("roleSaveOrUpdate")
    public String roleSaveOrUpdate(Role role, Model model, Long[] pers, Long[] menus) {
        String msg = "添加成功！";
        try {
            if (role.getId() == null) {
                //添加
                roleService.save(role, pers, menus);
            }else {
                //修改
                roleService.update(role, pers, menus);
                msg="修改成功！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "添加失败！" + e.getMessage();
        }
        return "forward:/roleList.do?msg=" + msg;
    }

    @RequiredPermission("删除角色")
    @RequestMapping("roleDelete")
    @ResponseBody
    public JSONResult roleDelete(Long id) {
        JSONResult result = new JSONResult();
        try {
            roleService.delete(id);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }

    @RequiredPermission("角色编辑")
    @RequestMapping("roleInput")
    public String roleInput(Long id, Model model) {
        handler(id, model);
        return "role/input";
    }

    private void handler(Long id, Model model) {
        //查询所有菜单
        List<SystemMenu> systemMenus = systemMenuService.queryAllWithoutRoot();
        model.addAttribute("all_systemMenus", systemMenus);
        //查询所有权限
        List<Permission> permissions = permissionService.queryAll();
        model.addAttribute("all_permissions", permissions);

        //判断需要还是不需要回显
        if (id != null) {
            Role role = roleService.selectById(id);
            model.addAttribute("role", role);
        }
    }

    @RequiredPermission("角色查看")
    @RequestMapping("roleShow")
    public String roleShow(Long id, Model model) {
        handler(id, model);
        return "role/show";
    }
}
