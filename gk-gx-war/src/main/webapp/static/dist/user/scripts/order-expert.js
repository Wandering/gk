define(function (require) {
    var $ = require('$');
    //require('laydate');
    require('swiper');


    $('.btn-submit').click(function () {
        //验证
        var s = $('.data-start').val();
        var e = $('.data-end').val();
        var start = Date.parse(new Date(s)) / 1000;
        var end = Date.parse(new Date(e)) / 1000;
        var title = $('.order-theme').val().trim();
        var content = $('#content').val();
        var name = $('.name').val();
        var mobile = $('.mobile').val();
        var qq = $('.qq').val();
        if (title.length > 50 ) {
            $('.error-tips').text('标题字数过长，请输入50字以内').fadeIn(1000).fadeOut(3000);
            return false;
        }
        if (content.length > 5000 ) {
            $('.error-tips').text('预约内容过长，5000字以内').fadeIn(1000).fadeOut(3000);
            return false;
        }
        if (content.length == 0 || title.length == 0 ||end.length == 0 || start.length == 0 || name.length == 0 || mobile.length == 0 || qq.length == 0) {
            $('.error-tips').text('为了保证内容完整性，该表单每项都必填。').fadeIn(1000).fadeOut(3000);
            return false;
        }


        if (name.length > 10 ) {
            $('.error-tips').text('姓名不能超过10个字').fadeIn(1000).fadeOut(3000);
            return false;
        }
        var qq_reg = /^\s*[.0-9]{5,11}\s*$/;
        if (!qq_reg.test(qq)) {
            $('.error-tips').text('QQ号码输入有误').fadeIn(1000).fadeOut(3000);
            return false;
        }

        var reg = /^0?1[3|4|5|6|7|8][0-9]\d{8}$/;
        if (!reg.test(mobile)) {
            $('.error-tips').text('预约电话号码输入有误').fadeIn(1000).fadeOut(3000);
            return false;
        }


        $.ajax({
            url: '/appointment/addAppointment.do',
            dataType: 'json',
            type: 'post',
            data: {
                "title": title,
                "content": content,
                "startDate": start,
                "endDate": end,
                "name": name,
                "mobile": mobile,
                "qq": qq
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    $('.error-tips').text('恭喜你，预约成功').fadeIn(1000).fadeOut(3000);
                    window.location.href = 'http://' +window.location.host+'/user/expert-service.jsp'
                } else {
                    $('.error-tips').text('对不起，预约失败').fadeIn(1000).fadeOut(3000);
                }
            }
        })
    });
});
