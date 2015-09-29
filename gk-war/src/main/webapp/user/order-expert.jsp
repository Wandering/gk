<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp" %>
    <title>我要预约</title>
    <link rel="stylesheet" href="/static/dist/user/styles/order-expert.min.css"/>
    <link rel="stylesheet" href="/static/bower_components/laydate/need/laydate.css"/>
    <link rel="stylesheet" href="/static/bower_components/laydate/skins/dahong/laydate.css"/>
    <link rel="stylesheet" href="/static/bower_components/kindeditor/themes/default/default.css"/>
</head>
<body>
<%@include file="/common/header.jsp" %>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li><a href="modify-psd.jsp">修改密码</a></li>
        <li><a href="app-center.jsp">应用中心</a></li>
        <li><a href="online-answer.jsp">在线答疑</a></li>
        <li class="active"><a href="expert-service.jsp">专家服务</a></li>
    </ul>

    <div class="content">
        <div class="search-content">
             <span>
                 <input type="text" placeholder="请输入搜索内容">
                 <input type="button" value="搜索">
            </span>
        </div>
        <div class="input-item-comm">
            <span class="w-title">预约主题</span>
            <input type="text" class="comm-input order-theme">
        </div>
        <div class="input-item-comm">
            <span class="w-title">预约日期</span>
            <input onclick="laydate()" class="comm-input data-start laydate-icon">
            &nbsp;至
            <input onclick="laydate()" class="comm-input data-end laydate-icon">
            <span class="des-text">请告诉我们您期望与专家见面沟通的日期</span>
        </div>
        <div class="input-item-comm">
            <span class="w-title">您的需求</span>
            <textarea name="content" id="content"></textarea>
        </div>
        <div class="input-item-comm">
            <span class="w-title">您的姓名</span>
            <input type="text" class="comm-input name">
        </div>
        <div class="input-item-comm">
            <span class="w-title">联系电话</span>
            <input type="text" class="comm-input mobile">
        </div>
        <div class="input-item-comm">
            <span class="w-title">QQ号码</span>
            <input type="text" class="comm-input qq">
        </div>
        <div class="btn-box">
            <div class="btn btn-submit">提交</div>
            <div class="btn btn-submit">重置</div>
        </div>
    </div>
</div>


<%@include file="/common/footer.jsp" %>

<script src="/static/bower_components/kindeditor/kindeditor.js"></script>
<script src="/static/bower_components/kindeditor/lang/zh-CN.js"></script>

<script>
    seajs.use("/static/src/user/scripts/order-expert");
</script>

</body>
</html>
