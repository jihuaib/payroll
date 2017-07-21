package com.muping.payroll.service;

import com.muping.payroll.domain.Role;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RoleQueryObject;

import java.util.List;

public interface IRoleService {

    /**
     *高级查询
     * @param qo
     * @return
     */
    PageResult<Role> list(RoleQueryObject qo);

    /**
     * 添加
     * @param role
     */
    void save(Role role,Long[] ids1,Long[] ids2);

    /**
     * 删除角色
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    Role selectById(Long id);

    /**
     * 修改角色
     * @param role
     */
    void update(Role role,Long[] ids1,Long[] ids2);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> selectAll();
}
