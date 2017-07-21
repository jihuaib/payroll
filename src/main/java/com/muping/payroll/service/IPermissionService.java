package com.muping.payroll.service;

import com.muping.payroll.domain.Permission;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.PermissionQueryObject;

import java.util.List;

public interface IPermissionService {
    /**
     * 查询所有权限
     * @return
     */
    PageResult<Permission> list(PermissionQueryObject qo);

    /**
     * 加载系统权限
     */
    void loadPermissionList();

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> queryAll();
}
