<%@ page language="java" pageEncoding="UTF-8" %>
<div class="header">
  <div class="w1000">
    <a href="/index.jsp">
      <img src="/static/dist/common/images/logo-min.png" alt="高考360" class="logo fl"/>
    </a>
    <ul class="main-menu fl" id="main-menu">
      <li class="menu-item">
        <a href="/index.jsp">首页</a>
        <ul class="submenu">
          <li><a href="/guide/guide.jsp">高考政策</a></li>
          <li><a href="/consult/gk_hot.jsp">高考资讯</a></li>
          <li><a href="/before/before.jsp">考前备考</a></li>
          <li><a href="/after/after.jsp">考后报考</a></li>
        </ul>
      </li>
      <li class="menu-item"><a href="/before/teacher-lecture.jsp?classifyType=1&searchV=" beforeMenuType="1">名师讲堂</a></li>
      <li class="menu-item"><a href="/before/exam.jsp?classifyType=3&searchV=" beforeMenuType="3">真题密卷</a></li>
      <li class="menu-item"><a href="/before/mentality.jsp?classifyType=2&searchV=" beforeMenuType="2">高考心理</a></li>
      <li class="menu-item"><a href="/before/before.jsp?classifyType=4" id="volunteer-flow">院校推荐</a></li>
    </ul>
    <div class="log-reg hide">
      <a href="/login/login.jsp">登录/注册</a>
    </div>
    <div class="user-info-list fr hide">
      <div class="user">
        <img src="" alt="avatar" class="user-avatar hide"/>
        <a href="javascript:" id="accountNum" class="username"></a>
      </div>
      <ul class="menu hide">
        <li><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/login/logout.do">退出</a></li>
      </ul>
    </div>
  </div>
</div>
<script>
  seajs.use("/static/src/before/scripts/header-before");
</script>
