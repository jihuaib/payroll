package com.muping.payroll.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageQueryObject extends QueryObject{

    public static final int STATE_ALL_MY=0;//查询我的所有
    public static final int STATE_ALL=3;//查询所有
    public static final int STATE_SEND=1;//查询我发送的
    public static final int STATE_RE=2;//查询我接收的

    private Long currentUserId=-1L;//-1表示查询所有

    private int state=STATE_ALL;

}
