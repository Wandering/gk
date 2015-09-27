define(function (require) {
    var $ = require('$');
    require('header-user');
    $.ajax({
        url: '/appointment/getAppointment.do',
        dataType: 'json',
        type: 'get',
        data: {
            pageNo:1,
            pageSize:3,
            titleKey:89
        },
        success: function (res) {
            if (res.rtnCode == '0000000') {
                console.log(res);
            }
        }
    })
});
