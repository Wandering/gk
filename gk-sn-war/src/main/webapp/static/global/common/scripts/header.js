define(function (require) {
    var $ = require('$');
    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    var isUser = GetCookie("snuser");
    $.getJSON(
        "/info/getUserAccount.do",
        function (res) {
            console.log(res);
            if (res.rtnCode == '0000000') {
                var userData = res.bizData;
                var account = userData.account;
                var name = userData.name;
                $('#accountNum').attr('accountNum', account);
                if (name == null || name == '') {
                    name == account;
                }
                $('.username').text(name);
                var userImg = userData.icon;
                if (userImg) {
                    $('#user-avatar').attr('src', userImg);
                }

            }
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

    $(function () {
        if (isUser) {
            $('#loginUser,#user-avatar').show();
            $('#log-reg').hide();
        } else {
            $('#loginUser,#user-avatar').hide();
            $('#log-reg').show();
        }
        addMenuActive();
        $('#main-menu').on('mouseover', 'li.menu-item', function () {
            $(this).addClass('active');
        });
        $('#main-menu').on('mouseout', 'li.menu-item', function () {
            $(this).removeClass('active');
        });
    });

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
