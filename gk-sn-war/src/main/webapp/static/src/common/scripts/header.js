define(function (require) {
    var $ = require('$');

    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }

    var  isUser = GetCookie("snuser");

    //判断当前用户cookie是否存在
    //if (!GetCookie("snuser") || GetCookie("snuser") == '""') {
    //    console.log('没有登录');
    //    $('.menu').hide();
    //    $('.log-reg').show();
    //    return false;
    //}


    //$.ajax({
    //    url: '/info/getUserAccount.do',
    //    dataType: 'json',
    //    type: 'get',
    //    data: {},
    //    success: function (res) {
    //        if (res.rtnCode == '0000000') {
    //            var userData = res.bizData;
    //            $('#accountNum').attr('accountNum',userData.account);
    //            var name = userData.name;
    //            if (name == null || name == '') {
    //                name == userData.account;
    //            }
    //            $('.username').text(name);
    //            var userImg;
    //            if (userData.icon == null || userData.icon == '') {
    //                userImg = 'http://cdn.gaokao360.net/static/global/common/images/icon_default.png';
    //            } else {
    //                userImg = userData.icon
    //            }
    //            $('.user-avatar').attr('src', userImg).fadeIn();
    //        }
    //    }
    //});









    $(function(){
        if(isUser){
            $('.user-info-list').show();

        }

        getUser();

        function getUser(){

            console.log(22)
            $.getJSON(
                "/info/getUserAccount.do",
                function (result) {
                    console.log(result)
                });


            //$.ajax({
            //    url: '/info/getUserAccount.do',
            //    dataType: 'json',
            //    type: 'get',
            //    data: {},
            //    success: function (res) {
            //        if (res.rtnCode == '0000000') {
            //            var userData = res.bizData;
            //            $('#accountNum').attr('accountNum',userData.account);
            //            var name = userData.name;
            //            if (name == null || name == '') {
            //                name == userData.account;
            //            }
            //            $('.username').text(name);
            //            var userImg;
            //            if (userData.icon == null || userData.icon == '') {
            //                userImg = 'http://cdn.gaokao360.net/static/global/common/images/icon_default.png';
            //            } else {
            //                userImg = userData.icon
            //            }
            //            $('.user-avatar').attr('src', userImg).fadeIn();
            //        }
            //    }
            //});
        }







        $(document).scroll(function() {
            if ($(this).scrollTop() > 70) {
                if (!$('.header').hasClass('fix')) {
                    $('.header').addClass('fix');
                }
            } else {
                $('.header').removeClass('fix');
            }

        });


        $('#main-menu').on('mouseover','li.menu-item',function(){
            $(this).addClass('active');
        });
        $('#main-menu').on('mouseout','li.menu-item',function(){
            $(this).removeClass('active');
        });



        function addMenuActive() {
            var pathName = window.location.pathname.split('/');
            var pageName = pathName[pathName.length - 1];
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
                case 'school_detail.jsp':
                case 'area-scores.jsp':
                case 'profession_detail.jsp':
                case 'gk_hot_detail.jsp':
                    $('.gk_hot').addClass('active');
                    break;
                case 'before.jsp':
                case 'teacher-lecture.jsp':
                case 'exam.jsp':
                case 'mentality.jsp':
                case 'teacher-lecture-play.jsp':
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





    //$('#main-menu').on('mouseover','li.menu-item',function(){
    //    $(this).addClass('active');
    //});
    //$('#main-menu').on('mouseout','li.menu-item',function(){
    //    $(this).removeClass('active');
    //});
    //
    //
    //$(document).scroll(function() {
    //    if ($(this).scrollTop() > 70) {
    //        if (!$('.header').hasClass('fix')) {
    //            $('.header').addClass('fix');
    //        }
    //    } else {
    //        $('.header').removeClass('fix');
    //    }
    //
    //});
    //function addMenuActive() {
    //    var pathName = window.location.pathname.split('/');
    //    var pageName = pathName[pathName.length - 1];
    //    if (!pageName) {
    //        $('.index').addClass('active');
    //        return;
    //    }
    //    switch (pageName) {
    //        case 'index.jsp':
    //            $('.index').addClass('active');
    //            break;
    //        case 'guide.jsp':
    //        case 'volunteer_forum.jsp':
    //            $('.guide').addClass('active');
    //            break;
    //        case 'gk_hot.jsp':
    //        case 'consult.jsp':
    //        case 'school_info.jsp':
    //        case 'profession_info.jsp':
    //        case 'school_detail.jsp':
    //        case 'area-scores.jsp':
    //        case 'profession_detail.jsp':
    //        case 'gk_hot_detail.jsp':
    //            $('.gk_hot').addClass('active');
    //            break;
    //        case 'before.jsp':
    //        case 'teacher-lecture.jsp':
    //        case 'exam.jsp':
    //        case 'mentality.jsp':
    //        case 'teacher-lecture-play.jsp':
    //            $('.before').addClass('active');
    //            break;
    //        case 'after.jsp':
    //        case 'forward.do':
    //            $('.after').addClass('active');
    //            break;
    //        default:
    //            break;
    //    }
    //}
    //
    //addMenuActive();

});
