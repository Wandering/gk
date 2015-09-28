define(function (require) {
    var $ = require('$');
    require('header-user');

    $(document).scroll(function() {
        if ($(this).scrollTop() > $(window).height()) {
            if (!$('.header').hasClass('fix')) {
                $('.header').addClass('fix');
            }
        } else {
            $('.header').removeClass('fix');
        }
    });

});
