<%@ page language="java" pageEncoding="UTF-8" %>
<div class="header">
  <div class="w1000">
    <a href="/index.jsp">
      <img src="http://cdn.gaokao360.net/static/gx/common/images/gx-logo.png" alt="高考360" class="logo fl"/>
    </a>
    <ul class="main-menu fl" id="main-menu">
      <li class="menu-item"><a class="index" href="/index.jsp">首页</a></li>
      <li class="menu-item"><a class="guide" href="javascript:;">高考政策</a>
        <ul class="submenu">
          <li><a href="/guide/guide.jsp">政策解读</a></li>
          <li><a href="/guide/volunteer_forum.jsp">志愿讲堂</a></li>
        </ul>
      </li>
      <li class="menu-item"><a class="gk_hot" href="javascript:;">高考资讯</a>
        <ul class="submenu">
          <li><a href="/consult/gk_hot.jsp">高考热点</a></li>
          <li><a href="/consult/consult.jsp">专业测评</a></li>
          <li><a href="/consult/school_info.jsp">院校信息</a></li>
          <li><a href="/consult/profession_info.jsp">专业信息</a></li>
          <li><a href="/consult/area-scores.jsp">地区批次线</a></li>
        </ul>
      </li>
      <li class="menu-item"><a class="before" href="/before/before.jsp">考前备考</a>
        <ul class="submenu">
          <li><a href="/before/teacher-lecture.jsp?classifyType=1&searchV=">名师讲堂</a></li>
          <li><a href="/before/exam.jsp?classifyType=3&searchV=">真题密卷</a></li>
          <li><a href="/before/mentality.jsp?classifyType=2&searchV=">高考心理</a></li>
          <li><a href="/before/before.jsp?classifyType=4">院校推荐</a></li>
        </ul>
      </li>
      <li class="menu-item"><a class="after" href="/after/after.jsp">考后报考</a></li>
    </ul>
    <div class="log-reg hide" id="log-reg">
      <a href="/login/login.jsp">登录/注册</a>
    </div>
    <div class="user-info-list fr" id="login-user-info">
      <div class="user hide" id="loginUser"></div>
      <ul class="menu hide">
        <li><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
        <li><a href="/user/my-collect.jsp">我的收藏</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/login/logout.do">退出</a></li>
      </ul>
    </div>
  </div>
</div>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script>
//  seajs.use("/static/src/common/scripts/header");
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
</script>


