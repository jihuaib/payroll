package com.muping.payroll.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SystemMenu {

    private SystemMenu parent;//存储父菜单
    private Long id;
    private String sn;
    private String name;
    private String url;

    public String getJSONStr(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id",id);
        map.put("sn",sn);
        map.put("name",name);
        map.put("url",url);
        return JSON.toJSONString(map);
    }

}
