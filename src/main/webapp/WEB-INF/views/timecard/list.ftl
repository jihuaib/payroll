<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-timecard管理</title>

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
            
            $(".select_project_btn").click(function () {
                layer.open({
                    type: 2,
                    content: '/projectList.do',
                    area:['1000px','500px']
                });
            });

            //打开添加框
            $("#load_btn").click(function () {
                layer.alert('如果当前时间段没有timecard，我们会根据用户类型创建相应的timecard！', function (index) {
                    layer.close(index);
                    $.ajax({
                        dataType: "json",
                        url: "timecardSaveOrUpdate.do",
                        type: "post",
                        success: function (data) {
                            if (data.success) {
                                layer.alert(data.message, function (xx) {
                                    window.location.reload();
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
    <a class="btn btn-warning pull-left" id="load_btn"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加timecard</a>

    <form class="form-inline pull-right" id="query_form" method="post" action="timecardList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;timecard列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>timecard开始时间</th>
        <th>timecard结束时间</th>
        <th>timecard所属员工</th>
        <th>timecard所属员工类型</th>
        <th>timecard状态</th>
        <th>操作</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>
                <#if (item.beginDate)??>
                ${(item.beginDate)?string("yyyy-MM-dd HH:mm:ss")}
            </#if>
            </td>
            <td>
                <#if (item.endDate)??>
                ${(item.endDate)?string("yyyy-MM-dd HH:mm:ss")}
            </#if>
            </td>
            <td>${(item.employee.userName)!""}</td>
            <td>${(item.employee.userTypeShow)!""}</td>
            <td>${(item.stateShow)!""}</td>
            <td>
                <#if USER_IN_SESSION.userType!=0>
                <a class="btn btn-danger btn-sm mp_delete_btn" data-deleteid="${item.id}">提交</a>
                <a class="btn btn-warning btn-sm select_project_btn">查询项目</a>
                </#if>
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