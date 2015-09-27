define(function (require) {
    var $ = require('$');
    require('header-user');
    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }
    //拉取数据
    var id = getUrLinKey('id');
    $(function () {
        $.ajax({
            url: '',
            dataType: 'json',
            type: 'get',
            data: {
                id:id
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    console.log(res);
                }
            }
        })
    })

});
