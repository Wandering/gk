<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>详情页</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/consult/styles/gk_hot_detile.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">

        <div class="search-content ta mt20">
                 <span>
                     <input type="text" placeholder="请输入搜索关键字" id="keywords"/>
                     <input type="button" value="搜索" id="search"/>
                     <%--<a target="_blank" href="/question/ask.jsp">我要提问</a>--%>
                </span>
        </div>

        <div class="main-body">
            <section class="section-article" id="section_article">
            </section>

            <section class="ask-list" id="ask_list">
            </section>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="/static/bower_components/utils/getTime.js"></script>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/consult/scripts/gk_hot_detile");
</script>
</body>
</html>


