<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>专家服务</title>
    <link rel="stylesheet" href="/static/dist/user/styles/expert-service.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li class="active"><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="search-content">
             <span>
                 <input type="text" placeholder="请输入专业名称进行搜索">
                 <input type="button" value="搜索">
                 <!--<button>我要预约</button>-->
                 <a href="order-expert.jsp">我要预约</a>
            </span>
        </div>
        <div class="content-list">
            <div class="content-title-list">
                <div class="col-3 t-indent">标题</div>
                <div class="col-1">时间</div>
            </div>
            <div class="data-list">
                <a class="row" href="expert-service-detail.jsp">
                    <div class="col-3 ">我需要一名专家当面给我讲授一些志愿填报方面的知识！</div>
                    <div class="col-1">2015-05-15 09:52</div>
                </a>
                <div class="row">
                    <div class="col-3 ">我需要一名专家当面给我讲授一些志愿填报方面的知识！</div>
                    <div class="col-1">2015-05-15 09:52</div>
                </div>
                <div class="row">
                    <div class="col-3 ">我需要一名专家当面给我讲授一些志愿填报方面的知识！</div>
                    <div class="col-1">2015-05-15 09:52</div>
                </div>
                <div class="row">
                    <div class="col-3 ">我需要一名专家当面给我讲授一些志愿填报方面的知识！</div>
                    <div class="col-1">2015-05-15 09:52</div>
                </div>
                <div class="row">
                    <div class="col-3 ">我需要一名专家当面给我讲授一些志愿填报方面的知识！</div>
                    <div class="col-1">2015-05-15 09:52</div>
                </div>
                <div class="row">
                    <div class="col-3 ">我需要一名专家当面给我讲授一些志愿填报方面的知识！</div>
                    <div class="col-1">2015-05-15 09:52</div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/common/footer.jsp"%>

</body>
</html>
