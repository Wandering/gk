/**
 * Created by pdeng on 15/9/26.
 */
define(function (require, exports, modules) {
    var $ = require('$');
    // 获取cookies
    //function GetCookie(sMainName, sSubName) {
    //    var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
    //    return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    //}
    //
    //


    function getCookie(c_name)
    {
        if (document.cookie.length>0)
        {
            var c_start=document.cookie.indexOf(c_name + "=")
            if (c_start!=-1)
            {
                c_start=c_start + c_name.length+1
                var c_end=document.cookie.indexOf(";",c_start)
                if (c_end==-1) c_end=document.cookie.length
                return unescape(document.cookie.substring(c_start,c_end))
            }
        }
        return ""
    }






    $(function () {
        //判断当前用户cookie是否存在
        console.log()
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

    })
});
