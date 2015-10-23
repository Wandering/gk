<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>高考热点1</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/consult/styles/gk_hot.min.css" />
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
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use(["http://cdn.gaokao360.net/static/global/consult/scripts/gk_hot.min",'http://cdn.gaokao360.net/static/bower_components/utils/getTime.js']);
</script>
</body>
</html>
