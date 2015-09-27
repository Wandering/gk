<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <title>专家/服务详情</title>
    <link rel="stylesheet" href="/static/dist/user/styles/expert-service-detail.min.css"/>
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
        <div class="content-list">
            <div class="data-list">
                <div class="row">
                    <div class="col-1 ">预约主题</div>
                    <div class="col-3">我需要一名专家当面给我讲授一些志愿填报方面的知识</div>
                </div>
                <div class="row">
                    <div class="col-1 ">预约日期</div>
                    <div class="col-3">2015-9-11至2015-9-18</div>
                </div>
                <div class=" requirement-box-content">
                    <div class="req-box-left ">&nbsp;您的需求</div>
                    <div class="req-box-right">希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家希望是高中数学方面的专家</div>
                </div>
                <div class="row">
                    <div class="col-1 ">您的姓名</div>
                    <div class="col-3">谢敏</div>
                </div>
                <div class="row">
                    <div class="col-1 ">联系电话</div>
                    <div class="col-3">13811112222</div>
                </div>
                <div class="row">
                    <div class="col-1 ">QQ</div>
                    <div class="col-3">123456789</div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@include file="/common/footer.jsp"%>

<script>
    seajs.use("/static/src/user/scripts/expert-service-detail");
</script>
</body>
</html>
