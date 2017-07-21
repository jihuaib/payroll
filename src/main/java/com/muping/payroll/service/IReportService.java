package com.muping.payroll.service;

import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RepoetQueryObject;
import com.muping.payroll.vo.ReportVo;

import java.util.List;

public interface IReportService {
    /**
     * 高级查询分页
     * @return
     */
    PageResult<ReportVo> queryPage(RepoetQueryObject qo);

    /**
     * 查询所有
     * @return
     */
    List<ReportVo> queryAll();
}
