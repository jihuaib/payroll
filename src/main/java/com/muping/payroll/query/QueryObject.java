package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract  class QueryObject {

    private int curPage=1;
    private int pageSize=6;

    public int getStartIndex(){
        return (curPage-1)*pageSize;
    }
}
