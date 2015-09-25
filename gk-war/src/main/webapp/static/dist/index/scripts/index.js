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
    var url = 'http://' + window.location.host;
    $(function () {
        //在线互动
        var more = $('.more');
        $('.ask-question').click(function () {
            window.location.assign(url + '/question/ask.jsp');
        });
        $('.go-search').click(function () {
            var searchText = $('.search-val').val();
            window.location.assign(url + '/question/question_search_result.jsp?val=' + searchText);
        });
        $('#tabs-online').find('li').click(function () {
            var n = $(this).index();
            $(this).addClass('active').siblings().removeClass('active');
            $('.tab').fadeOut();
            $('.tab').eq(n).fadeIn();
        });
        more.click(function () {
            window.location.assign(url + '/question/question.jsp')
        });
        //热门资讯
        $('#tabs-hosts').find('li').click(function () {
            var n = $(this).index();
            var m = $('.more');
            $(this).addClass('active').siblings().removeClass('active');
            $('.tab-info').fadeOut();
            $('.tab-info').eq(n).fadeIn();
            (n == 1) ? (m.fadeOut()) : (m.fadeIn());
        });
        $.get('/agent/getAgent.do', function (res) {
            if (res.rtnCode == '0000000') {
                var dataJson = res.bizData;
                $.each(dataJson, function (i, v) {
                    //console.log(v.address)
                    //console.log(v.name)
                    //console.log(v.telphone)
                });
            } else {
                alert(res.msg);
            }
        });
        $.ajax({
            url:' /gkinformation/getAllInformation.do',
            dataType:'json',
            type:'get',
            data:{},
            success:function(res){
                console.log(res);
            }
        })


    });

});
