<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-角色管理</title>

    <link rel="icon" href="/images/logo.ico">

    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="/js/jquery-form.js"></script>
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

            //判断msg是否为空
            if("${msg}"!=""){
                layer.alert("${msg}", function (index) {
                    layer.close(index);
                });
            }

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
                        url: "roleDelete.do",
                        type: "post",
                        success: function (data) {
                            if (data.success) {
                                layer.alert("删除成功！", function (index) {
                                    window.location.reload();
                                    <#assign msg=""/>
                                });
                            } else {
                                layer.msg(data.message);
                            }
                        }
                    });
                });
            });

        });

    </script>

</head>
<body>

<div class="mp_query" style="height: 40px;margin-right: 10px;">
    <a class="btn btn-warning pull-left" href="roleInput.do"><span
            class="glyphicon glyphicon-plus"></span>&nbsp;添加角色</a>

    <form class="form-inline pull-right" id="query_form" method="post" action="roleList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;角色列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>角色名称</th>
        <th>角色编码</th>
        <th>操作</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${(item.name)!""}</td>
            <td>${(item.sn)!""}</td>
            <td>
                <a class="btn btn-danger btn-sm mp_delete_btn" data-deleteid="${item.id}">删除</a>
                <a href="/roleInput.do?id=${item.id}" class="btn btn-warning btn-sm">修改</a>
                <a href="/roleShow.do?id=${item.id}" class="btn btn-info btn-sm">查看角色详细信息</a>
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

</body>
</html>