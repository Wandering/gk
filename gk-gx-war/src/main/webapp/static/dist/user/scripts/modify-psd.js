define(function (require) {
    var $ = require('$');
    require('header-user');
    $(document).ready(function () {
        $.get('/info/getUserAccount.do', function (res) {
            if (res.rtnCode == '0000000') {
                $('#tel').text(res.bizData.account);
            }
        })
    });

//修改密码
    $('.btn-submit').click(function () {
        var currentPsd = $('#current-psd');
        var newPsd = $('#new-psd');
        var confirmPsd = $('#confirm-psd');
        if (currentPsd.val() == '') {
            currentPsd.addClass('error-bd').next().text('当前密码不能为空');
            return
        } else {
            currentPsd.removeClass('error-bd').next().text('');
        }
        if ($.trim(currentPsd.val()).length < 16  && $.trim(currentPsd.val()).length > 6) {
            currentPsd.addClass('error-bd').next().text('密码输入有误，6-16位');
            return
        }
        if (newPsd.val() == '') {
            newPsd.addClass('error-bd').next().text('新密码不能为空');
            return
        } else {
            newPsd.removeClass('error-bd').next().text('');
        }
        if ($.trim(newPsd.val()).length < 16 && $.trim(newPsd.val()).length > 6) {
            newPsd.addClass('error-bd').next().text('新输入有误，6-16位');
            return
        }
        if (confirmPsd.val() == '') {
            confirmPsd.addClass('error-bd').next().text('确认密码不能为空');
            return
        } else {
            confirmPsd.removeClass('error-bd').next().text('');
        }
        if ($.trim(confirmPsd.val()).length < 16 && $.trim(confirmPsd.val()).length > 6) {
            confirmPsd.addClass('error-bd').next().text('新输入有误，6-16位');
            return
        }
        if ($.trim(confirmPsd.val()) != $.trim(newPsd.val())) {
            confirmPsd.addClass('error-bd').next().text('两次密码输入不一致');
            newPsd.addClass('error-bd').next().text('两次密码输入不一致');
            return
        }
        $.ajax({
            url: '/info/modifyPassword.do',
            dataType: 'json',
            type: 'post',
            data: {
                oldPassword: currentPsd.val(),//旧密码
                password: newPsd.val()//新密码
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    window.location.href = 'http://' + window.location.host + '/login/login.jsp';
                } else {
                    currentPsd.addClass('error-bd').next().text(res.msg);
                }
            }
        })
    });

});
