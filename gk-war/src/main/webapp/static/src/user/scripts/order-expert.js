define(function (require) {
    var $ = require('$');
    require('header-user');
    require('laydate');
    $.ajax({
        url: 'appointment/addAppointment.do',
        dataType: 'json',
        type: 'post',
        data: {
            "title": "预约一个数学专家",
            "content": "我想预约一个专家",
            "startDate": 1442996204696,
            "endDate": 1442996204696,
            "name": "樊旭光",
            "mobile": 1378909290,
            "qq": 41138890
        },
        success: function (res) {
            if (res.rtnCode == '0000000') {
                console.log(res);
            }
        }
    })
});
