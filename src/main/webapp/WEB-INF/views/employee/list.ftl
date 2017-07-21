<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-员工管理</title>

    <link rel="icon" href="/images/logo.ico">

    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
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
            if ("${msg}" != "") {
                layer.alert("${msg}", function (index) {
                    layer.close(index);
                });
            }

            //删除
            $(".mp_delete_btn").click(function () {
                var id = $(this).data("deleteid");
                //询问是否一定删除
                layer.confirm('不能直接删除员工，该删除只是改变用户状态，是否继续？', {icon: 3, title: '删除'}, function (index) {
                    $.ajax({
                        dataType: "json",
                        data: {
                            'id': id
                        },
                        url: "changeState.do",
                        type: "post",
                        success: function (data) {
                            if (data.success) {
                                layer.alert("改变状态成功！", function (index) {
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


<!--高级查询-->
<div class="mp_query" style="height: 40px;margin-right: 10px;">

    <a class="btn btn-info pull-left mp_add_btn" href="/employeeInput.do"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加员工</a>

    <form class="form-inline pull-right" method="post" action="/employeeList.do" id="query_form">
        <input type="hidden" name="curPage" value="1" id="curPage">
        <div class="form-group">
            <select type="text" class="form-control" name="state" style="font-size: 12px;">
                <option selected="selected" value="-1">请选择用户状态</option>
                <option value="1">正常状态</option>
                <option value="0">删除状态</option>
            </select>

        <#if qo.state??>
            <script>
                $("#query_form :input[name='state'] option[value='${qo.state}']").prop("selected", "selected");
            </script>
        </#if>

        </div>
        <div class="form-group">
            <select type="text" class="form-control" name="payMethod" style="font-size: 12px;">
                <option selected="selected" value="-1">请选择支付方式</option>
                <option value="0">邮寄</option>
                <option value="1">直接交易</option>
                <option value="2">银行支付</option>
            </select>

        <#if qo.payMethod??>
            <script>
                $("#query_form :input[name='payMethod'] option[value='${qo.payMethod}']").prop("selected", "selected");
            </script>
        </#if>

        </div>
        <div class="form-group">
            <select type="text" class="form-control" name="userType" style="font-size: 12px;">
                <option selected="selected" value="-1">请选择用户类型</option>
                <option value="0">管理员</option>
                <option value="1">受薪工</option>
                <option value="2">小时工</option>
                <option value="3">委托工</option>
            </select>

        <#if qo.userType??>
            <script>
                $("#query_form :input[name='userType'] option[value='${qo.userType}']").prop("selected", "selected");
            </script>
        </#if>

        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="keyword" placeholder="关键字(用户名/真实姓名)" style="font-size: 12px;"
                   value="${qo.keyword!""}">
        </div>
        <button type="submit" class="btn btn-warning" style="width: 80px">查询</button>
    </form>
</div>
<!--高级查询-->

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;员工列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>用户名(昵称)</th>
        <th>真实姓名</th>
        <th>邮箱</th>
        <th>手机号码</th>
        <th>支付方式</th>
        <th>用户类型</th>
        <th>用户状态</th>
        <th>银行卡号</th>
        <th>邮寄地址</th>
        <th>工资状况</th>
        <th>邮箱状态</th>
        <th>角色编码</th>
        <th>操作</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${(item.userName)!""}</td>
            <td>${(item.realName)!""}</td>
            <td>${(item.email)!""}</td>
            <td>${(item.phone)!""}</td>
            <td>${(item.payMethodShow)!""}</td>
            <td>${(item.userTypeShow)!""}</td>
            <td>${(item.stateShow)!""}</td>
            <td>${(item.bank)!""}</td>
            <td>${(item.address)!""}</td>
            <td>${(item.salaryShow)!""}</td>
            <td>${(item.emailStateShow)!""}</td>
            <td>${(item.rolesShow)!""}</td>
            <td>
                <a class="btn btn-danger btn-sm mp_delete_btn" data-deleteid="${item.id}">删除</a>
                <a href="/employeeInput.do?id=${item.id}" class="btn btn-warning btn-sm">修改</a>
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