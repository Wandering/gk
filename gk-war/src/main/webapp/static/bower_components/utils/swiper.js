define(function (require) {

    var $ = require('$');
    require('swiper-jquery');
    //幻灯片
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        loop: true
    });

    //$.ajax({
    //    url: '/banner/getUrlByType.do',
    //    type: 'get',
    //    dataType: 'json',
    //    data: {
    //        type:1
    //    },
    //    success: function (res) {
    //        console.log(res);
    //        if (res.rtnCode == '0000000') {
    //            //window.location.href
    //        }
    //    }
    //});

    $.getJSON('/banner/getUrlByType.do',{
        type:1
    },function(res){
        console.log(res);
    })




});
