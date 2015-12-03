define(function (require) {
    var $ = require('$');


    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }

    $(function () {
        var isUser = GetCookie("gxuser");
        if (isUser) {
            $.getJSON(
                "/info/getUserAccount.do",
                function (res) {
                    //console.log(res.rtnCode)
                    if (res.rtnCode == '0000000') {
                        var userData = res.bizData;
                        var account = userData.account;
                        var name = userData.name;
                        var userImg = userData.icon;
                        var imgUrl = '';
                        if(!userImg){
                            imgUrl = 'http://cdn.gaokao360.net/static/global/common/images/icon_default.png';
                        }else{
                            imgUrl = userImg;
                        }
                        var username = '';
                        if(name){
                            username = name;
                        }else{
                            username = account;
                        }
                        var loginUserHtml = ''
                            +'<img src="'+ imgUrl +'" alt="avatar" class="user-avatar" id="user-avatar"/>'
                            +'<a href="javascript:" id="accountNum" class="username">'+ username +'</a>';
                        $('#loginUser').append(loginUserHtml);

                    }
                });
            $('#loginUser,#user-avatar').show();
            $('#log-reg').hide();

        } else {
            $('#loginUser,#user-avatar').hide();
            $('#log-reg').show();
             /*
             * @param 添加权限管理
             *        user、模块需要用户登陆才能访问
             * */
            var url = window.location.pathname;
            var path = url.split('/');
            if(path[1] == 'user'){
                window.location.href = '/login/login.jsp';
            }
        }
        $('#main-menu').on('mouseover', 'li.menu-item', function () {
            $(this).addClass('active');
        });
        $('#main-menu').on('mouseout', 'li.menu-item', function () {
            $(this).removeClass('active');
        });
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



    $(document).scroll(function () {
        if ($(this).scrollTop() > 70) {
            if (!$('.header').hasClass('fix')) {
                $('.header').addClass('fix');
            }
        } else {
            $('.header').removeClass('fix');
        }
    });
});
