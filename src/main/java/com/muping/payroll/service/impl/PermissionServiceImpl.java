package com.muping.payroll.service.impl;

import com.muping.payroll.controller.BaseController;
import com.muping.payroll.domain.Permission;
import com.muping.payroll.mapper.PermissionMapper;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.PermissionQueryObject;
import com.muping.payroll.service.IPermissionService;
import com.muping.payroll.utils.PermissionUtil;
import com.muping.payroll.utils.RequiredPermission;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements IPermissionService,ApplicationContextAware{

    private ApplicationContext applicationContext;

    @Autowired
    private PermissionMapper permissionMapper;

    public PageResult<Permission> list(PermissionQueryObject qo) {
        //查询总数
        Long count=permissionMapper.queryCount(qo);

        if(count==0){
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Permission> permissions=permissionMapper.queryList(qo);
        PageResult<Permission> result = new PageResult<Permission>(qo.getCurPage(),qo.getPageSize(),permissions,count.intValue());
        return result;
    }

    public void loadPermissionList() {
        //获取controller下的所有需要权限的方法，拼接权限表达式(需要注入applicationContext)
        Map<String, BaseController> beansOfType = applicationContext.getBeansOfType(BaseController.class);
        for (BaseController baseController : beansOfType.values()) {
            Method[] methods = baseController.getClass().getMethods();
            for (Method method : methods) {
                //需要权限
                if(method.isAnnotationPresent(RequiredPermission.class)){
                    //获取权限表达式
                    String expression = PermissionUtil.buildExpression(method);
                    //判断此权限表达式是否存在数据库
                    Permission findPer=permissionMapper.queryByExpression(expression);
                    if(findPer==null){
                        //进行添加操作
                        Permission permission = new Permission();
                        //获取权限描述
                        String des = method.getAnnotation(RequiredPermission.class).value();
                        permission.setExpression(expression);
                        permission.setDes(des);
                        //存入数据库
                        permissionMapper.insert(permission);
                    }
                }
            }
        }
    }

    @Override
    public List<Permission> queryAll() {
        return permissionMapper.selectAll();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
