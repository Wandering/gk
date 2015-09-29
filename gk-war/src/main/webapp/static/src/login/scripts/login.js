define(function (require) {
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
    $(function () {
        //登陆
        Dom.tabLogin.click(function () {
            $(this).addClass('active').siblings().removeClass('active');
            Dom.registerInput.hide();
            Dom.loginInput.fadeIn();
            Dom.errorTip1.text('');
            Dom.errorTip2.text('');
        });
        Dom.btnLogin.click(function () {
            var tel = Dom.loginAccount.val();
            var pwd = Dom.loginPassword.val();
            Dom.errorTip1.text('');
            Dom.errorTip2.text('');
            var isPhone = /^0?1[3578]\d{9}$/;
            if (tel.trim() == '') {
                Dom.errorTip1.text('请输入账号').fadeIn();
                return
            }
            if (!isPhone.test(tel.trim())) {
                Dom.errorTip1.text('账号输入有误').fadeIn();
                return
            }
            if (pwd.trim() == '') {
                Dom.errorTip1.text('密码不能为空').fadeIn();
                return
            }
            if (pwd.trim().length < 6 || tel.trim().length > 16) {
                Dom.errorTip1.text('密码输入有误').fadeIn();
                return false;
            } else {
                Dom.errorTip1.text('');
            }
            $.ajax({
                url: '/login/login.do',
                type: 'post',
                dataType: 'json',
                data: {
                    account: tel,  //18717817817
                    password: pwd  //123123
                },
                success: function (res) {
                    console.log(res);
                    if (res.rtnCode == '0000000') {
                        window.location.assign('http://' + window.location.host + '/index.jsp');
                    } else {
                        Dom.errorTip1.text(res.msg);
                    }
                }
            })
        });
        //注册
        Dom.tabRegister.click(function () {
            $(this).addClass('active').siblings().removeClass('active');
            Dom.registerInput.fadeIn();
            Dom.loginInput.hide();
        });
        Dom.codeText.click(function () {
            Dom.errorTip2.text('');
            var reg_tel = Dom.telNumber.val().trim();
            var isPhone = /^0?1[3578]\d{9}$/;
            if (reg_tel.trim() == '') {
                Dom.errorTip2.text('手机号不能为空').fadeIn();
                return
            }
            if (!isPhone.test(reg_tel.trim())) {
                Dom.errorTip2.text('手机号输入有误').fadeIn();
                return
            }
            $.ajax({
                url: '/register/confirmAccount.do',
                type: 'post',
                dataType: 'json',
                data: {
                    type: 0,//0.注册标志
                    account: reg_tel
                },
                success: function (res) {
                    if (res.rtnCode !== '0000000') {
                        Dom.errorTip2.text(res.msg).fadeIn();
                    } else {
                        //获取验证码
                        $.ajax({
                            url: '/captcha/captcha.do',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                account: reg_tel,//用户账号
                                type: 0 //注册为时type=0，找回密码时type=1
                            },
                            success: function (res) {
                                if (res.rtnCode == '0000000') {
                                    Dom.codeText.css('background-color', '#ccc').attr('disabled', true);
                                    var s = (JSON.parse(res.bizData)).time;
                                    var timer = setInterval(function () {
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
        });
        Dom.btnLoginRegister.click(function () {
            var reg_code = Dom.captchaCode.val().trim();
            var reg_pwd = Dom.regPassword.val().trim();
            var reg_conform_pwd = Dom.regPasswordConfirm.val().trim();
            var reg_tel = Dom.telNumber.val().trim();
            var isPhone = /^0?1[3578]\d{9}$/;
            if (reg_tel.trim() == '') {
                Dom.errorTip2.text('手机号不能为空').fadeIn();
                return
            }
            if (!isPhone.test(reg_tel.trim())) {
                Dom.errorTip2.text('手机号输入有误').fadeIn();
                return
            }
            if (reg_code.trim() == '') {
                Dom.errorTip2.text('验证码不能为空').fadeIn();
                return
            }
            if (reg_code.trim().length > 6) {
                Dom.errorTip2.text('验证码输入有误').fadeIn();
                return
            }
            if (reg_pwd.trim() == '' || reg_conform_pwd.trim() == '') {
                Dom.errorTip2.text('密码不能为空').fadeIn();
                return
            }
            if (reg_pwd.trim() != reg_conform_pwd.trim()) {
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
                    account: reg_tel,//用户账号
                    captcha: reg_code,//验证码
                    password: reg_pwd//密码
                },
                success: function (res) {
                    console.log(res);
                    if (res.rtnCode == '0000000') {
                        window.location.assign('http://' + window.location.host + '/user/personal-info.jsp');
                        //window.location.href = 'http://'+window.location.host+'/login/login.jsp';
                    }else{
                        Dom.errorTip2(res.msg);
                    }
                }
            })
        });

    })
});
