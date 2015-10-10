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
    //时间戳转化
    Date.prototype.Format = function(fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
    function getTime(timestamp) {
        var newDate = new Date();
        newDate.setTime(timestamp);
        return newDate.Format("yyyy年MM月dd日");
    }
    $(function () {
        $.ajax({
            url: '/appointment/getAppointmentDetail.do',
            dataType: 'json',
            type: 'get',
            data: {
                id:id
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    var dataJson = res.bizData;
                    var start = getTime(dataJson.startDate * 1000);
                    var end = getTime(dataJson.createDate);
                    $('#appointment-title').text(dataJson.title);
                    $('#appointment-start').text(start);
                    $('#appointment-end').text(end);
                    $('#appointment-require').text(dataJson.content);
                    $('#appointment-name').text(dataJson.name);
                    $('#appointment-tel').text(dataJson.mobile);
                    $('#appointment-qq').text(dataJson.qq);
                }else{
                    $('.content-list').html(res.msg);
                }
            }
        })
    })

});
