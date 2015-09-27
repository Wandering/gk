<%--
  Created by IntelliJ IDEA.
  User: kepeng
  Date: 15/9/24
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<div class="header">
  <div class="w1000">
    <a href="">
      <img src="/static/dist/common/images/logo-min.png" alt="高考360" class="logo fl"/>
    </a>
    <ul class="main-menu fl" id="before-menu">
      <li><a href="/index.jsp">首页</a></li>
      <li><a href="/before/teacher-lecture.jsp?classifyType=1" beforeMenuType="1">名师讲堂</a></li>
      <li><a href="/before/exam.jsp?classifyType=3" beforeMenuType="3">真题密卷</a></li>
      <li><a href="/before/mentality.jsp?classifyType=2" beforeMenuType="2">高考心理</a></li>
      <li><a href="/before/before.jsp?classifyType=4" id="volunteer-flow">院校推荐</a></li>
    </ul>
    <div class="user-info-list fr">
      <div class="user">
        <img src="/static/dist/common/images/avatar.png" alt="avatar" class="user-avatar"/>
        <a href="">韩小寒</a>
      </div>
      <ul class="menu hide">
        <li><a href="">个人信息</a></li>
        <li><a href="">修改密码</a></li>
        <li><a href="">退出</a></li>
      </ul>
    </div>
  </div>
</div>
<script>
  seajs.use("/static/src/before/scripts/header-before");
</script>
