<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-员工报告</title>

    <link rel="icon" href="/images/logo.ico">

    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="/js/jquery-form.js"></script>
    <script type="text/javascript" src="/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/layer/layer.js"></script>
    <script type="text/javascript" src="/js/jquery.twbsPagination.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

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


            $("#load_btn").click(function () {
                layer.open({
                    type: 2,
                    title:"图表分析",
                    content: '/line.do',
                    area:['1000px','600px']
                });
            });

        });


    </script>

</head>
<body>

<div class="mp_query" style="height: 40px;margin-right: 10px;">
    <a class="btn btn-warning pull-left" id="load_btn"><span class="glyphicon glyphicon-plus"></span>&nbsp;图表分析</a>
    <form class="form-inline pull-right" id="query_form" method="post" action="reportList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;报告列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>员工用户名</th>
        <th>员工类型</th>
        <th>总计工作小时</th>
        <th>总计订单数</th>
        <th>总计薪水</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${(item.userName)!""}</td>
            <td>${(item.userTypeShow)!""}</td>
            <td>${(item.totalCount)!"0"}</td>
            <td>
                ${(item.totalOrders)!"0"}
            </td>
            <td>
                ${(item.totalSalary)!"0"}
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