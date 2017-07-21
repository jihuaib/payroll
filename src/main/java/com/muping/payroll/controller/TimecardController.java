package com.muping.payroll.controller;

import com.alibaba.fastjson.JSON;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Timecard;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.TimecardQueryObject;
import com.muping.payroll.service.ITimecardService;
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
public class TimecardController extends BaseController {

    @Autowired
    private ITimecardService timecardService;

    @RequiredPermission("Timecard列表")
    @RequestMapping("timecardList")
    public String timecardList(@ModelAttribute("qo") TimecardQueryObject qo, Model model) {
        if(UserContext.getCurrentUser().getUserType()!= LoginInfo.USERTYPE_ADMIN){
            qo.setEmployeeId(UserContext.getCurrentUser().getId());
        }
        PageResult<Timecard> result = timecardService.list(qo);
        model.addAttribute("result", result);
        return "timecard/list";
    }

    @RequiredPermission("添加/修改Timecard")
    @RequestMapping("timecardSaveOrUpdate")
    @ResponseBody
    public JSONResult timecardSaveOrUpdate(Timecard timecard, Model model) {
        JSONResult result = new JSONResult();
        try {
            if (timecard.getId() == null) {
                //添加
                timecardService.save(timecard);
                result.setMessage("添加成功！");
            } else {
                //修改
                timecardService.update(timecard);
                result.setMessage("修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("失败！" + e.getMessage());
        }
        return result;
    }

    @RequiredPermission("根据主键查询Timecard")
    @RequestMapping("queryTimecardById")
    public void queryTimecardById(Long id, HttpServletResponse response) {
        Timecard timecard = timecardService.selectById(id);
        response.setContentType("text/json;charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONStringWithDateFormat(timecard, "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
