package com.muping.payroll.mapper;

import com.muping.payroll.domain.Timecard;
import com.muping.payroll.query.TimecardQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TimecardMapper {
    int insert(Timecard record);

    Timecard selectByPrimaryKey(Long id);

    List<Timecard> selectAll();

    int updateByPrimaryKey(Timecard record);

    /**
     * 查询总数
     * @param qo
     * @return
     */
    Long queryCount(TimecardQueryObject qo);

    /**
     * 查询结果集
     * @param qo
     * @return
     */
    List<Timecard> queryList(TimecardQueryObject qo);

    /**
     * 查询当前时间段是否存在时间卡
     * @param date
     * @param id
     * @return
     */
    Timecard findExsistTimecardCurrentTime(@Param("date") Date date, @Param("id") Long id);

    /**
     * 查询有效的时间卡
     * @param stateCur
     * @return
     */
    List<Timecard> findEnableTimecard(int stateCur);
}