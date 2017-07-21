<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-员工编辑</title>

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

        //提交之前选定提交option
        function selectedSubmitOption() {
            $("#role_option_right option").prop("selected", "selected");
        }

        $(function () {
            //处理角色移动
            //全部右移
            $("#role_moveAllRight").click(function () {
                $("#role_option_right").append($("#role_option_left option"));
            });
            //左移全部
            $("#role_moveAllLeft").click(function () {
                $("#role_option_right option").appendTo($("#role_option_left"));
            });
            //右移选定
            $("#role_moveSelectedRight").click(function () {
                $("#role_option_right").append($("#role_option_left option:selected"));
            });
            //左移选定
            $("#role_moveSelectedLeft").click(function () {
                $("#role_option_right option:selected").appendTo($("#role_option_left"));
            });

            //手动提交,选定提交项
            $("#updateForm").submit(function () {
                selectedSubmitOption();
            });

            //去除两边相同option
            //权限
            var arr = $.map($("#role_option_right option"), function (item) {
                return $(item).prop("value");
            });
            $.each($("#role_option_left option"), function (index, item) {
                if ($.inArray($(item).prop("value"), arr) >= 0) {
                    $(item).remove();
                }
            });

        });

    </script>

</head>
<body>

<!--添加/修改-->
<div class="col-md-6 center-block" style="margin-left: 20%;">
    <form method="post" action="/employeeSaveOrUpdate.do" id="updateForm">
        <input type="hidden" name="id" value="${(employee.id)!""}">
        <div class="row">
            <div class="form-group col-md-10">
                <label for="form_userType">用户类型</label>
                <select class="form-control" id="form_userType" style="font-size: 12px;" name="userType">
                    <option selected="selected" value="0">管理员</option>
                    <option value="1">受薪员工</option>
                    <option value="2">小时员工</option>
                    <option value="3">委托员工</option>
                </select>
                <#if employee?? && employee.userType??>
                    <script>
                        $("#form_userType option[value='${employee.userType}']").prop("selected","selected");
                    </script>
                </#if>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-10">
                <label for="form_userType">银行卡号</label>
                <input type="text" class="form-control input-sm"  name="bank" placeholder="请输入银行卡号" value="${(employee.bank)!""}">
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-10">
                <label for="form_userType">邮寄地址</label>
                <input type="text" class="form-control input-sm" name="address" placeholder="请输入邮寄地址" value="${(employee.address)!""}">
            </div>
        </div>
        <label>选择角色</label>
        <div class="form-group row">
            <div>
                <div class="col-md-4">
                    <select multiple="multiple" style="width: 198px;height: 400px;" class="form-control input-sm"
                            id="role_option_left">
                    <#if roles??>
                        <#list roles as item>
                            <option value="${item.id}">${item.sn}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
                <div class="col-md-2">
                    <a class="btn btn-info btn-sm" style="margin-top:160px;" id="role_moveSelectedRight">右移选定</a>
                    <br/>
                    <a class="btn btn-danger btn-sm" id="role_moveAllRight">右移全部</a>
                    <br/>
                    <a class="btn btn-info btn-sm" id="role_moveSelectedLeft">左移选定</a>
                    <br/>
                    <a class="btn btn-danger btn-sm" id="role_moveAllLeft">左移全部</a>
                </div>
                <div class="col-md-5">
                    <select multiple="multiple" style="width: 198px;height: 400px;" class="form-control input-sm"
                            id="role_option_right" name="roleIds">
                    <#if employee?? && employee.roles??>
                        <#list employee.roles as per>
                            <option value="${per.id}">${per.sn}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-md-4"></div>
        <button type="submit" class="btn btn-warning btn-sm" style="width: 120px;">添加/修改</button>
    </form>
</div>
<!--添加/修改-->

</body>
</html>