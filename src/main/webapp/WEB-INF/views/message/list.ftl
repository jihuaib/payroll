<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-站内信管理</title>

    <link rel="icon" href="/images/logo.ico">

    <link rel="stylesheet" type="text/css" href="/js/wangEditor-2.1.23/dist/css/wangEditor.min.css">
    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="/js/jquery-form.js"></script>
    <script type="text/javascript" src="/js/wangEditor-2.1.23/dist/js/wangEditor.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/layer/layer.js"></script>
    <script type="text/javascript" src="/js/jquery.twbsPagination.min.js"></script>

    <style type="text/css">
        body {
            font-size: 12px;
            color: #2F4056;
        }
    </style>

    <script type="text/javascript">

        $(function () {
            //分页
            $('#pagination').twbsPagination({
                totalPages: ${result.totalPages},
                visiblePages: 10,
                startPage:${result.curPage},
                first: "首页",
                last: "未页",
                prev: "上一页",
                next: "下一页",
                onPageClick: function (event, page) {
                    //设置当前页
                    $("#curPage").val(page);
                    $("#query_form").submit();
                }
            });

            //删除角色
            $(".mp_delete_btn").click(function () {
                var id = $(this).data("deleteid");
                //询问是否一定删除
                layer.confirm('确定删除？', {icon: 3, title: '删除'}, function (index) {
                    $.ajax({
                        dataType: "json",
                        data: {
                            'id': id
                        },
                        url: "messageDelete.do",
                        type: "post",
                        success: function (data) {
                            if (data.success) {
                                layer.alert("删除成功！", function (index) {
                                    window.location.reload();
                                });
                            } else {
                                layer.msg(data.message);
                            }
                        }
                    });
                });
            });

            //点击查看内容打开弹出框
            $(".show_message_btn").click(function () {
                //获取id
                var id = $(this).data("showid");
                //发送请求
                $.ajax({
                    type: "post",
                    url: "/queryMessageById.do",
                    data: {
                        'id': id
                    },
                    success: function (data) {
                        var json = JSON.parse(data);
                        //设置数据
                        $("#show_send").val(json.send.userName);
                        $("#show_recieve").val(json.receive.userName);
                        //处理日期
                        var da = new Date(json.date);
                        var year = da.getFullYear();
                        var month = da.getMonth() + 1;
                        var date = da.getDate();
                        var hour = da.getHours();
                        var minute = da.getMinutes();
                        var second = da.getSeconds();
                        $("#show_date").val(year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second);
                        //处理日期
                        $("#show_title").val(json.title);
                        $("#show_content").html(json.content);
                        layer.open({
                            title: "查看站内信",
                            area: ['1000px', '500px'],
                            type: 1,
                            content: $('#mp_add_box')
                        });
                    }
                });
            });
        });

        //创建内容编辑区
        $(function () {
            var editor = new wangEditor("edit");
            editor.create();
        });

        //发送站内信
        $(function () {
            $(".send_message_btn").click(function () {
                layer.open({
                    title: "发送站内信",
                    area: ['1000px', '500px'],
                    type: 1,
                    content: $('#mp_send_message_box')
                });
            });

            //将发送站内信表单变为ajax提交的表单
            $("#send_message_form").ajaxForm({
                dataType:"json",
                success:function (data) {
                    if(data.success){
                        layer.alert("发送成功", function (index) {
                            window.location.reload();
                        });
                    }else{
                        layer.msg(data.message);
                    }
                }
            });
        });

    </script>

</head>
<body>

<div class="mp_query" style="height: 40px;margin-right: 10px;">
    <a class="btn btn-warning pull-left send_message_btn"><span
            class="glyphicon glyphicon-plus"></span>&nbsp;发送站内信</a>

    <form class="form-inline pull-right" id="query_form" method="post" action="/messageList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
        <div class="form-group">
            <select type="text" class="form-control" name="state" style="font-size: 12px;">
                <#if USER_IN_SESSION.userType==0>
                    <option selected="selected" value="3">查询所有列表</option>
                </#if>
                <option value="0">查询我的所有信息</option>
                <option value="1">已发送</option>
                <option value="2">已接收</option>
            </select>

        <#if qo.state??>
            <script>
                $("#query_form :input[name='state'] option[value='${qo.state}']").prop("selected", "selected");
            </script>
        </#if>

        </div>
        <button type="submit" class="btn btn-warning" style="width: 80px">查询</button>
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;站内信列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>发送人(用户名)</th>
        <th>接受人(用户名)</th>
        <th>标题</th>
        <th>发送时间</th>
        <th>操作</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${(item.send.userName)!""}</td>
            <td>${(item.receive.userName)!""}</td>
            <td>${(item.title)!""}</td>
            <td>${(item.date)?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>
                <a class="btn btn-danger btn-sm mp_delete_btn" data-deleteid="${item.id}">删除</a>
                <a class="btn btn-info btn-sm show_message_btn" data-showid="${item.id}">查看站内信内容</a>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
<!--表格-->

<!--分页-->
<div class="page pull-right" style="margin-right: 80px;">
    <ul id="pagination" class="pagination-sm"></ul>
</div>
<!--分页-->

<!--查看站内信-->
<div id="mp_add_box" style="display: none;padding-left: 40px;padding-right: 40px;padding-top: 10px;">
    <div class="form-group">
        <label for="show_send">发送人(用户名)</label>
        <input type="text" class="form-control input-sm" id="show_send">
    </div>
    <div class="form-group">
        <label for="show_recieve">接收人(用户名)</label>
        <input type="text" class="form-control input-sm" id="show_recieve">
    </div>
    <div class="form-group">
        <label for="show_title">标题</label>
        <input type="text" class="form-control input-sm" id="show_title">
    </div>
    <div class="form-group">
        <label for="show_date">发送时间</label>
        <input type="text" class="form-control input-sm" id="show_date">
    </div>
    <div class="form-group">
        <label>站内信内容</label>
        <div id="show_content" style="border: 1px solid black;padding: 10px;">
        </div>
    </div>
</div>
<!--查看站内信-->

<!--发送站内信-->
<div id="mp_send_message_box" style="display: none;padding-left: 40px;padding-right: 40px;padding-top: 10px;">
    <form action="/sendMessage.do" method="post" id="send_message_form">
        <div class="form-group">
            <label>接收人(用户名)</label>
            <input type="text" class="form-control input-sm" name="receive.userName">
        </div>
        <div class="form-group">
            <label>标题</label>
            <input type="text" class="form-control input-sm" name="title">
        </div>
        <div class="form-group">
            <label>站内信内容</label>
            <textarea id="edit" style="height:700px;" name="content"></textarea>
        </div>
        <input type="submit" class="btn btn-danger btn-sm form-control" value="发送">
    </form>
</div>
<!--发送站内信站内信-->

</body>
</html>