package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Role;
import com.muping.payroll.mapper.RoleMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.RoleQueryObject;
import com.muping.payroll.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult<Role> list(RoleQueryObject qo) {
        //查询总数
        Long count=roleMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Role> roles=roleMapper.queryList(qo);
        PageResult<Role> result = new PageResult<Role>(qo.getCurPage(),qo.getPageSize(),roles,count.intValue());
        return result;
    }

    @Override
    public void save(Role role,Long[] ids1,Long[] ids2) {
        //保存角色
        roleMapper.insert(role);
        //处理关系
        if(ids1!=null) {
            for (int i = 0; i < ids1.length; i++) {
                roleMapper.handlerRelationWithPermission(role.getId(), ids1[i]);
            }
        }
        if(ids2!=null) {
            for (int i = 0; i < ids2.length; i++) {
                roleMapper.handlerRelationWithMenu(role.getId(), ids2[i]);
            }
        }
    }

    @Override
    public void delete(Long id) {
        //删除关联属性
        //删除权限
        roleMapper.deletePermissionRalation(id);
        //删除菜单
        roleMapper.deleteMenuRalation(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Role selectById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Role role,Long[] ids1,Long[] ids2) {
        //删除关联属性
        //删除权限
        roleMapper.deletePermissionRalation(role.getId());
        //删除菜单
        roleMapper.deleteMenuRalation(role.getId());
        roleMapper.updateByPrimaryKey(role);
        //更新关联属性
        //处理关系
        if(ids1!=null) {
            for (int i = 0; i < ids1.length; i++) {
                roleMapper.handlerRelationWithPermission(role.getId(), ids1[i]);
            }
        }
        if(ids2!=null) {
            for (int i = 0; i < ids2.length; i++) {
                roleMapper.handlerRelationWithMenu(role.getId(), ids2[i]);
            }
        }
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }
}
