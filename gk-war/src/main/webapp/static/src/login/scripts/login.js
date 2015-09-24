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
                   account:tel,  //zou
                   password:pwd  //
                },
                success: function (res) {
                    console.log(res);
                    if (res.rtnCode == '0000000') {
                        window.location.assign('http://'+window.location.host+'/index/index.jsp');
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
        Dom.btnLoginRegister.click(function () {
            var reg_tel = Dom.telNumber.val();
            var reg_code = Dom.captchaCode.val();
            var reg_pwd = Dom.regPassword.val();
            var reg_conform_pwd = Dom.regPasswordConfirm.val();
            var isPhone = /^0?1[3578]\d{9}$/;
            if (reg_tel.trim() == '') {
                Dom.errorTip2.text('手机号不能为空').fadeIn();
                return
            }
            if (!isPhone.test(reg_tel.trim())) {
                Dom.errorTip2.text('账号输入有误').fadeIn();
                return
            }
            if (reg_code.trim() == '') {
                Dom.errorTip2.text('验证码不能为空').fadeIn();
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
                url: '',
                type: 'post',
                dataType: 'json',
                data: {},
                success: function (res) {
                    console.log(res);
                    if (res.rtnCode == '0000000') {
                        //window.location.href
                    }
                }
            })
        });

    })
});
