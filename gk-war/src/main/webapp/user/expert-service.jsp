<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <title>专家服务</title>
    <link rel="stylesheet" href="/static/dist/user/styles/expert-service.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li class="active"><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="search-content">
             <span>
                 <input type="text" id="search" placeholder="请输入专业名称进行搜索">
                 <input type="button" value="搜索" id="btn-search">
                 <!--<button>我要预约</button>-->
                 <a href="/user/order-expert.jsp">我要预约</a>
            </span>
        </div>
        <div class="content-list">
            <div class="content-title-list">
                <div class="col-3 t-indent">标题</div>
                <div class="col-1">时间</div>
            </div>
            <div class="data-list">
                <a class="row" href="javascript:void(0);" id="go-detail">
                    <div class="col-3" id="title" data-id=""></div>
                    <div class="col-1" id="createTime"></div>
                </a>
            </div>
        </div>
    </div>
</div>
<%@include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/user/scripts/expert-service");
</script>
</body>
</html>
