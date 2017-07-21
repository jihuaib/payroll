<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-权限管理</title>

    <link rel="icon" href="/images/logo.ico">

    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.js"></script>
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
                first:"首页",
                last:"未页",
                prev:"上一页",
                next:"下一页",
                onPageClick: function (event, page) {
                   //设置当前页
                    $("#curPage").val(page);
                    $("#query_form").submit();
                }
            });

            //加载系统权限
            $("#load_btn").click(function () {
                layer.confirm('加载系统权限会消耗很长时间，确定继续加载？', {icon: 3, title:'加载系统权限'}, function(index){
                    //打开load
                    var load=layer.load();
                    //加载权限
                    $.ajax({
                        type:"post",
                        url:"loadPermissionList.do",
                        dataType:"json",
                        success:function (data) {
                            if(data.success){
                                layer.close(load);
                                layer.alert("加载成功", function(index){
                                    window.location.reload();
                                });
                            }else{
                                layer.msg("加载失败！"+data.message);
                                layer.close(load);
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
    <a class="btn btn-warning pull-left" id="load_btn"><span class="glyphicon glyphicon-plus"></span>&nbsp;加载系统权限</a>

    <form class="form-inline pull-right" id="query_form" method="post" action="permissionList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;权限列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>权限描述</th>
        <th>权限表达式</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${item.des}</td>
            <td>${item.expression}</td>
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