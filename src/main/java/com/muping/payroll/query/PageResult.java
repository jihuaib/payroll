package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

//封装分页信息
@Getter
@Setter
@ToString
public class PageResult<T> {

    //用户传入
    private Integer curPage=1;
    private Integer pageSize=6;
    private List<T> result;
    private Integer totalCount;

    private Integer prePage;
    private Integer nextPage;
    private Integer totalPages;

    public PageResult(Integer curPage, Integer pageSize, List<T> result, Integer totalCount) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.result = result;
        this.totalCount = totalCount;

        //计算
        this.totalPages=(totalCount%pageSize==0)?(totalCount/pageSize):(totalCount/pageSize+1);
        this.prePage=(curPage-1<1)?(1):(curPage-1);
        this.nextPage=(curPage+1>this.totalPages)?(this.totalPages):(curPage+1);
    }

    public static PageResult getEmpty(){
        PageResult result = new PageResult(1, 6, null, 0);
        result.setNextPage(1);
        result.setTotalPages(1);
        return result;
    }
}
