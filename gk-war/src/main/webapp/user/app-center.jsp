<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>应用中心</title>
    <link rel="stylesheet" href="/static/dist/user/styles/app-center.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>

<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li><a href="modify-psd.jsp">修改密码</a></li>
        <li class="active"><a href="app-center.jsp">应用中心</a></li>
        <li><a href="online-answer.jsp">在线答疑</a></li>
        <li><a href="expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="row">
            <div class="col-3">
                <img src="/static/dist/user/images/app-center-icon1.png"/>
                <p class="app-icon">志愿学堂</p>
            </div>
            <div class="col-3">
                <img src="/static/dist/user/images/app-center-icon2.png"/>
                <p class="app-icon">院校信息</p>
            </div>
            <div class="col-3">
                <img src="/static/dist/user/images/app-center-icon3.png"/>
                <p class="app-icon">高考备考</p>
            </div>
        </div>
        <div class="row">
            <div class="col-3">
                <img src="/static/dist/user/images/app-center-icon4.png"/>
                <p class="app-icon">测试专业</p>
            </div>
            <div class="col-3">
                <img src="/static/dist/user/images/app-center-icon5.png"/>
                <p class="app-icon">模拟填报</p>
            </div>
            <div class="col-3">
                <img src="/static/dist/user/images/app-center-icon6.png"/>
                <p class="app-icon">志愿指导</p>
            </div>
        </div>
    </div>
</div>
<%@include file="/common/footer.jsp"%>
</body>
</html>
