<%@ page language="java" pageEncoding="UTF-8" %>
<div class="header">
  <div class="w1000">
    <a href="">
      <img src="/static/dist/common/images/logo-min.png" alt="高考360" class="logo fl"/>
    </a>
    <ul class="main-menu fl">
      <li><a class="index" href="/index.jsp">首页</a></li>
      <li><a class="guide" href="/guide/guide.jsp">高考政策</a>
        <ul class="submenu">
          <li><a href="/guide/guide.jsp">政策解读</a></li>
          <li><a href="/guide/volunteer_forum.jsp">志愿讲堂</a></li>
        </ul>
      </li>
      <li><a class="gk_hot" href="/consult/gk_hot.jsp">高考资讯</a>
        <ul class="submenu">
          <li><a href="/consult/consult.jsp">专业测评</a></li>
          <li><a href="/consult/school_info.jsp">院校信息</a></li>
          <li><a href="/consult/profession_info.jsp">专业信息</a></li>
        </ul>
      </li>
      <li><a class="before" href="/before/before.jsp">考前备考</a></li>
      <li><a class="after" href="/after/after.jsp">考后报考</a></li>
    </ul>
    <div class="log-reg hide">
      <a href="/login/login.jsp">登陆/注册</a>
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
        <li><a href="javascript:;">退出</a></li>
      </ul>
    </div>
  </div>
</div>

<script>
  seajs.use("/static/src/common/scripts/header");
</script>


