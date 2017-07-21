package com.muping.payroll.controller;

import com.alibaba.fastjson.JSON;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Project;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.ProjectQueryObject;
import com.muping.payroll.service.IProjectService;
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
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;

    @RequiredPermission("项目列表")
    @RequestMapping("projectList")
    public String projectList(@ModelAttribute("qo") ProjectQueryObject qo, Model model) {
        if(UserContext.getCurrentUser().getUserType()== LoginInfo.USERTYPE_ADMIN){
            qo.setEmployeeId(-1L);
        }
        PageResult<Project> result = projectService.list(qo);
        model.addAttribute("result", result);
        return "project/list";
    }

    @RequiredPermission("添加/修改项目")
    @RequestMapping("projectSaveOrUpdate")
    @ResponseBody
    public JSONResult projectSaveOrUpdate(Project project, Model model) {
        JSONResult result = new JSONResult();
        try {
            if (project.getId() == null) {
                //添加
                projectService.save(project);
                result.setMessage("添加成功！");
            } else {
                //修改
                projectService.update(project);
                result.setMessage("修改成功！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("失败！" + e.getMessage());
        }
        return result;
    }

    @RequiredPermission("根据主键查询项目")
    @RequestMapping("queryProjectById")
    public void queryProjectById(Long id, HttpServletResponse response) {
        Project project = projectService.selectById(id);
        response.setContentType("text/json;charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONStringWithDateFormat(project, "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiredPermission("删除项目")
    @RequestMapping("projectDelete")
    @ResponseBody
    public JSONResult projectDelete(Long id) {
        JSONResult result = new JSONResult();
        try {
            projectService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败！" + e.getMessage());
        }
        return result;
    }

}
