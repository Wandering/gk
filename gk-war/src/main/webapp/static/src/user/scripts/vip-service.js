define(function (require) {
    var $ = require('$');



    // 获取商品信息
    $.ajax({
        url:'/product/findProduct.do',
        type: 'GET',
        dataType: 'JSON',
        data: {
            code: 10000001
        },
        success: function (result) {
            console.log(result)
            if (result.rtnCode == '0000000') {
                var price = result.bizData.price,
                    userId = result.bizData.id,
                    validValue = result.bizData.validValue;

                //创建订单
                $('#createOrderBtn').on('click', function () {
                    var products = [];
                    var product = {};
                    product["productCode"] = vipCode;
                    product["productNum"] = 1;
                    product["unitPrice"] = price;
                    product["validValue"] = validValue;
                    products[0] = product;
                    var pay = {
                        "userId": userId
                    };
                    pay["products"] = JSON.stringify(products);

                    var extra = {};
                    if (index === 0) {
                        extra["success_url"] = window.location.hostname + "/success.jsp";
                        extra["cancel_url"] = window.location.hostname + "/fail.jsp";
                        pay["channel"] = 'alipay_wap';
                    } else {
                        extra["result_url"] = "http://ep.zhiless.com/";
                        pay["channel"] = 'upacp_wap';
                    }
                    pay["remoteIp"] = '127.0.0.1';
                    pay["extra"] = JSON.stringify(extra);
                    $.ajax({
                        url: '/orders/createOrders.do',
                        type: 'POST',
                        dataType: 'JSON',
                        data: pay,
                        success: function (res) {
                            console.log(res);
                        }
                    });
                })


            }
        }
    });



});
