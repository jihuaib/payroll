package com.muping.payroll.service;

import com.muping.payroll.domain.SystemMenu;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.SystemMenuQueryObject;

import java.util.List;

public interface ISystemMenuService {

    /**
     *高级查询
     * @param qo
     * @return
     */
    PageResult<SystemMenu> list(SystemMenuQueryObject qo);

    /**
     * 添加
     * @param systemMenu
     */
    void save(SystemMenu systemMenu);

    /**
     * 查询子菜单的父菜单
     * @param parentId
     * @return
     */
    SystemMenu queryParentNameByParentId(Long parentId);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询菜单信息
     * @param id
     * @return
     */
    SystemMenu selectById(Long id);

    /**
     * 修改菜单
     * @param systemMenu
     */
    void update(SystemMenu systemMenu);

    /**
     * 查询所有非根菜单菜单
     * @return
     */
    List<SystemMenu> queryAllWithoutRoot();

    /**
     * 查询所有跟菜单
     * @return
     */
    List<SystemMenu> queryAllRootMenus();

    /**
     * 查询登录用户的所有可操作的菜单
     * @param id
     * @return
     */
    List<SystemMenu> queryAllMenusWithPermission(Long id);
}
