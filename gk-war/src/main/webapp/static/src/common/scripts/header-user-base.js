/**
 * Created by pdeng on 15/9/26.
 */
define(function (require) {
    var $ = require('$');

    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    //判断当前用户cookie是否存在
    if (!GetCookie("gkuser",'')) {
        console.log('用户已经退出了');
        window.location.href = 'http://www.baidu.com';
    }else{
        console.log('登录状态');
        console.log(GetCookie("gkuser",''));
    }

    //var c_start = document.cookie.indexOf("gkuser");
    //if (c_start == -1) {
    //    console.log(a);
    //    console.log('已登录');
    //} else {
    //    console.log('请重新登录')
    //}

    //登出
    $('.menu li:last-child').click(function (event) {
        event.stopPropagation();
        event.preventDefault();
        $.ajax({
            type: 'get',
            url: '/login/logout.do',
            success: function (res) {
                console.log(res);
                //if(res.rtnCode =='0000000'){
                //    window.location.href = '/index.jsp';
                //}else{
                //    //alert(res.msg);
                //}
            }
        });
    });

    $(function () {
        //判断用户是否登陆
        //if (cookie()) {
        //    console.log('已经登录了');
        //    $('.log-reg').removeClass('hide');
        //    //获取用户信息
        //    $.ajax({
        //        url: '/info/getUserAccount.do',
        //        dataType: 'json',
        //        type: 'post',
        //        data: {},
        //        success: function (res) {
        //            console.log(res);
        //            if (res.rtnCode == '0000000') {
        //                var userData = res.bizData;
        //                var name = userData.name;
        //                if (name == null || name == '') {
        //                    name == userData.account;
        //                }
        //                $('.username').text(name);
        //                if (userData.icon == null || userData.icon == '') {
        //                    console.log(userData.icon);
        //                    var userImg = '/static/dist/common/images/icon_default.png';
        //                }
        //                $('.user-avatar').attr('src', userImg);
        //                $('.menu').fadeIn();
        //            }
        //        }
        //    });
        //} else {
        //    console.log('没有登录');
        //    //$('.user-info-list').addClass('hide');
        //    //$('.log-reg').addClass('hide');
        //
        //}
    });


});