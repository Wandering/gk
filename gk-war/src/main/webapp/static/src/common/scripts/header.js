define(function (require) {
    var $ = require('$');
    require('header-user');

    $(document).scroll(function() {
        if ($(this).scrollTop() > 70) {
            if (!$('.header').hasClass('fix')) {
                $('.header').addClass('fix');
            }
        } else {
            $('.header').removeClass('fix');
        }
    });



    function addMenuActive() {
        var pathName = window.location.pathname.split('/');
        var pageName = pathName[pathName.length - 1];
        console.log(pageName)
        if (!pageName) {
            $('.index').addClass('active');
            return;
        }
        switch (pageName) {
            case 'index.jsp':
                $('.index').addClass('active');
                break;
            case 'guide.jsp':
            case 'volunteer_forum.jsp':
                $('.guide').addClass('active');
                break;
            case 'gk_hot.jsp':
            case 'consult.jsp':
            case 'school_info.jsp':
            case 'profession_info.jsp':
                $('.gk_hot').addClass('active');
                break;
            case 'before.jsp':
                $('.before').addClass('active');
                break;
            case 'after.jsp':
            case 'forward.do':
                $('.after').addClass('active');
                break;
            default:
                break;
        }
    }

    addMenuActive();

});
