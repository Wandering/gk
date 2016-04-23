define(['commonjs'], function (util) {
    require('../css/user/user-account-info.css');
    $(function () {
        $('#banner-info').prepend(require('html!../user-banner.html'));

        var cookie = require('cookie');
        var icon = cookie.getCookieValue('icon');
        var imgIco = require('../img/icon_default.png');
        if(icon=='undefined'){
            icon = imgIco;
        }
        var userName = cookie.getCookieValue('userName');
        var vipStatus = cookie.getCookieValue('vipStatus');
        $('.user-avatar').attr('src',icon);
        $('.user-name').text(userName);
        if(vipStatus==0){
            $('#btn-vip,#user-type').show();
            $('#vip-box').hide();
        }else{
            $('#btn-vip,#user-type').hide();
            $('#vip-box').show();
        }

    });


});



