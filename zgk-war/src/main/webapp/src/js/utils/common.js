define(['commonCss', 'jquery'], function () {

    //浏览器判断

    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 0;
    var vesion = parseInt(Sys.ie);
    if (Sys.ie && vesion <= 8) {
        window.location.href = '/static/terrible-broswer.html';
    }
    $('head').prepend(require('html!../../meta.html'));
    var noHeaderFooterUrl = window.location.pathname;
    if (noHeaderFooterUrl != '/static/login.html' && noHeaderFooterUrl != '/static/class-college-detail.html') {
        $('body')
            .prepend(require('html!../../header.html'))
            .append(require('html!../../footer.html'));
    }


    var cookie = require('cookie');
    if (cookie.getCookieValue('isLogin') == 'true') {
        //alert(cookie.getCookieValue('isLogin'))
        $('#login-front').hide();
        $('#login-end').show();
    } else {
        $('#login-front').show();
        $('#login-end').hide();
        filterUrl();
    }

    var icon = cookie.getCookieValue('icon');
    var userName = cookie.getCookieValue('userName');
    var imgIco = require('../../img/icon_default.png');
    if(icon=='undefined'){
        icon = imgIco;
    }
    $('#header-user-avatar,.user-avatar').attr('src', icon);
    $('#header-user-name,.user-name').text(userName);
    $('body').on('click', '#logout-btn',function () {
        cookie.deleteCookie('isLogin','');
        cookie.deleteCookie('token','');
        window.location.assign('http://' + window.location.host + '/static/index.html')
    });


    var paths = window.location.pathname.split('/');
    var pagePath = paths[paths.length - 1];
    switch (pagePath) {
        case 'index.html':
            $('#nav-index').addClass('active');
            break;
        case 'news-hot.html':
        case 'news-detail.html':
        case 'news-policy.html':
        case 'news-schedule.html':
        case 'news-online-interactive.html':
        case 'data-gk-word.html':
            $('#nav-news-hot').addClass('active');
            break;
        case 'data-school-info.html':
        case 'data-professional-info.html':
        case 'data-school-enrollment.html':
        case 'data-occupational-info.html':
        case 'data-occupational-detail.html':
        case 'data-area-scores.html':
            $('#nav-data-school-info').addClass('active');
            break;
        case 'class-college.html':
        case 'class-college-detail.html':
            $('#nav-class-college').addClass('active');
            break;
        case 'predict-degree.html':
        case 'predict-school.html':
        case 'predict-professional.html':
        case 'volunteer.html':
            $('#nav-predict-degree').addClass('active');
            break;
        default:
            break;
    }

    function filterUrl() {
        var pathName = window.location.pathname.split('/');
        var pageName = pathName[pathName.length - 1];
        switch (pageName) {
            case 'index.html':
                $('#nav-index').addClass('active');
                break;
            case 'user-vip.html':
            case 'user-collection.html':
            case 'user-order.html':
            case 'user-target.html':
            case 'user-answer':
            case 'user-service.html':
            case 'user-report.html':
            case 'class-college-detail.html':
                window.location.assign('http://' + window.location.host + '/static/login.html')
                break;
            default:
                break;
        }
    }


    var isLogin = function () {
        return cookie.getCookieValue('isLogin')
    };

    var INTERFACE_URL = require('urlConfig');
    //ajax拉取数据 IE8 跨域
    function ajaxFun(url, method, data, callback, callbackError) {
        if (cookie.getCookieValue('token')) {
            data.token = cookie.getCookieValue('token');
        }
        data.userKey = 'zj';
        var strParameter = '';
        for (var i in data) {
            strParameter += "&" + i + "=" + data[i];
        }
        $.ajax({
            url: url,
            type: method,
            data: data || {},
            dataType: 'json',
            success: callback,
            error:callback
        });
    };


    var getLinkey = function getLinkey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    };


    return {
        isLogin: isLogin, //判断是否登录成功
        ajaxFun: ajaxFun,//数据拉取
        getLinkey: getLinkey,//url获取参数
        INTERFACE_URL: INTERFACE_URL,
        cookie: cookie,
    };


});




