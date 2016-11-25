/**
 * saas找回密码
 * @type {{init: ForgotPassword.init, fetchSmsCode: ForgotPassword.fetchSmsCode, eventSendSms: ForgotPassword.eventSendSms, eventForgotPassword: ForgotPassword.eventForgotPassword, validateFormItem: ForgotPassword.validateFormItem}}
 */
var ForgotPassword = {
    init: function () {
        this.bindEvents();
        //this.eventForgotPassword();
        //this.eventSendSms();
    },
    bindEvents: function () {
        var that = this;
        $('#phone').blur(function () {
            that.fetchImgCode();
        });
        $('#verification-code-img').keyup(function () {
            that.verifyMsgCode();
        });
        $('#verification-btn-msg').click(function () {
            that.fetchMsgCode();
        });
        $('#submit-btn').click(function () {
            var paramData = that.verifySubmit();
            if (paramData) {
                that.findPwdSubmit(paramData);
            }
        });
    },
    fetchImgCode: function () {
        var regMobile = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$/;
        var $phone = $.trim($('#phone').val());
        if ($.trim($('#phone').val()).length === 0) {
            return false;
        }
        if (regMobile.test($phone) === false) {
            return false;
        }
        $('#security-box').removeClass('dh');
        $('#verification-btn-img').attr('src', '/captcha/imageCaptcha.do?account=' + $phone + '&time=' + Date.parse(new Date()));
    },
    verifyMsgCode: function () {
        var $msgCode = $.trim($('#verification-code-img').val());
        if ($msgCode.length == 4) {
            $('#verification-btn-msg').css({
                'background': '#FF872F',
                'cursor': 'pointer'
            }).attr('disabled', false);
        } else {
            $('#verification-btn-msg').css({
                'background': '#ccc',
                'cursor': 'not-allowed'
            }).attr('disabled', true);
        }
    },
    fetchMsgCode: function () {
        Common.ajaxFun('/captcha/captcha.do', 'GET', {
            type: 1,
            account: $.trim($('#phone').val()),
            capText: $.trim($('#verification-code-img').val())
        }, function (res) {
            var $msgBtn = $('#verification-btn-msg');
            $msgBtn.attr('disabled', true);
            if (res.rtnCode === '0000000') {
                var s = (JSON.parse(res.bizData)).time;
                if (s != 60) {
                    layer.msg('短信验证码60s内不能重复发送');
                }
                var timer = setInterval(function () {
                    s--;
                    $msgBtn.text(s + 's后获取');
                    if (s <= 0) {
                        window.clearInterval(timer);
                        $msgBtn.text('重新获取').css({
                            'background-color': '#FF872F',
                            'color': '#fff'
                        });
                        $msgBtn.attr('src', '/captcha/imageCaptcha.do?account=' + $.trim($('#phone').val()) + '&time=' + Date.parse(new Date()));
                        $msgBtn.attr('disabled', false);
                    }
                }, 1000);
            } else {
                layer.msg(res.msg);
            }
        });
    },
    verifySubmit: function () {
        var $phone = $.trim($('#phone').val());
        var regMobile = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$/;
        var $resetPwd = $('#reset-pwd').val();
        var $confirm = $('#confirm').val();
        if ($phone.length === 0) {
            layer.msg('手机号不能为空');
            return false;
        }
        if (regMobile.test($phone) === false) {
            layer.msg('手机号有误');
            return false;
        }
        if ($.trim($('#verification-code-img').val()).length != 4) {
            layer.msg('图形验证码输入有误');
            return false;
        }
        if ($.trim($('#verification-code-msg').val()).length != 6) {
            layer.msg('短信验证码输入有误');
            return false;
        }
        if ($.trim($resetPwd).length < 6 || $.trim($resetPwd) == '') {
            layer.msg('重置密码输入有误');
            return false;
        }
        if ($.trim($confirm).length < 6 || $.trim($confirm) == '') {
            layer.msg('确认密码输入有误');
            return false;
        }
        if ($.trim($resetPwd) != $.trim($confirm)) {
            layer.msg('两次密码输入不一致');
            return false;
        }
        return {
            account: $phone,
            captcha: $.trim($('#verification-code-msg').val()),
            password: $.md5($.trim($resetPwd))
        }
    },
    findPwdSubmit: function (data) {
        Common.ajaxFun('/expert/admin/login/retrievePassword.do', 'POST', data, function (res) {
            if(res.rtnCode === '0000000'){
                window.location.href = '/';
            }else{
                layer.msg(res.msg);
            }
        })
    }
};
ForgotPassword.init();





