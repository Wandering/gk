<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp" %>
    <title>个人信息</title>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/user/styles/personal-info.min.css"/>
    <%--<link rel="stylesheet" href="/static/global/user/styles/personal-info.min.css"/>--%>
</head>
<body>
<%@include file="/common/header.jsp" %>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
        <li class="active"><a href="/user/my-collect.jsp">我的收藏</a></li>
    </ul>
    <div class="container">
        <div class="content">
            <div class="collect" id="">
                <table id="collect-table">
                    <thead>
                    <tr>
                        <th>院校名称</th>
                        <th>所在地区</th>
                        <th>院校类型</th>
                        <th>院校隶属</th>
                        <th>院校特征</th>
                        <th>取消收藏</th>
                    </tr>
                    </thead>
                    <tbody id="list-msg-item" pageNo="0">
                    </tbody>
                </table>
                <a href="javascript:;" class="next-btn hide" style="display: none;" id="nextPage">加载更多...</a>
            </div>

        </div>
    </div>
</div>

<script>
    seajs.use("/static/src/user/scripts/my-collect");
    //        seajs.use("http://cdn.gaokao360.net/static/global/user/scripts/my-collect");
</script>
<%@include file="/common/footer.jsp" %>
</body>
</html>
