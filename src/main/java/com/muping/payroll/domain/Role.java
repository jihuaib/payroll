package com.muping.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Role {

    private Long id;
    private String name;
    private String sn;

    //多对多关系
    private List<Permission> permissions=new ArrayList<Permission>();
    private List<SystemMenu> systemMenus=new ArrayList<SystemMenu>();
}
