package com.muping.payroll.service;

import com.muping.payroll.domain.Timecard;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.TimecardQueryObject;

public interface ITimecardService {

    /**
     *高级查询
     * @param qo
     * @return
     */
    PageResult<Timecard> list(TimecardQueryObject qo);

    /**
     * 添加
     * @param timecard
     */
    void save(Timecard timecard);


    /**
     * 根据id查询timecard
     * @param id
     * @return
     */
    Timecard selectById(Long id);

    /**
     * 修改timecard
     * @param timecard
     */
    void update(Timecard timecard);

}
