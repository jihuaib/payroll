package com.muping.payroll.service;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.query.EmployeeQueryObject;

public interface IEmployeeService {

    /**
     *高级查询
     * @param qo
     * @return
     */
    PageResult<Employee> list(EmployeeQueryObject qo);

    /**
     * 添加
     * @param employee
     */
    void save(Employee employee, Long[] ids1);

    /**
     * 删除员工
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee selectById(Long id);

    /**
     * 修改员工
     * @param employee
     */
    void update(Employee employee, Long[] ids1);

    /**
     * 改变用户状态
     * @param id
     */
    void changeState(Long id);

    /**
     * 绑定邮箱
     * @param email
     */
    void bindEmail(String email);

    /**
     * 验证邮箱
     * @param key
     */
    void validateCode(String key);

    /**
     * 维护个人信息
     * @param id
     * @param userName
     * @param realName
     * @param phone
     * @param payMethod
     */
    void updateInformation(Long id, String userName, String realName, String phone, int payMethod,String bank,String address);

    /**
     * 发送修改密码的验证码
     */
    void sendUpdatePasswordCode();

    /**
     * 修改密码
     * @param code
     * @param password
     */
    void updatePassword(String code, String password);
}
