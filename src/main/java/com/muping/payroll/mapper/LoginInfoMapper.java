package com.muping.payroll.mapper;

import com.muping.payroll.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

public interface LoginInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(LoginInfo record);

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    LoginInfo queryByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password,@Param("state")int state);

    /**
     * 查询管理员的个数
     * @return
     */
    Long queryAdminCount(int userType);

    /**
     * 查询用户名是否已经存在
     * @param s
     * @return
     */
    Long queryCountByUserName(String s);

    /**
     * 根据用户名查找LoginInfo
     * @param userName
     * @return
     */
    LoginInfo queryByUserName(String userName);

    /**
     * 查询除了自己还有没有用户名重复
     * @param userName
     * @param id
     * @return
     */
    LoginInfo queryByUserNameNotMyself(@Param("userName") String userName, @Param("id") Long id);
}