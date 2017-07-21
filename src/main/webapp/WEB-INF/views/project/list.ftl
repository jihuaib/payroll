<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-项目管理</title>

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
            $("#beginDate").click(function () {
                WdatePicker({
                    maxDate: new Date(),
                    dateFmt: "yyyy-MM-dd HH:mm:ss"
                });
            });
            $("#endDate").click(function () {
                WdatePicker({
                    minDate: $("#beginDate").val(),
                    maxDate: new Date(),
                    dateFmt: "yyyy-MM-dd HH:mm:ss"
                });
            });
        });

        //清除数据
        function clearData() {
            $("#mp_add_box #id").val("");
            $("#mp_add_box #name").val("");
            $("#mp_add_box #beginDate").val("");
            $("#mp_add_box #endDate").val("");
            $("#mp_add_box #count").val("");
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
                    area: ['600px', '250px'],
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
                        url: "projectDelete.do",
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
                    type: "post",
                    url: "/queryProjectById.do",
                    data: {
                        id: id,
                    },
                    dataType: "json",
                    success: function (data) {
                        $("#mp_add_box #id").val(data.id);
                        $("#mp_add_box #name").val(data.name);
                        $("#mp_add_box #beginDate").val(data.beginDate);
                        $("#mp_add_box #endDate").val(data.endDate);
                        $("#mp_add_box #count").val(data.count);

                        //打开添加框
                        layer.open({
                            title: "添加/修改",
                            area: ['600px', '300px'],
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
<#if USER_IN_SESSION.userType==0>
    <a class="btn btn-warning pull-left" id="load_btn"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加项目</a>
</#if>
    <form class="form-inline pull-right" id="query_form" method="post" action="projectList.do">
        <input type="hidden" name="curPage" value="1" id="curPage">
        <div class="form-group">
            <input type="text" class="form-control" name="count" placeholder="查询多少小时以内需要完成的项目"
                   style="font-size: 12px;width: 220px;" value="${(qo.count)!""}">
        </div>
        <div class="form-group">
            <select type="text" class="form-control" name="state" style="font-size: 12px;">
                <option selected="selected" value="-1">请选择项目状态</option>
                <option value="0">空闲状态</option>
                <option value="1">接管状态</option>
                <option value="2">完成状态</option>
            </select>

        <#if qo.state??>
            <script>
                $("#query_form :input[name='state'] option[value='${qo.state}']").prop("selected", "selected");
            </script>
        </#if>

        </div>

    <#if USER_IN_SESSION.userType!=0>
        <div class="form-group">
            <select type="text" class="form-control" name="employeeId" style="font-size: 12px;">
                <option selected="selected" value="-1">查询所有</option>
                <option value="${USER_IN_SESSION.id}">我的项目</option>
            </select>

            <#if qo.state??>
                <script>
                    $("#query_form :input[name='employeeId'] option[value='${qo.employeeId}']").prop("selected", "selected");
                </script>
            </#if>

        </div>
    </#if>

        <div class="form-group">
            <input type="text" class="form-control" name="keyword" placeholder="项目名称" style="font-size: 12px;"
                   value="${qo.keyword!""}">
        </div>
        <button type="submit" class="btn btn-warning" style="width: 80px">查询</button>
    </form>
</div>

<!--表格-->
<h2 class="color_2"><span class="glyphicon glyphicon-th-list"></span>&nbsp;项目列表</h2>
<table class="table table-striped table-bordered table-hover">
    <tbody>
    <tr>
        <th>项目名称</th>
        <th>项目耗时(单位:小时)</th>
        <th>项目开始时间</th>
        <th>项目结束时间</th>
        <th>项目接管人</th>
        <th>项目状态</th>
        <th>操作</th>
    </tr>
    </tbody>
    <tbody>
    <#if result.result??>
        <#list result.result as item>
        <tr>
            <td>${(item.name)!""}</td>
            <td>${(item.count)!""}</td>
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
            <td>${(item.stateShow)!""}</td>
            <td>
                <#if USER_IN_SESSION.userType==0 &&item.state!=1 &&item.state!=2>
                    <a class="btn btn-danger btn-sm mp_delete_btn" data-deleteid="${item.id}">删除</a>
                    <a class="btn btn-warning btn-sm mp_update_btn" data-updateid="${item.id}">修改</a>
                </#if>
                <#if USER_IN_SESSION.userType!=0 &&item.state!=1 &&item.state!=2>
                    <a class="btn btn-warning btn-sm mp_update_btn" data-updateid="${item.id}">选择</a>
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

<!--添加/修改-->
<div id="mp_add_box" style="display: none;padding-left: 40px;padding-right: 40px;padding-top: 10px;">
    <form method="post" action="/projectSaveOrUpdate.do">
        <input type="hidden" name="id" id="id">
    <#if USER_IN_SESSION.userType==0>
        <div class="form-group">
            <label>项目名称</label>
            <input type="text" class="form-control input-sm" id="name" name="name" placeholder="请输入项目名称"
            >
        </div>
        <div class="form-group">
            <label>项目耗时(单位:小时)</label>
            <input type="text" class="form-control input-sm" id="count" name="count" placeholder="请输入项目耗时">
        </div>
    </#if>
    <#if USER_IN_SESSION.userType!=0>
        <div class="form-group">
            <label>项目开始时间</label>
            <input type="text" class="form-control input-sm" id="beginDate" name="beginDate" placeholder="请选择项目开始时间"
                   readonly="readonly">
        </div>
        <div class="form-group">
            <label>项目结束时间</label>
            <input type="text" class="form-control input-sm" name="endDate" id="endDate" placeholder="请选择项目结束时间"
                   readonly="readonly">
        </div>
    </#if>
        <button type="submit" class="btn btn-info form-control">添加/修改</button>
    </form>
</div>
<!--添加/修改-->

</body>
</html>