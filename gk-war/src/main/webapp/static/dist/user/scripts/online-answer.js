define(function (require) {
    var $ = require('$');
    require('header-user');



    $.ajax({
        url:'/product/findProduct.do',
        type: 'GET',
        dataType: 'JSON',
        data: {
            code: 10000001
        },
        success: function (result) {
            if (result.rtnCode == '0000000') {
                console.log(result)
            }
        }
    });



});
