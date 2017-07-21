<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PayRoll-个人中心</title>

    <link rel="icon" href="/images/logo.ico">

    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <link rel="stylesheet" href="/css/index.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(function () {
            //点击左侧菜单，将相应的url给iframe
            $(".content .mp_menu_item").click(function () {
                var url = $(this).data("url");
                $(this).siblings().removeClass("active");
                $(this).addClass("active");

                //赋值
                $("#mp_main").prop("src", $(this).data("url"));

                //改变路径导航
                var text=$(this).text();
                var parentName=$(this).attr("parentName");
                $("#bread_1").text(parentName);
                $("#bread_2").text(text);
                $("#bread_2").prop("href",url);
            });

            //控制菜单的显示
            $.each($(".mp_menu_item"), function (index, item) {
                //获取tag
                var tag=$(item).attr("tag");
                //获取parentTag
                var parentTag=$(item).attr("parentTag");
                if(tag!=parentTag){
                    $(item).remove();
                }
            });

        });

    </script>

    <style>
    </style>

</head>
<body>

<div class="box">

    <!--banner开始-->
    <div class="banner">
        <div class="title color_2">
            <img src="/images/logo.png">
            <span>PayRoll-工资管理系统</span>
        </div>
    </div>
    <!--banner结束-->

    <!--content开始-->
    <!--左边导航栏开始开始-->
    <div class="content">
        <div class="left_menu">
            <div class="list-group">
            <#if roots??>
                <#list roots as root>
                    <a href="javascript:;" class="list-group-item list-group-item-warning mp_parent">
                        <span class="glyphicon glyphicon-plus"></span>&nbsp;${root.name}
                    </a>
                    <#if menus??>
                        <#list menus as menu>
                            <a tag="${menu.parent.sn}" href="javascript:;" class="list-group-item mp_menu_item"
                               parentTag="${root.sn}" parentName="${root.name}"
                               data-url="${menu.url}">${menu.name}</a>
                        </#list>
                    </#if>
                </#list>
            </#if>
            </div>
        </div>
        <!--左边导航栏开始结束-->

        <!--右边内容开始-->
        <div class="right_content">

            <!--路径导航-->
            <div style="padding-left: 10px;">
                <ol class="breadcrumb">
                    <li class="active" id="bread_1">欢迎你</li>
                    <li class="active color_6" id="bread_2">开始页</li>
                </ol>
            </div>
            <!--路径导航-->

            <iframe name="right" id="mp_main" src="/welcome.html" frameborder="no" scrolling="auto"
                    width="100%"
                    height="590" style="padding-left: 10px;"
                    allowtransparency="true"></iframe>
        </div>
        <!--右边内容结束-->
    </div>
    <!--content结束-->

</div>
</body>
</html>