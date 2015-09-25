define(function (require) {
    var $ = require('$');
    require('swiper');
    //幻灯片
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        loop: true
    });
    $(function () {
        $('.ask-question').click(function () {
            window.location.assign('http://' + window.location.host + '/question/ask.jsp');
        });
        $('.go-search').click(function () {
            var searchText = $('.search-val').val();
            window.location.assign('http://' + window.location.host + '/question/question_search_result.jsp?val='+searchText);
        });
        $('.tabs-list').find('li').click(function(){
            var n = $(this).index();
            $(this).addClass('active').siblings().removeClass('active');
            $('.tab').hide();
            $('.tab').eq(n).show();
        });

    });

});
