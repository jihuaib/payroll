<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-员工信息维护</title>

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

        //切换导航栏
        $(function () {

            $("#banner li").click(function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                if ($(this).index() == 0) {
                    $("#bind_email_box").removeClass("hidden");
                    $("#update_information_box").addClass("hidden");
                    $("#update_password_box").addClass("hidden");
                }
                if ($(this).index() == 1) {
                    $("#bind_email_box").addClass("hidden");
                    $("#update_information_box").removeClass("hidden");
                    $("#update_password_box").addClass("hidden");
                }
                if ($(this).index() == 2) {
                    $("#bind_email_box").addClass("hidden");
                    $("#update_information_box").addClass("hidden");
                    $("#update_password_box").removeClass("hidden");
                }
            });
        });

        $(function () {
            var updateModal;

            //点击绑定邮箱弹出绑定邮箱弹出框
            $("#bind_email_btn").click(function () {
                layer.open({
                    title: "绑定邮箱",
                    area: ['400px', '200px'],
                    type: 1,
                    content: $('#bind_email_modal')
                });
            });

            $("#update_password_btn").click(function () {
                updateModal=layer.open({
                    title: "修改密码",
                    area: ['400px', '400px'],
                    type: 1,
                    content: $('#update_password_modal')
                });
            });

            //
            $("#send_code_btn").click(function () {
                var _this=$(this);
                _this.addClass("disabled");
                var time=4;
                //ajax异步发送表单
                $.ajax({
                    type:"post",
                    url:"/sendUpdatePasswordCode.do",
                    dataType:"json",
                    success:function (data) {
                        if (data.success) {
                           layer.msg("发送成功!");
                            var timer=window.setInterval(function () {
                                _this.text((--time)+"s后重新发送");
                                if(time<0){
                                    _this.removeClass("disabled");
                                    _this.text("重新发送");
                                    window.clearInterval(timer);
                                }
                            },1000);
                        } else {
                            layer.msg(data.message);
                            _this.removeClass("disabled");
                        }
                    }
                });
            });


            //将表单变为ajax表单
            $("#bind_email_form").ajaxForm({
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.alert("发送成功!请进入邮件验证绑定！", function (index) {
                            window.location.reload();
                        });
                    } else {
                        layer.msg(data.message);
                    }
                }
            });

            $("#update_password_form").ajaxForm({
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.alert("密码修改成功！", function (cc) {
                            layer.close(cc);
                            layer.close(updateModal);
                        });
                    } else {
                        layer.msg(data.message);
                    }
                }
            });

            //修改信息
            $("#query_form").ajaxForm({
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        layer.alert("修改成功", function (aa) {
                            layer.close(aa);
                        });
                    } else {
                        layer.msg(data.message);
                    }
                }
            })
        });

    </script>

</head>
<body>

<!--导航栏-->
<div id="banner">
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="javascript:;">邮箱绑定</a></li>
        <li role="presentation"><a href="javascript:;">基本信息维护</a></li>
        <li role="presentation"><a href="javascript:;">修改密码</a></li>
    </ul>
</div>
<!--导航栏-->

<!--邮箱绑定-->
<div id="bind_email_box">
    <div class="panel panel-danger">
        <h4 style="padding: 10px;" class="text-danger">首次登陆的用户，请一定要进行邮箱绑定。不然无法修改密码和找回密码！</h4>
    </div>
    <div>
        <h4>我的邮箱</h4>
    <#if employee.emailState==0>
        <h5 style="color: red">未绑定</h5>
        <button class="btn btn-info" id="bind_email_btn">立即绑定</button>
    </#if>
    <#if employee.emailState==1>
        <h5 class="glyphicon glyphicon-ok text-info">已绑定</h5>
        <h5>你的绑定邮箱:${employee.email}</h5>
    </#if>
    </div>

</div>
<!--邮箱绑定-->

<!--基本信息维护-->
<div id="update_information_box" class="hidden">
    <div class="panel panel-danger">
        <h4 style="padding: 10px;" class="text-danger">请尽快维护自己的基本信息。主要修改支付方式和用户名！</h4>
    </div>
    <fieldset style=" width:300px;float: left;">
        <legend>不可变更信息</legend>
        <form>
            <div class="form-group">
                <label>用户类型</label>
                <input type="text" class="form-control input-sm" readonly="readonly" value="${employee.userTypeShow}">
            </div>
            <div class="form-group">
                <label>用户状态</label>
                <input type="text" class="form-control input-sm" readonly="readonly" value="${employee.stateShow}">
            </div>
            <div class="form-group">
                <label>工资状况</label>
                <input type="text" class="form-control input-sm" readonly="readonly" value="${employee.salaryShow}">
            </div>
            <div class="form-group">
                <label>角色编码</label>
                <input type="text" class="form-control input-sm" readonly="readonly" value="${employee.rolesShow}">
            </div>
            <div class="form-group">
                <label>邮箱状态</label>
                <input type="text" class="form-control input-sm" readonly="readonly" value="${employee.emailStateShow}">
            </div>
        </form>
    </fieldset>

    <fieldset style=" width:300px;float: left;margin-left: 50px;">
        <legend>可变更信息</legend>
        <form id="query_form" method="post" action="/updateInformation.do">
            <input type="hidden" class="form-control input-sm" name="id" value="${employee.id!""}">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" class="form-control input-sm" name="userName" value="${employee.userName!""}">
            </div>
            <div class="form-group">
                <label>真实姓名</label>
                <input type="text" class="form-control input-sm" name="realName" value="${employee.realName!""}">
            </div>
            <div class="form-group">
                <label>手机号码</label>
                <input type="text" class="form-control input-sm" name="phone" value="${employee.phone!""}">
            </div>
            <div class="form-group">
                <label>银行卡号</label>
                <input type="text" class="form-control input-sm" name="bank" value="${employee.bank!""}">
            </div>
            <div class="form-group">
                <label>邮寄地址</label>
                <input type="text" class="form-control input-sm" name="address" value="${employee.address!""}">
            </div>
            <div class="form-group">
                <label>支付方式</label>
                <select type="text" class="form-control" name="payMethod" style="font-size: 12px;">
                    <option value="0">邮寄</option>
                    <option value="1">直接交易</option>
                    <option value="2">银行支付</option>
                </select>

            <#if employee.payMethod??>
                <script>
                    $("#query_form :input[name='payMethod'] option[value='${employee.payMethod}']").prop("selected", "selected");
                </script>
            </#if>
            </div>
            <button type="submit" class="btn btn-warning" style="width: 80px">修改</button>
        </form>
    </fieldset>
</div>
<!--基本信息维护-->

<!--修改密码-->
<div id="update_password_box" class="hidden">
    <div class="panel panel-danger">
        <h4 style="padding: 10px;" class="text-danger">请尽快维护修改自己的密码。不过修改密码之前需要绑定邮箱！</h4>
    </div>
<#if employee.emailState==0>
    <h5 style="color: red">未绑定邮箱，请先绑定自己的邮箱！</h5>
</#if>
<#if employee.emailState==1>
    <button class="btn btn-info" id="update_password_btn">修改密码</button>
</#if>
</div>
<!--修改密码-->

<!--绑定邮箱弹出框-->
<div id="bind_email_modal" style="display: none;padding-left: 40px;padding-right: 40px;padding-top: 10px;">
    <form method="post" action="/bindEmail.do" id="bind_email_form">
        <div class="form-group">
            <label>我的邮箱</label>
            <input type="text" class="form-control input-sm" name="email" placeholder="请输入邮箱">
        </div>
        <button type="submit" class="btn btn-danger form-control">立即绑定</button>
    </form>
</div>
<!--绑定邮箱弹出框-->

<!--绑定邮箱弹出框-->
<div id="update_password_modal" style="display: none;padding-left: 40px;padding-right: 40px;padding-top: 10px;">
    <form method="post" action="/updatePassword.do" id="update_password_form">
        <div class="form-group">
            <label>我的邮箱</label>
            <input type="text" class="form-control input-sm" readonly="readonly" value="${employee.email!""}">
            <a href="javascript:;" class="btn btn-warning form-control" style="margin-top: 20px;" id="send_code_btn">发送验证码</a>
        </div>
        <div class="form-group">
            <label>验证码</label>
            <input type="text" class="form-control input-sm" name="code" placeholder="请输入验证码">
        </div>
        <div class="form-group">
            <label>新的密码</label>
            <input type="password" class="form-control input-sm" name="password" placeholder="请输入薪密码">
        </div>
        <button type="submit" class="btn btn-danger form-control">修改密码</button>
    </form>
</div>
<!--绑定邮箱弹出框-->

</body>
</html>