<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <title>专家服务</title>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/user/styles/expert-service.min.css"/>
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
        <li><a href="/user/my-collect.jsp">我的收藏</a></li>
    </ul>
    <div class="content">
        <div class="search-content">
             <span>
                 <input type="text" id="search" placeholder="请输入关键字进行搜索">
                 <input type="button" value="搜索" id="btn-search">
                 <!--<button>我要预约</button>-->
                 <a href="/user/order-expert.jsp">我要预约</a>
            </span>
        </div>
        <div class="content-list">
            <div class="content-title-list">
                <div class="col-3 t-indent">标题</div>
                <div class="col-1 t-time">时间</div>
            </div>
            <div class="data-list">
            </div>
            <div class="more hide"><a href="javascript:void(0)" class="next-btn">加载更多</a></div>
        </div>
    </div>
</div>
<%@include file="/common/footer.jsp"%>
<script>
    seajs.use("http://cdn.gaokao360.net/static/global/user/scripts/expert-service.min");
</script>
</body>
</html>
