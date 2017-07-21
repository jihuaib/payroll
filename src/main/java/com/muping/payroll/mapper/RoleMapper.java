package com.muping.payroll.mapper;

import com.muping.payroll.domain.Role;
import com.muping.payroll.query.RoleQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /**
     * 高级查询总数
     * @param qo
     * @return
     */
    Long queryCount(RoleQueryObject qo);

    /**
     * 高级查询结果集
     * @param qo
     * @return
     */
    List<Role> queryList(RoleQueryObject qo);

    /**
     * 处理角色和权限的关系
     * @param id
     * @param aLong
     */
    void handlerRelationWithPermission(@Param("role_id") Long id, @Param("permission_id") Long aLong);

    /**
     * 处理角色和菜单的关系
     * @param id
     * @param aLong
     */
    void handlerRelationWithMenu(@Param("role_id")Long id, @Param("menu_id")Long aLong);

    /**
     * 删除关联权限
     * @param id
     */
    void deletePermissionRalation(Long id);

    /**
     * 删除关联菜单
     * @param id
     */
    void deleteMenuRalation(Long id);

    /**
     * 根据员工查询角色
     * @param id
     * @return
     */
    List<Role> queryByEmployeeId(Long id);

    /**
     * 根据角色编码获取角色
     * @param admin
     * @return
     */
    Role queryBySn(String admin);
}