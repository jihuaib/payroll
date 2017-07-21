package com.muping.payroll.service;

import com.muping.payroll.domain.Project;
import com.muping.payroll.query.ProjectQueryObject;
import com.muping.payroll.query.PageResult;

public interface IProjectService {

    /**
     *高级查询
     * @param qo
     * @return
     */
    PageResult<Project> list(ProjectQueryObject qo);

    /**
     * 添加
     * @param project
     */
    void save(Project project);


    /**
     * 删除项目
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询项目信息
     * @param id
     * @return
     */
    Project selectById(Long id);

    /**
     * 修改项目
     * @param project
     */
    void update(Project project);

}
