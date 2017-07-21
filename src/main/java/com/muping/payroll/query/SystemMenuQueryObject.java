package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemMenuQueryObject extends QueryObject{

    private Long parentId=-1L;//-1表示根菜单

}
