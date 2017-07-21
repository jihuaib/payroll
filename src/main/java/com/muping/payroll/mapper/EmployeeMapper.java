package com.muping.payroll.mapper;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.query.EmployeeQueryObject;
import com.muping.payroll.query.RepoetQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    /**
     * 处理员工和角色的关系
     * @param id
     * @param id1
     */
    void handlerRelationWithRole(@Param("employee_id") Long id, @Param("role_id") Long id1);

    /**
     * 查询总数
     * @param qo
     * @return
     */
    Long queryCount(EmployeeQueryObject qo);

    /**
     * 查询结果集
     * @param qo
     * @return
     */
    List<Employee> queryList(EmployeeQueryObject qo);

    /**
     * 删除中间表中该用户对应的角色
     * @param id
     */
    void deleteRoleRalation(Long id);

    /**
     * 根据邮箱查询
     * @return
     */
    Long queryByEmail(String email);

    /**
     * 查询报告总数
     * @param qo
     * @return
     */
    Long queryReportCount(RepoetQueryObject qo);

    /**
     * 查询报告结果集
     * @param qo
     * @return
     */
    List<Employee> queryReportList(RepoetQueryObject qo);

    /**
     * 查询总的工作小时
     * @param id
     * @return
     */
    Long queryAllWorkHours(Long id);
}