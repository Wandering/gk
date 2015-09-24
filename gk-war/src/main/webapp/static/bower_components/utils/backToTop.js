define(function (require, exports, module) {
    var $ = require('$');
    // 返回顶部
    var $backToTopEle = $('<a href="javascript:void(0)" class="iconfont toTop" title="返回顶部" alt="返回顶部" style="display:none"><i class="icon-arrow-up" style="line-height: 38px;"></i></a>').appendTo($("body")).click(function () {
        $("html, body").animate({scrollTop: 0}, 120);
    });

    var $backToTopFun = function () {
        var st = $(document).scrollTop(), winh = $(window).height();
        (st > 0) ? $backToTopEle.show() : $backToTopEle.hide();
        /*IE6下的定位*/
        if (!window.XMLHttpRequest) {
            $backToTopEle.css("top", st + winh - 166);
        }
    };
    $(function () {
        $(window).on("scroll", $backToTopFun);
        $backToTopFun();
    });
});
