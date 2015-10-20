define(function (require) {
    var $ = require('$');
    require('header-user');

    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
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

    $('#main-menu').on('mouseover','li.menu-item',function(){
        $(this).addClass('active');
    });
    $('#main-menu').on('mouseout','li.menu-item',function(){
        $(this).removeClass('active');
    });

    $(document).scroll(function() {
        if ($(this).scrollTop() > 70) {
            if (!$('.header').hasClass('fix')) {
                $('.header').addClass('fix');
            }
        } else {
            $('.header').removeClass('fix');
        }
    });

});
