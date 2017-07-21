<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-角色查看</title>

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

            //禁用所有表单
            $(":input").prop("disabled","disabled");

            //去除两边相同option
            //权限
            var arr=$.map($("#permission_option_right option"),function (item) {
                return $(item).prop("value");
            });

            $.each($("#permission_option_left option"),function (index,item) {
                if( $.inArray($(item).prop("value"),arr)>=0){
                    $(item).remove();
                }
            });
            //菜单
            var arr=$.map($(".menu_option_right option"),function (item) {
                return $(item).prop("value");
            });

            $.each($(".menu_option_left option"),function (index,item) {
                if( $.inArray($(item).prop("value"),arr)>=0){
                    $(item).remove();
                }
            })
        });


    </script>

</head>
<body>

<!--添加/修改-->
<div class="col-md-6 center-block" style="margin-left: 20%;">
    <form method="post" action="roleSaveOrUpdate.do" id="updateForm">
        <input type="hidden" name="id" value="${(role.id)!""}">
        <div class="row">
            <div class="form-group col-md-10">
                <label for="name">角色名称</label>
                <input type="text" class="form-control input-sm" id="name" name="name" placeholder="请输入角色名称" value="${(role.name)!""}">
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-10">
                <label for="sn">角色编码</label>
                <input type="text" class="form-control input-sm" id="sn" name="sn" placeholder="请输入角色编码" value="${(role.sn)!""}">
            </div>
        </div>
        <label>已选权限</label>
        <div class="form-group row">
            <div>
                <div class="col-md-4">
                    <select multiple="multiple" style="width: 198px;height: 400px;" class="form-control input-sm" id="permission_option_left">
                    <#if all_permissions??>
                        <#list all_permissions as item>
                            <option value="${item.id}">${item.des}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
                <div class="col-md-2">
                    <a class="btn btn-info btn-sm" style="margin-top:160px;" id="permission_moveSelectedRight">右移选定</a>
                    <br/>
                    <a class="btn btn-danger btn-sm" id="permission_moveAllRight">右移全部</a>
                    <br/>
                    <a class="btn btn-info btn-sm" id="permission_moveSelectedLeft">左移选定</a>
                    <br/>
                    <a class="btn btn-danger btn-sm" id="permission_moveAllLeft">左移全部</a>
                </div>
                <div class="col-md-5">
                    <select multiple="multiple" style="width: 198px;height: 400px;" class="form-control input-sm" id="permission_option_right" name="pers">
                    <#if role?? && role.permissions??>
                            <#list role.permissions as per>
                                <option value="${per.id}">${per.des}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </div>
        </div>
        <label>已选菜单</label>
        <div class="form-group row">
            <div>
                <div class="col-md-4">
                    <select multiple="multiple" style="width: 198px;height: 400px;" class="form-control input-sm menu_option_left">
                    <#if all_systemMenus??>
                        <#list all_systemMenus as item>
                            <option value="${item.id}">${item.name}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
                <div class="col-md-2">
                    <a class="btn btn-info btn-sm" style="margin-top:160px;" id="menu_moveSelectedRight">右移选定</a>
                    <br/>
                    <a class="btn btn-danger btn-sm" id="menu_moveAllRight">右移全部</a>
                    <br/>
                    <a class="btn btn-info btn-sm" id="menu_moveSelectedLeft">左移选定</a>
                    <br/>
                    <a class="btn btn-danger btn-sm" id="menu_moveAllLeft">左移全部</a>
                </div>
                <div class="col-md-5">
                    <select multiple="multiple" style="width: 198px;height: 400px;" class="form-control input-sm menu_option_right" name="menus">
                    <#if role?? && role.systemMenus??>
                        <#list role.systemMenus as menu>
                            <option value="${menu.id}">${menu.name}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-md-4"></div>
        <button type="submit" class="btn btn-warning btn-sm" style="width: 120px;display: none">添加/修改</button>
    </form>
</div>
<!--添加/修改-->

</body>
</html>