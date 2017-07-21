package com.muping.payroll.mapper;

import com.muping.payroll.domain.VerificationEmailCode;

public interface VerificationEmailCodeMapper {

    int insert(VerificationEmailCode record);

    /**
     * 根据uuid查询验证码对象
     * @param key
     * @return
     */
    VerificationEmailCode queryByKey(String key);

}