define(function (require, exports, modules) {
    var $ = require('$');

    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    $(function () {
        //判断当前用户cookie是否存在
        if (!GetCookie("gxuser") || GetCookie("gxuser") == '""') {
            console.log('没有登录11');
            $('.menu').hide();
            $('#log-reg').show();
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
                            userImg = 'http://cdn.gaokao360.net/static/global/common/images/icon_default.png';
                        } else {
                            userImg = userData.icon
                        }
                        $('.user-avatar').attr('src', userImg).fadeIn();
                    }
                }
            });
            $('.user-info-list').show();
        }
    })
});
