<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>高考热点</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/consult/styles/gk_hot.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <div class="w1000">

        <div class="content-title mt60">
            <p class="fl w40"></p>
            <p class="fl w20">
                <i class="fl arraw"></i>
                <span>高考热点</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>

        <section class="section-article">
            <div class="search-content ta">
                 <span>
                     <input type="text" placeholder="" id="key_words"/>
                     <input type="button" value="搜索" id="search"/>
                     <%--<a target="_blank" href="/question/ask.jsp">我要提问</a>--%>
                </span>
            </div>
        </section>

        <div id="wrapper"></div>
        <div><a href="javascript:void(0)" class="next-btn" style="display: none">加载更多...</a></div>

        <%--<div class="detile-content mt20">--%>
            <%--<div class="detile-header">--%>
                <%--<span class="order-number">2</span>--%>
                <%--<span class="detile-title">军队、武警部队院校招生，国防生体格检查标准</span>--%>
                <%--<span class="fr">上传时间：2015-05-15 09:52</span>--%>
            <%--</div>--%>
            <%--<div class="detile-info mt20">--%>
                <%--徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="/static/bower_components/utils/getTime.js"></script>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/consult/scripts/gk_hot");
</script>
</body>
</html>
