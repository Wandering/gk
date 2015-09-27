define(function (require) {
    var $ = require('$');
    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);

    console.log(classifyType)
    if(classifyType==1){
         $('a[beforeMenuType="1"]').addClass('active');
    }
    if(classifyType==2){
        $('a[beforeMenuType="2"]').addClass('active');
    }
    if(classifyType==3){
        $('a[beforeMenuType="3"]').addClass('active');
    }

    $('#volunteer-flow').on('click', function () {
        $('#main-volunteer-tabs li').eq(1).click();
        $('html,body').animate({scrollTop: ($('#main-volunteer-box').offset().top)}, 800);
    });

});