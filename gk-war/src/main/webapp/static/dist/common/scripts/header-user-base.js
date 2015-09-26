/**
 * Created by pdeng on 15/9/26.
 */
define(function (require) {
    var $ = require('$');
    //获取用户信息
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) {
            return unescape(arr[2]);
        } else {
            return null;
        }
    }

    //if (getCookie('gkuser')) {
    //    $('.log-reg').show();
    //} else {
        $.ajax({
            url: '/info/getUserAccount.do',
            dataType: 'json',
            type: 'get',
            data: {},
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    var userData = res.bizData;
                    $('.username').text(userData.name);
                    if (userData.icon == null || userData.icon == '') {
                        console.log(userData.icon);
                        var userImg = '/static/dist/common/images/icon_default.png';
                    }
                    $('.user-avatar').attr('src', userImg);
                    $('.user-info-list').fadeIn();
                }
            }
        });

    //}


});