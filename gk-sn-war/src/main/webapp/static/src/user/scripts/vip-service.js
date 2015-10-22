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
        if($('#pay-card').val().trim()==""){
            $('.error-tips').text("卡号不能为空").fadeIn(1000).fadeOut(1000);
            return;
        }
        if($('#pay-card').val().trim().length != 10){
            $('.error-tips').text("请输入正确的卡号").fadeIn(1000).fadeOut(1000);
            return;
        }
        if($('#pay-password').val().trim()==""){
            $('.error-tips').text("卡密码不能为空").fadeIn(1000).fadeOut(1000);
            return;
        }
        if($('#pay-password').val().trim().length!=9){
            $('.error-tips').text("请输入正确的卡密码").fadeIn(1000).fadeOut(1000);
            return;
        }

        $.ajax({
            url:'/vip/upgradeVipByCard.do',
            type: 'POST',
            dataType: 'JSON',
            data:{
                "cardNumber": $('#pay-card').val(),
                "password": $('#pay-password').val()
            },
            success: function (res) {

                if(res.rtnCode=='0000000'){
                    $('.error-tips').text("申请成功").fadeIn(1000).fadeOut(1000);
                }


                if(res.rtnCode=='0900002' || res.rtnCode=='0900001'){
                    $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);

                }
            }
        });
    });


    //各地招办联系方式
    $.get('/agent/getAgent.do', function (res) {
        if (res.rtnCode == '0000000') {
            var dataJson = res.bizData;
            var addressHtml = ''
            $.each(dataJson, function (i, v) {
                var address = v.address;
                var name = v.name;
                var telphone = v.telphone;
                addressHtml+= '<div class="col-3">'
                + '<p class="area-name">' + address + '</p>'
                + '<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">' + telphone + '</span>'+ name + '</p>'
                + '</div>';
                $('#address-box').html(addressHtml);
            });
        } else {
            alert(res.msg);
        }
    });

});
