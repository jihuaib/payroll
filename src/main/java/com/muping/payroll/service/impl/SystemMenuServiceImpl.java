package com.muping.payroll.service.impl;

import com.muping.payroll.domain.SystemMenu;
import com.muping.payroll.mapper.SystemMenuMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.SystemMenuQueryObject;
import com.muping.payroll.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    public PageResult<SystemMenu> list(SystemMenuQueryObject qo) {
        //查询总数
        Long count=systemMenuMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<SystemMenu> systemMenus=systemMenuMapper.queryList(qo);
        PageResult<SystemMenu> result = new PageResult<SystemMenu>(qo.getCurPage(),qo.getPageSize(),systemMenus,count.intValue());
        return result;
    }

    public void save(SystemMenu systemMenu) {
        systemMenuMapper.insert(systemMenu);
    }

    public SystemMenu queryParentNameByParentId(Long parentId) {
        return systemMenuMapper.queryParentNameByParentId(parentId);
    }

    public void delete(Long id) {
        //判断是否存在子菜单
        Long count=systemMenuMapper.queryChildCount(id);
        if(count!=0) {
            throw new RuntimeException("存在子菜单，禁止删除！");
        }
        systemMenuMapper.deleteByPrimaryKey(id);
    }

    public SystemMenu selectById(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    public void update(SystemMenu systemMenu) {
        systemMenuMapper.updateByPrimaryKey(systemMenu);
    }

    @Override
    public List<SystemMenu> queryAllWithoutRoot() {
        return systemMenuMapper.queryAllWithoutRoot();
    }

    @Override
    public List<SystemMenu> queryAllRootMenus() {
        return systemMenuMapper.queryAllRootMenus();
    }

    @Override
    public List<SystemMenu> queryAllMenusWithPermission(Long id) {
        return systemMenuMapper.queryAllMenusWithPermission(id);
    }
}
