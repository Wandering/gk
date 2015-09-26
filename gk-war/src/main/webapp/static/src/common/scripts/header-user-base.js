/**
 * Created by pdeng on 15/9/26.
 */
define(function (require) {
    var $ = require('$');

    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) {
            return unescape(arr[2]);
        } else {
            return null;
        }
    }

    $(function () {
        //判断用户是否登陆
        if (getCookie('gkuser')) {
            $('.log-reg').removeClass('hide');
        } else {
            //获取用户信息
            $.ajax({
                url: '/info/getUserAccount.do',
                dataType: 'json',
                type: 'get',
                data: {},
                success: function (res) {
                    if (res.rtnCode == '0000000') {
                        var userData = res.bizData;
                        var name = userData.name;
                        if (name == null || name == '') {
                            name == userData.account;
                        }
                        $('.username').text(name);
                        if (userData.icon == null || userData.icon == '') {
                            console.log(userData.icon);
                            var userImg = '/static/dist/common/images/icon_default.png';
                        }
                        $('.user-avatar').attr('src', userImg);
                        $('.user-info-list').fadeIn();
                    }
                }
            });
        }
    });


});