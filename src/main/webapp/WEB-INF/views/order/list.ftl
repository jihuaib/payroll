<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-订单管理</title>

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
            $("#date").click(function () {
                WdatePicker({
                    dateFmt:"yyyy-MM-dd HH:mm:ss",
                    maxDate:new Date()
                });
            });
        });

        //清除数据
        function clearData() {
            $("#mp_add_box #id").val("");
            $("#mp_add_box #address").val("");
            $("#mp_add_box #productName").val("");
            $("#mp_add_box #clientName").val("");
            $("#mp_add_box #date").val("");
        }

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

            //打开添加框
            $("#load_btn").click(function () {
                clearData();
                layer.open({
                    title: "添加/修改",
                    area: ['800px', '400px'],
                    type: 1,
                    content: $('#mp_add_box')
                });
            });

            //将表单变为ajax表单
            $("#mp_add_box").ajaxForm({
                datatype: "json",
                success: function (data) {
                    if (data.success) {
                        layer.alert(data.message, function (index) {
                            window.location.reload();
                        });
                    } else {
                        layer.msg(data.message);
                    }
                }
            });

            //删除菜单
            $(".mp_delete_btn").click(function () {
                var id = $(this).data("deleteid");
                //询问是否一定删除
                layer.confirm('确定删除？', {icon: 3, title: '删除'}, function (index) {
                    $.ajax({
                        dataType: "json",
                        data: {
                            'id': id
                        },
                        url: "orderDelete.do",
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

            //修改菜单
            $(".mp_update_btn").click(function () {
                var id = $(this).data("updateid");
                $.ajax({
                    type:"post",
                    url:"/queryOrderById.do",
                    data:{
                        id:id,
                    },
                    dataType:"json",
                    success:function (data) {
                        $("#mp_add_box #id").val(data.id);
                        $("#mp_add_box #address").val(data.address);
                        $("#mp_add_box #productName").val(data.productName);
                        $("#mp_add_box #clientName").val(data.clientName);
                        $("#mp_add_box #date").val(data.date);

                        //打开添加框
                        layer.open({
                            title: "添加/修改",
                            area: ['800px', '400px'],
                            type: 1,
                            content: $('#mp_add_box')
                        });
                    }

                });
            });


        });

    </script>

</head>
<body>

<div class="mp_query" style="height: 40px;margin-right: 10px;">
    <a class="btn btn-warning pull-left" id="load_btn"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加订单</a>

    <form class="form-inline pull-right" id="query_form" method="post" action="orderList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;订单列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>下单员工</th>
        <th>联系地址</th>
        <th>产品名称</th>
        <th>客户姓名</th>
        <th>下单日期</th>
        <th>操作</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${(item.employee.userName)!""}</td>
            <td>${(item.address)!""}</td>
            <td>${(item.productName)!""}</td>
            <td>${(item.clientName)!""}</td>
            <td>${(item.date)?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>
                <a class="btn btn-danger btn-sm mp_delete_btn" data-deleteid="${item.id}">删除</a>
                <a class="btn btn-warning btn-sm mp_update_btn" data-updateid="${item.id}">修改</a>
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

<!--添加/修改-->
<div id="mp_add_box" style="display: none;padding-left: 40px;padding-right: 40px;padding-top: 10px;">
    <form method="post" action="/orderSaveOrUpdate.do">
        <input type="hidden" name="id" id="id">
        <div class="form-group">
            <label>下单地址</label>
            <input type="text" class="form-control input-sm" id="address" name="address" placeholder="请输入下单地址"
            >
        </div>
        <div class="form-group">
            <label>产品姓名</label>
            <input type="text" class="form-control input-sm" id="productName" name="productName" placeholder="请输入产品姓名">
        </div>
        <div class="form-group">
            <label>客户姓名</label>
            <input type="text" class="form-control input-sm" id="clientName" name="clientName" placeholder="请输入客户姓名">
        </div>
        <div class="form-group">
            <label >下单日期</label>
            <input type="text" class="form-control input-sm" name="date" id="date" placeholder="请选择下单日期" readonly="readonly">
        </div>
        <button type="submit" class="btn btn-info form-control">添加/修改</button>
    </form>
</div>
<!--添加/修改-->

</body>
</html>