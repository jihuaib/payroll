package com.muping.payroll.mapper;

import com.muping.payroll.domain.Project;
import com.muping.payroll.query.ProjectQueryObject;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Project record);

    Project selectByPrimaryKey(Long id);

    List<Project> selectAll();

    int updateByPrimaryKey(Project record);

    /**
     * 查询总数
     * @param qo
     * @return
     */
    Long queryCount(ProjectQueryObject qo);

    /**
     * 查询结果集
     * @param qo
     * @return
     */
    List<Project> queryList(ProjectQueryObject qo);
}