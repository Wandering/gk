define(function (require) {
    var $ = require('$');
    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    if (!GetCookie("gxuser") || GetCookie("gxuser") == '""') {
        console.log('没有登录');
        var defualtVideoHtml = ''
            + '<img src="http://cdn.gaokao360.net/static/global/before/images/defualt-video.jpg"/>'
            + '<p><a target="_blank" href="/login/login.jsp">登录</a>后,才可以正常播放</p>'
        $('#logoutStatus').show().html(defualtVideoHtml)
    }
});
