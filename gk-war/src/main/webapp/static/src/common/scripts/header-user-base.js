/**
 * Created by pdeng on 15/9/26.
 */
define(function (require, exports, modules) {
    var $ = require('$');

    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    $(function () {
        //判断当前用户cookie是否存在
        if (!GetCookie("gkuser") || GetCookie("gkuser") == '""') {
            console.log('没有登录');
            $('.menu').hide();
            $('.log-reg').show();
        } else {
            console.log('登录状态');
            $.ajax({
                url: '/info/getUserAccount.do',
                dataType: 'json',
                type: 'get',
                data: {},
                success: function (res) {
                    if (res.rtnCode == '0000000') {
                        var userData = res.bizData;
                        $('#accountNum').attr('accountNum',userData.account);
                        var name = userData.name;
                        if (name == null || name == '') {
                            name == userData.account;
                        }
                        $('.username').text(name);
                        var userImg;
                        if (userData.icon == null || userData.icon == '') {
                            userImg = '/static/dist/common/images/icon_default.png';
                        } else {
                            userImg = userData.icon
                        }
                        $('.user-avatar').attr('src', userImg).fadeIn();
                    }
                }
            });
            $('.user-info-list').show();
        }
        //登出
        $('.menu li:last-child').click(function (event) {
            event.stopPropagation();
            event.preventDefault();
            $.ajax({
                type: 'get',
                url: '/login/logout.do',
                success: function (res) {
                    console.log(res);
                    if (res.rtnCode == '0000000') {
                        window.location.href = '/index.jsp';
                    } else {
                        alert(res.msg);
                    }
                }
            });
        });
    })
});