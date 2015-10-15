<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp" %>
    <title>应用中心</title>
    <link rel="stylesheet" href="/static/dist/user/styles/app-center.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>

<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li class="active"><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="row">
            <div class="col-3">
                <a href="/guide/volunteer_forum.jsp" target="_blank">
                    <img src="/static/dist/user/images/app-center-icon1.png"/>
                </a>
                <p class="app-icon"><a href="/guide/volunteer_forum.jsp" target="_blank">志愿学堂</a></p>
            </div>
            <div class="col-3">
                <a href="/consult/school_info.jsp" target="_blank">
                    <img src="/static/dist/user/images/app-center-icon2.png"/>
                </a>
                <p class="app-icon"><a href="/consult/school_info.jsp" target="_blank">院校信息</a></p>
            </div>
            <div class="col-3">
                <a href="/before/before.jsp">
                    <img src="/static/dist/user/images/app-center-icon3.png"/>
                </a>
                <p class="app-icon">高考备考</p>
            </div>
        </div>
        <div class="row">
            <div class="col-3">
                <a href="/consult/consult.jsp">
                    <img src="/static/dist/user/images/app-center-icon4.png"/>
                </a>
                <p class="app-icon">测试专业</p>
            </div>
            <div class="col-3">
                <a href="">
                    <img src="/static/dist/user/images/app-center-icon5.png"/>
                </a>
                <p class="app-icon">模拟填报</p>
            </div>
            <div class="col-3">
                <a href="/after/after.jsp">
                    <img src="/static/dist/user/images/app-center-icon6.png"/>
                </a>
                <p class="app-icon">志愿指导</p>
            </div>
        </div>
    </div>
</div>
<%@include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/user/scripts/app-center");
</script>
</body>
</html>
