define(['commonjs', 'tips','./verification-code.js'], function (util, tips) {

    $(function () {
        // 登录提交
        $('#register-btn').on('click', function () {
            var registerPhoneV = $.trim($('#register-phone').val()),
                verificationCodeV = $.trim($('#verification-code').val()),
                registerPwdV = $.trim($('#register-pwd').val()),
                registerPwdRepeatV = $.trim($('#register-pwd-repeat').val());
            if (registerPhoneV == "") {
                tips('#tips2', '请输入手机号');
                return false;
            }
            var regMobile = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$/;
            var mobileResult = regMobile.test(registerPhoneV);
            if (mobileResult == false) {
                tips('#tips2', '手机号有误,请重新输入');
                return false;
            }
            if (verificationCodeV == "") {
                tips('#tips2', '请输入验证码');
                return false;
            }
            if (verificationCodeV.length != 6) {
                tips('#tips2', '请输入正确的验证码');
                return false;
            }
            if (registerPwdV == "") {
                tips('#tips2', '请输入密码');
                return false;
            }
            if (registerPwdRepeatV == "") {
                tips('#tips2', '请输入确认密码');
                return false;
            }
            if (registerPwdV !== registerPwdRepeatV) {
                tips('#tips2', '两次密码输入不一致');
                return false;
            }

            util.ajaxFun(util.INTERFACE_URL.postRegisterLogin, 'POST', {
                account: registerPhoneV, //用户账号
                captcha: verificationCodeV, //验证码
                password: registerPwdV //密码
            }, function (res) {
                if (res.rtnCode === "0000000") {
                    var token = res.bizData.token;
                    var userName = res.bizData.userInfo.name;
                    var icon = res.bizData.userInfo.icon;
                    var vipStatus = res.bizData.userInfo.vipStatus;
                    var subjectType = res.bizData.userInfo.subjectType;
                    util.cookie.setCookie("token", token, 4, "");
                    util.cookie.setCookie("isLogin", "true", 4, "");
                    util.cookie.setCookie("userName", userName, 4, "");
                    util.cookie.setCookie("icon", icon, 4, "");
                    util.cookie.setCookie("vipStatus", vipStatus, 4, "");
                    util.cookie.setCookie("subjectType",subjectType, 4, "");
                    window.location.assign('http://' + window.location.host + '/user-account-info.html');
                } else {
                    tips('#tips2', res.msg);
                }
            });

        });
    });


})