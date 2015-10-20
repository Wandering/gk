define(function (require) {
    var $ = require('$');
    require('swiper-jquery');
    //幻灯片
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        loop: true,
        //speed:500,
        startSlide: 4,  //起始图片切换的索引位置
        autoplay: 5000, //设置自动切换时间，单位毫秒
        continuous: true
    });
    //banner
    $.ajax({
        url: '/banner/getBannerByType.do',
        type: 'get',
        dataType: 'json',
        data: {
            type: 1
        },
        success: function (res) {
            //console.log(res);
            if (res.rtnCode == '0000000') {
                var dataJson = res.bizData;
                $.each(dataJson, function (i, v) {
                    //console.log(v.imageUrl);
                    //$('.banner-toggle').attr('src',v);
                })
            }
        }
    });


});
