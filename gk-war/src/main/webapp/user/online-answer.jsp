<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp" %>
    <title>在线答疑</title>
    <link rel="stylesheet" href="/static/dist/user/styles/online-answer.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>

<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li class="active"><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="toggle-nav">
            <div class="btn btn-selected" data-isAnswer="1">已解答</div>
            <div class="btn" data-isAnswer="0">未解答</div>
        </div>
        <div class="search-content">
             <span>
                 <input type="text" placeholder="请输入关键词进行搜索" id="keywords">
                 <input type="button" value="搜索" id="search">
                 <a target="_blank" href="/question/ask.jsp">我要提问</a>
            </span>
        </div>
        <div id="detail_content_question">
        </div>
        <div><a href="javascript:void(0)" class="next-btn hide">加载更多...</a></div>
        <%--<div class="detail-content">--%>
            <%--<div class="detail-header">--%>
                <%--<span class="detail-title">军队、武警部队院校招生，国防生体格检查标准</span>--%>
                <%--<span class="upload-time">上传时间：2015-05-15 09:52</span>--%>
            <%--</div>--%>
            <%--<div class="detail-info">徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头.--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="detail-content">--%>
            <%--<div class="detail-header">--%>
                <%--<span class="detail-title">军队、武警部队院校招生，国防生体格检查标准</span>--%>
                <%--<span class="upload-time">上传时间：2015-05-15 09:52</span>--%>
            <%--</div>--%>
            <%--<div class="detail-info">徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头.--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="detail-content">--%>
            <%--<div class="detail-header">--%>
                <%--<span class="detail-title">军队、武警部队院校招生，国防生体格检查标准</span>--%>
                <%--<span class="upload-time">上传时间：2015-05-15 09:52</span>--%>
            <%--</div>--%>
            <%--<div class="detail-info">徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头.--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>

</div>

<%@include file="/common/footer.jsp"%>
<script type="text/javascript" src="/static/bower_components/utils/getTime.js"></script>
<script>
    seajs.use("/static/src/user/scripts/online-answer");
</script>
</body>
</html>
