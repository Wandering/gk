define(function (require) {
    var $ = require('$');
    //require('header-user');

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




    $('#logout-btn').click(function (event) {
        event.stopPropagation();
        event.preventDefault();
        $.ajax({
            type: 'get',
            url: '/login/logout.do',
            success: function (res) {
                console.log(res);
                if (res.rtnCode == '0000000') {
                    window.location.href = '/index.jsp';
                    delCookie('gkuser');
                } else {
                    //window.location.href = '/login/login.jsp';
                    console.log(res.msg);
                }
            }
        });
    });
    //删除cookies
    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }




    //登录
    function getCookie(c_name)
    {
        if (document.cookie.length>0)
        {
            var c_start=document.cookie.indexOf(c_name + "=")
            if (c_start!=-1)
            {
                c_start=c_start + c_name.length+1
                var c_end=document.cookie.indexOf(";",c_start)
                if (c_end==-1) c_end=document.cookie.length
                return unescape(document.cookie.substring(c_start,c_end))
            }
        }
        return ""
    }
    console.log(getCookie('gkuser'))

    var cookiesV = getCookie('gkuser');
    if(cookiesV==""){
        $('#login-user-info').hide();
        $('#log-reg').show();
    }else{
        $('#login-user-info').show();
        $('#log-reg').hide();
        $.ajax({
            url: '/info/getUserAccount.do',
            dataType: 'json',
            type: 'get',
            data: {},
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    var userData = res.bizData;
                    $('#accountNum').attr('accountNum',userData.account);
                    var name = userData.name;
                    if (name == null || name == '') {
                        name == userData.account;
                    }
                    $('.username').text(name);
                    var userImg;
                    if (userData.icon == null || userData.icon == '') {
                        userImg = '/static/dist/common/images/icon_default.png';
                    } else {
                        userImg = userData.icon
                    }
                    $('.user-avatar').attr('src', userImg).fadeIn();
                }
            }
        });
    }





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
