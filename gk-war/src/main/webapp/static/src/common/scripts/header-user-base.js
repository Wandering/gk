/**
 * Created by pdeng on 15/9/26.
 */
define(function (require) {
    var $ = require('$');
    //获取用户信息
    $.ajax({
        url: '/info/getUserInfo.do',
        dataType: 'json',
        type: 'get',
        data: {},
        success: function (res) {
            if(res.rtnCode == '0000000'){
                var userData = res.bizData;
                console.log(userData.name);
                console.log(userData.icon);
            }
        }
    })
});