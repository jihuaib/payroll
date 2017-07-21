package com.muping.payroll.mapper;

import com.muping.payroll.domain.Permission;
import com.muping.payroll.query.PermissionQueryObject;

import java.util.List;

public interface PermissionMapper {

    int insert(Permission record);

    /**
     * 高级查询总数
     * @param qo
     * @return
     */
    Long queryCount(PermissionQueryObject qo);

    /**
     * 高级查询结果集
     * @param qo
     * @return
     */
    List<Permission> queryList(PermissionQueryObject qo);

    /**
     * 根据角色查询所有权限
     * @param id
     * @return
     */
    List<Permission> queryByRoleId(Long id);

    /**
     * 查询所有
     * @return
     */
    List<Permission> selectAll();

    /**
     * 根据权限表达式查找
     * @param expression
     * @return
     */
    Permission queryByExpression(String expression);

    /**
     * 查询登录用户的所有权限
     * @param id
     * @return
     */
    List<Permission> queryAllPermissionsCurUser(Long id);
}