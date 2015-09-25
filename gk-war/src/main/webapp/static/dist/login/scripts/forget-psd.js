define(function (require) {
    var $ = require('$');
    $(function () {
        //找回密码
        $('.btn-login').click(function () {
            console.log(23);
            var tel = $('.tel').val();
            var code = $('.code').val();
            var newPsd = $('.new-psd').val();
            var confirmPsd =$('.confirm-psd').val();
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
            if (newPsd.trim() == '' || confirmPsd.trim() =='') {
                $('.error-tip2').text('密码不能为空').fadeIn();
                return
            }
            if (newPsd.trim() != confirmPsd.trim() ) {
                $('.error-tip2').text('两次密码输入不一致').fadeIn();
                return
            } else {
                $('.error-tip2').text('');
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
