define(['commonjs'], function (util) {
    require('../css/volunteer/volunteer-prediction.css');
    $(function () {

        function getUrLinKey(name) {
            var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
            if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
            return "";
        }

        var acId = getUrLinKey('Type')

        util.ajaxFun(util.INTERFACE_URL.postQueryApeskUrl, 'POST', {
            acId: acId//测试类型
        }, function (res) {
            if (res.rtnCode == '0000000') {
                console.log(res);
                if (res.rtnCode == "0000000") {
                    $("#apeskIframe").attr("src", res.bizData.data);
                } else {
                    alert(res.msg);

                }
            }
        });


    });
});