/**
 * Created by pdeng on 15/9/26.
 */
define(function (require) {
    var $ = require('$');
    $.ajax({
        url: '/register/account.do',
        dataType: 'json',
        type: 'post',
        data: {
            account: '18710842703',
            captcha: '543553',
            password: '123123'
        },
        success: function (res) {
            console.log(res);
        }
    })
});