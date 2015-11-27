<%@ page language="java" pageEncoding="UTF-8" %>
<div class="header">
  <div class="w1000">
    <a href="/index.jsp">
      <img src="http://cdn.gaokao360.net/static/global/common/hn/images/logo-min.png" alt="高考360" class="logo fl"/>
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
        <ul class="submenu" style="z-index: 100">
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
      <ul class="menu" id="userMenu">
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
<script>
  seajs.use("/static/src/common/scripts/header");
</script>


