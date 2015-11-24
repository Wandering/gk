define(function (require) {
    var $ = require('$');
    $('#goTop').on('click','.gotop',function(){
        $("html, body").animate({scrollTop: 0}, 800);
    });

    var $backToTopFun = function () {
        var $backToTopEle = $('#goTop').find('.gotop');
        var st = $(document).scrollTop(),
            winh = $(window).height();
        (st > 0) ? $backToTopEle.slideDown("slow") : $backToTopEle.slideUp("slow");
        /*IE6下的定位*/
        if (!window.XMLHttpRequest) {
            $backToTopEle.css("top", st + winh - 166);
        }
    };
    $(function () {
        $(window).on("scroll", $backToTopFun);
        $backToTopFun();
    });
    var timer = null;
    var timer2 = null;
    $('#qr-code,#qr-code-container').on('mouseover',function(){
        clearInterval(timer);
        $('#qr-code-container').show();
        $('#mixpool-container').hide();
    });
    $('#qr-code,#qr-code-container').on('mouseout',function(){
        timer = setInterval(function(){
            $('#qr-code-container').hide();
        },1000);
    });
    $('#mixpool,#mixpool-container').on('mouseover',function(){
        clearInterval(timer2);
        $('#mixpool-container').show();
        $('#qr-code-container').hide();
    });
    $('#mixpool,#mixpool-container').on('mouseout',function(){
        timer2 = setInterval(function(){
            $('#mixpool-container').hide();
        },1000);
    });
    $('#goTopScroll').hover(function(){
              $(this).addClass('hover');
    },function(){
        $(this).removeClass('hover');
    });
});
