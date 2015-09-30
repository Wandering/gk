<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <%@include file="/common/meta.jsp"%>
    <title>修改密码</title>
    <link rel="stylesheet" href="/static/dist/user/styles/modify-psd.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>
<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li class="active"><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="input-item-comm">
            <span class="w-title">登录账号</span>
            <span id="tel"></span>
        </div>
        <div class="input-item-comm">
            <span class="w-title">当前密码</span>
            &nbsp;<input type="password" class="comm-psd" id="current-psd"/>
            <span class="error-tips"></span>
        </div>
        <div class="input-item-comm">
            <span class="w-title">新密码</span>
            <input type="password" class="comm-psd"  id="new-psd"/>
            <span class="error-tips"></span>
        </div>
        <div class="input-item-comm">
            <span class="w-title">确认密码</span>
            <input type="password" class="comm-psd" id="confirm-psd"/>
            <span class="error-tips"></span>
        </div>
        <div class="btn btn-submit">提交</div>
    </div>

</div>
<%@include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/user/scripts/modify-psd");
</script>
</body>
</html>
