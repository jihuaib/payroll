package com.muping.payroll.test;

import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RepoetQueryObject;
import com.muping.payroll.service.IReportService;
import com.muping.payroll.vo.ReportVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ReportTest {
    @Autowired
    private IReportService reportService;

    @Test
    public void testReport(){
        RepoetQueryObject qo = new RepoetQueryObject();
        PageResult<ReportVo> result = reportService.queryPage(qo);
        System.out.println(result);
    }
}
