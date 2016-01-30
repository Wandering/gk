define(['commonjs','tips'], function (util,tips) {
    $(function () {
        // 登录提交
        $('#submit-btn').on('click', function () {
            var loginPhoneV = $.trim($('#login-phone').val()),
                loginPwdV = $.trim($('#login-pwd').val());
            if (loginPhoneV == "") {
                tips('#tips', '请输入手机号');
                return false;
            }
            var regMobile = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$/;
            var mobileResult = regMobile.test(loginPhoneV);
            if (mobileResult == false) {
                tips('#tips', '手机号有误,请重新输入');
                return false;
            }
            if (loginPwdV == "") {
                tips('#tips', '请输入密码');
                return false;
            }
            util.ajaxFun(util.INTERFACE_URL.postLogin, 'POST', {
                account: loginPhoneV,
                password: loginPwdV
            }, function (res) {
                if (res.rtnCode === "0000000") {
                    console.log(res);
                    var token = res.bizData.token;
                    var userName = res.bizData.userInfo.name;
                    var icon = res.bizData.userInfo.icon;
                    var vipStatus = res.bizData.userInfo.vipStatus;
                    var subjectType = res.bizData.userInfo.subjectType;
                    var phone = res.bizData.userInfo.account;
                    util.cookie.setCookie("token", token, 4, "");
                    util.cookie.setCookie("isLogin", "true", 4, "");
                    util.cookie.setCookie("userName", userName, 4, "");
                    util.cookie.setCookie("icon", icon, 4, "");
                    util.cookie.setCookie("vipStatus", vipStatus, 4, "");
                    util.cookie.setCookie("subjectType",subjectType, 4, "");
                    util.cookie.setCookie("phone",phone, 4, "");

                    alert(99)
                    window.location.assign('http://' + window.location.host + '/static/index.html');
                } else {
                    tips('#tips', res.msg);
                }
            });
        });
        $('#login-pwd').keydown(function() {
            if (event.keyCode == 13) {
                $('#submit-btn').click();
            }
        });
    });


});



