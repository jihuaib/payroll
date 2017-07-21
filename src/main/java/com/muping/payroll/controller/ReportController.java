package com.muping.payroll.controller;

import com.alibaba.fastjson.JSON;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RepoetQueryObject;
import com.muping.payroll.service.IReportService;
import com.muping.payroll.utils.RequiredPermission;
import com.muping.payroll.utils.UserContext;
import com.muping.payroll.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController extends BaseController {

    @Autowired
    private IReportService reportService;

    @RequiredPermission("报告列表")
    @RequestMapping("reportList")
    public String reportList(Model model, RepoetQueryObject qo){
        try {
            if(UserContext.getCurrentUser().getUserType()!= LoginInfo.USERTYPE_ADMIN){
                qo.setEmployeeId(UserContext.getCurrentUser().getId());
            }
            PageResult<ReportVo> result=reportService.queryPage(qo);
            model.addAttribute("result",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "report/list";
    }

    @RequiredPermission("线形图分析")
    @RequestMapping("line")
    public String line(Model model){
        List<ReportVo> reportVos = reportService.queryAll();
        List<String> titles=new ArrayList<String>();
        List<BigDecimal> salarys=new ArrayList<BigDecimal>();
        for (ReportVo reportVo : reportVos) {
            titles.add(reportVo.getUserName());
        }
        for (ReportVo reportVo : reportVos) {
            salarys.add(reportVo.getTotalSalary());
        }
        System.out.println(JSON.toJSONString(titles));
        System.out.println(JSON.toJSONString(salarys));
        model.addAttribute("titles", JSON.toJSONString(titles));
        model.addAttribute("salarys",JSON.toJSONString(salarys));
        return "report/line";
    }

    @RequiredPermission("柱状图分析")
    @RequestMapping("column")
    public String column(Model model){
        List<ReportVo> reportVos = reportService.queryAll();
        List<String> titles=new ArrayList<String>();
        List<BigDecimal> salarys=new ArrayList<BigDecimal>();
        for (ReportVo reportVo : reportVos) {
            titles.add(reportVo.getUserName());
        }
        for (ReportVo reportVo : reportVos) {
            salarys.add(reportVo.getTotalSalary());
        }
        System.out.println(JSON.toJSONString(titles));
        System.out.println(JSON.toJSONString(salarys));
        model.addAttribute("titles", JSON.toJSONString(titles));
        model.addAttribute("salarys",JSON.toJSONString(salarys));
        return "report/column";
    }
}
