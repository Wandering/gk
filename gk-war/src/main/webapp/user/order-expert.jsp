<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>我要预约</title>
    <link rel="stylesheet" href="/static/dist/user/styles/order-expert.min.css"/>
    <link rel="stylesheet" href="/static/bower_components/kindeditor/themes/default/default.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>


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
            <input type="text" class="comm-input ">
        </div>
        <div class="input-item-comm">
            <span class="w-title">预约日期</span>
            <input type="date" class="comm-input data-start">&nbsp;至
            <input type="date" class="comm-input data-end">
            <span class="des-text">请告诉我们您期望与专家见面沟通的日期</span>
        </div>
        <div class="input-item-comm">
            <span class="w-title">您的需求</span>
            <textarea name="content"></textarea>
        </div>
        <div class="input-item-comm">
            <span class="w-title">您的姓名</span>
            <input type="text" class="comm-input ">
        </div>
        <div class="input-item-comm">
            <span class="w-title">联系电话</span>
            <input type="text" class="comm-input ">
        </div>
        <div class="input-item-comm">
            <span class="w-title">QQ号码</span>
            <input type="text" class="comm-input ">
        </div>
        <div class="btn-box">
            <div class="btn btn-submit">提交</div>
            <div class="btn btn-submit">重置</div>
        </div>
    </div>
</div>


<%@include file="/common/footer.jsp"%>

<script src="../static/bower_components/jquery/dist/jquery.min.js"></script>
<script src="../static/bower_components/kindeditor/kindeditor-min.js"></script>
<script src="../static/bower_components/kindeditor/lang/zh-CN.js"></script>
<script>
    //编辑框
    var editor;
    KindEditor.ready(function (K) {
        editor = K.create('textarea[name="content"]', {
            resizeType: 1,
            allowPreviewEmoticons: false,
            allowImageUpload: false,
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'image', 'link']
        });
    });
</script>
</body>
</html>
