define(function(require) {
    var $ = require('$');
    var Dom = {
        tabLogin: $('.tab-login'),
        tabRegister: $('.tab-register'),
        registerInput: $('.register-input'),
        errorTip1: $('.error-tip1'),
        errorTip2: $('.error-tip2'),
        loginInput: $('.login-input'),
        loginAccount: $('.login-account'),
        loginPassword: $('.login-password'),
        forgetPsd: $('.forget-psd'),
        btnLogin: $('.btn-login'),
        btnLoginRegister: $('.btn-login-register'),
        telNumber: $('.tel-number'),
        captchaCode: $('.captcha-code'),
        codeText: $('.code-text'),
        regPassword: $('.reg-password'),
        regPasswordConfirm: $('.reg-password-confirm')
    };
    // 登陆检测
    function loginCheck() {
        var tel = Dom.loginAccount.val();
        var pwd = Dom.loginPassword.val();
        Dom.errorTip1.text('');
        Dom.errorTip2.text('');
        var isPhone = /^0?1[3578]\d{9}$/;
        if ($.trim(tel) == '') {
            Dom.errorTip1.text('请输入账号').fadeIn();
            return
        }
        if (!isPhone.test($.trim(tel))) {
            Dom.errorTip1.text('账号输入有误').fadeIn();
            return
        }
        if ($.trim(pwd) == '') {
            Dom.errorTip1.text('密码不能为空').fadeIn();
            return
        }
        if ($.trim(pwd).length < 6 || $.trim(pwd).length > 16 || isNaN($.trim(pwd))) {
            Dom.errorTip1.text('密码输入有误').fadeIn();
            console.log('长度：'+$.trim(pwd).length+'----'+(!isNaN($.trim(pwd))));
            return false;
        } else {
            Dom.errorTip1.text('');
        }
        $.ajax({
            url: '/login/login.do',
            type: 'post',
            dataType: 'json',
            data: {
                account: tel, //18717817817
                password: pwd //123123
            },
            success: function(res) {
                console.log(res);
                if (res.rtnCode == '0000000') {
                    window.location.assign('http://' + window.location.host + '/index.jsp');
                } else {
                    Dom.errorTip1.text(res.msg);
                }
            }
        })
    };
    //验证码检测
    function codeCheck(){
        Dom.errorTip2.text('');
        var reg_tel = $.trim(Dom.telNumber.val());
        var isPhone = /^0?1[3578]\d{9}$/;
        if ($.trim(reg_tel) == '') {
            Dom.errorTip2.text('手机号不能为空').fadeIn();
            return
        }
        if (!isPhone.test($.trim(reg_tel))) {
            Dom.errorTip2.text('手机号输入有误').fadeIn();
            return
        }
        $.ajax({
            url: '/register/confirmAccount.do',
            type: 'post',
            dataType: 'json',
            data: {
                type: 0, //0.注册标志
                account: reg_tel
            },
            success: function(res) {
                if (res.rtnCode !== '0000000') {
                    Dom.errorTip2.text(res.msg).fadeIn();
                } else {
                    //获取验证码
                    $.ajax({
                        url: '/captcha/captcha.do',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            account: reg_tel, //用户账号
                            type: 0 //注册为时type=0，找回密码时type=1
                        },
                        success: function(res) {
                            if (res.rtnCode == '0000000') {
                                Dom.codeText.css('background-color', '#ccc').attr('disabled', true);
                                var s = (JSON.parse(res.bizData)).time;
                                var timer = setInterval(function() {
                                    s--;
                                    Dom.codeText.text(s + '秒后可重新获取');
                                    if (s <= 0) {
                                        clearInterval(timer);
                                        Dom.codeText.text('重新获取').css('background-color', '#52d09c');
                                        Dom.codeText.attr('disabled', false)
                                    }
                                }, 1000);
                            } else {
                                Dom.errorTip2.text(res.msg).fadeIn();
                            }
                        }
                    });
                }
            }
        });
    }
    // 注册检测
    function registerCheck(){
        var reg_code = $.trim(Dom.captchaCode.val());
        var reg_pwd = $.trim(Dom.regPassword.val());
        var reg_conform_pwd = $.trim(Dom.regPasswordConfirm.val());
        var reg_tel = $.trim(Dom.telNumber.val());
        var isPhone = /^0?1[3578]\d{9}$/;
        if ($.trim(reg_tel) == '') {
            Dom.errorTip2.text('手机号不能为空').fadeIn();
            return
        }
        if (!isPhone.test($.trim(reg_tel))) {
            Dom.errorTip2.text('手机号输入有误').fadeIn();
            return
        }
        if ($.trim(reg_code) == '') {
            Dom.errorTip2.text('验证码不能为空').fadeIn();
            return
        }
        if ($.trim(reg_code).length > 6) {
            Dom.errorTip2.text('验证码输入有误').fadeIn();
            return
        }
        if ($.trim(reg_pwd) == '' || $.trim(reg_conform_pwd) == '') {
            Dom.errorTip2.text('密码不能为空').fadeIn();
            return
        }
        if ($.trim(reg_pwd).length< 6 || $.trim(reg_pwd).length>16 || isNaN($.trim(reg_pwd))) {
            Dom.errorTip2.text('密码不能小于6位大于16位，且只能为数字').fadeIn();
            return
        }
        if ($.trim(reg_pwd) != $.trim(reg_conform_pwd)) {
            Dom.errorTip2.text('两次密码输入不一致').fadeIn();
            return
        } else {
            Dom.errorTip2.text('');
        }
        $.ajax({
            url: '/register/account.do',
            type: 'post',
            dataType: 'json',
            data: {
                account: reg_tel, //用户账号
                captcha: reg_code, //验证码
                password: reg_pwd //密码
            },
            success: function(res) {
                console.log(res);
                if (res.rtnCode == '0000000') {
                    window.location.assign('http://' + window.location.host + '/user/personal-info.jsp');
                    //window.location.href = 'http://'+window.location.host+'/login/login.jsp';
                } else {
                    Dom.errorTip2.text(res.msg);
                }
            }
        })
    }
    $(function() {
        //登陆切换
        Dom.tabLogin.click(function() {
            $(this).addClass('active').siblings().removeClass('active');
            Dom.registerInput.hide();
            Dom.loginInput.fadeIn();
            Dom.errorTip1.text('');
            Dom.errorTip2.text('');
        });
        //注册切换
        Dom.tabRegister.click(function() {
            $(this).addClass('active').siblings().removeClass('active');
            Dom.registerInput.fadeIn();
            Dom.loginInput.hide();
        });
        // 登陆
        Dom.btnLogin.click(function() {
            loginCheck();
        });
        Dom.loginPassword.keydown(function() {
            if (event.keyCode == 13) {
                loginCheck();
            }
        });
        // 注册验证码
        Dom.codeText.click(function() {
            codeCheck();
        });
        // 注册
        Dom.btnLoginRegister.click(function() {
            registerCheck();
        });
        Dom.regPasswordConfirm.keydown(function() {
            if (event.keyCode == 13) {
                registerCheck();
            }
        });
    })
});
