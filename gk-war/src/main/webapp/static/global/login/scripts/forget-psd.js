define(function (require) {
    var $ = require('$');
    $(function () {
        //找回密码
        $('.code-text').click(function () {
            var tel = $('.tel').val().trim();
            var isPhone = /^0?1[3578]\d{9}$/;
            if (tel.trim() == '') {
                $('.error-tip2').text('手机号不能为空').fadeIn();
                return
            }
            if (!isPhone.test(tel.trim())) {
                $('.error-tip2').text('手机号输入有误').fadeIn();
                return
            }
            $.ajax({
                url: '/register/confirmAccount.do',
                type: 'post',
                dataType: 'json',
                data: {
                    type:1,//1找回密码标志
                    account: tel
                },
                success: function (res) {
                    if (res.rtnCode == '0000000') {
                        //获取验证码
                        $.ajax({
                            url: '/captcha/captcha.do',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                account: tel,//用户账号
                                type: 1 //注册为时type=0，找回密码时type=1
                            },
                            success: function(res) {
                                if (res.rtnCode == '0000000') {
                                    $('.code-text').css('background-color', '#ccc').attr('disabled', true);
                                    var s = (JSON.parse(res.bizData)).time;
                                    var timer = setInterval(function() {
                                        s--;
                                        $('.code-text').text(s + '秒后可重新获取');
                                        if (s <= 0) {
                                            clearInterval(timer);
                                            $('.code-text').text('重新获取').css('background-color', '#52d09c');
                                            $('.code-text').attr('disabled', false)
                                        }
                                    }, 1000);
                                } else {
                                    $('.errorTip2').text(res.msg).fadeIn();
                                }
                            }
                        });
                    }else{
                        $('.error-tip2').text(res.msg).fadeIn();
                    }
                }
            });
        });
        function loginCheck(){
            //登陆验证
            var tel = $('.tel').val();
            var code = $('.code').val();
            var newPsd = $('.new-psd').val();
            var confirmPsd = $('.confirm-psd').val();
            var isPhone = /^0?1[3578]\d{9}$/;
            if (tel.trim() == '') {
                $('.error-tip2').text('手机号不能为空').fadeIn();
                return
            }
            if (!isPhone.test(tel.trim())) {
                $('.error-tip2').text('手机号输入有误').fadeIn();
                return
            }
            if (code.trim() == '') {
                $('.error-tip2').text('验证码不能为空').fadeIn();
                return
            }
            if (code.trim().length > 6) {
                $('.error-tip2').text('验证码输入有误').fadeIn();
                return
            }
            if (newPsd.trim() == '' || confirmPsd.trim() == '') {
                $('.error-tip2').text('密码不能为空').fadeIn();
                return
            }
            if (newPsd.trim().length< 6 || newPsd.trim().length>16 || isNaN(newPsd.trim())) {
                $('.error-tip2').text('密码不能小于6位大于16位，且只能为数字').fadeIn();
                return
            }
            if (newPsd.trim().length< 6 || newPsd.trim().length>16 || isNaN(newPsd.trim())) {
                $('.error-tip2').text('密码不能小于6位大于16位，且只能为数字').fadeIn();
                return
            }

            if (newPsd.trim() != confirmPsd.trim()) {
                $('.error-tip2').text('两次密码输入不一致').fadeIn();
                return
            } else {
                $('.error-tip2').text('');
            }
            $.ajax({
                url: '/register/retrievePassword.do',
                type: 'post',
                dataType: 'json',
                data: {
                    account:tel,//用户账号
                    captcha:code,//验证码
                    password:newPsd//密码
                },
                success: function (res) {
                    if (res.rtnCode == '0000000') {
                        console.log(res);
                        window.location.href = "http://"+window.location.host+'/login/login.jsp'
                    }else{
                        $('.error-tip2').text(res.msg);
                    }
                }
            })
        }
        $('.btn-login').click(function () {
            loginCheck();
        });
        $('.confirm-psd').keydown(function() {
            if (event.keyCode == 13) {
                loginCheck();
            }
        });
    })
});
