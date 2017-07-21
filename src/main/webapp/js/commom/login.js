/**
 * Created by HuaiBin on 2017/5/26.
 */
//输入非空验证
$(function () {
    $("#login_form").validate({
        invalidHandler: function () {
            layer.tips('用户名和密码不能为空！', '#show_error', {
                tips: [2, '#FF5722'],
                time: 3000
            });
        },
        errorElement: "span",
        highlight: function (element) {
            $(element).closest(".form-group").addClass("has-error");
        },
        unhighlight: function (element) {
            $(element).closest(".form-group").removeClass("has-error");
        },
        rules: {
            userName: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            userName: {
                required: ""
            },
            password: {
                required: ""
            }
        }
    });
});