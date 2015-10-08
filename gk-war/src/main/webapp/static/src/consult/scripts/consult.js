/**
 * Created by kepeng on 15/9/26.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    //if (!GetCookie("gkuser") || GetCookie("gkuser") == '""') {
    //    window.location.href = '/login/login.jsp';
    //}

    var api = flowplayer("player", "/static/src/guide/scripts/flowplayer-3.2.18.swf");

    $(document).ready(function() {
        $('#search').on('click', function(e) {
            var val = $('#keywords').val();
            window.location.href = '/consult/profession_info.jsp?val=' + val;
        });

        $('.into-evalution a').on('click',function(e) {
            var flag = false;
            if (!flag) {
                flag = true;
                $.get('/appraisal/lstest.do', function(data) {
                    console.log(data.rtnCode)
                    flag = true;
                    if ('0000000' === data.rtnCode && data.bizData) {
                        window.location.href = data.bizData;
                    }
                    if ('0100006' === data.rtnCode) {
                        $('#error_tip').html('您好，您不是VIP用户，请进入<a href="/user/vip-service.jsp">个人中心</a>进行VIP升级。');
                        $('#error_tip').show();
                    } if ('1000004' === data.rtnCode) {
                        $('#error_tip').html(data.msg + ",请<a target='_blank' href='/login/login.jsp'>登录</a>");
                        $('#error_tip').show();
                    }else {
                        $('#error_tip').html(data.msg);
                        $('#error_tip').show();
                    }
                });
            }
        });
    });
});
