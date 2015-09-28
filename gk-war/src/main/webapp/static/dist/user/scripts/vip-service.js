define(function (require) {
    var $ = require('$');
    require('header-user');


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
                    validValue = result.bizData.validValue,
                    vipCode = result.bizData.code;


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
                    extra["result_url"] = "http://ep.zhiless.com/";
                    pay["channel"] = 'alipay_wap';
                    pay["extra"] = JSON.stringify(extra);
                    console.log(pay)
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
    //高考志愿卡升级
    $.ajax({
        url:'/vip/getAccount.do',
        type: 'GET',
        dataType: 'JSON',
        success: function (result) {
            if(result.rtnCode=="0000000"){
                var accountNum = result.bizData.account;
                $('.accountNum').html(accountNum);
            }
        }
    });

    $('#accountBtn').on('click',function(){

        $.ajax({
            url:'/vip/upgradeVipByCard.do',
            type: 'POST',
            dataType: 'JSON',
            data:{
                "cardNumber": 777777,
                "password": 888888
            },
            success: function (res) {
                console.log(res)
            }
        });
    });

});
