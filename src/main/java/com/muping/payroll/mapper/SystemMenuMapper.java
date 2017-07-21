package com.muping.payroll.mapper;

import com.muping.payroll.domain.SystemMenu;
import com.muping.payroll.query.SystemMenuQueryObject;

import java.util.List;

public interface SystemMenuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> queryAllWithoutRoot();

    int updateByPrimaryKey(SystemMenu record);

    /**
     * 高级查询总数
     * @param qo
     * @return
     */
    Long queryCount(SystemMenuQueryObject qo);

    /**
     * 高级查询结果集
     * @param qo
     * @return
     */
    List<SystemMenu> queryList(SystemMenuQueryObject qo);

    /**
     * 查询子菜单的父菜单
     * @param parentId
     * @return
     */
    SystemMenu queryParentNameByParentId(Long parentId);

    /**
     * 查询该菜单的子菜单的个数
     * @param id
     * @return
     */
    Long queryChildCount(Long id);

    /**
     * 根据角色id获取所有权限
     * @param id
     * @return
     */
    List<SystemMenu> queryByRoleId(Long id);

    /**
     * 查询所有根菜单
     * @return
     */
    List<SystemMenu> queryAllRootMenus();

    /**
     * 查询所有已近登录用户的可操作的菜单
     * @param id
     * @return
     */
    List<SystemMenu> queryAllMenusWithPermission(Long id);
}