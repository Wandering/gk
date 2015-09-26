<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>搜索结果页 </title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/question/styles/search_result.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">

        <section class="section-article">
            <div class="search-content ta">
                 <span>
                     <input type="text" placeholder="请输入你要提问的问题关键字" id="keywords"/>
                     <input type="button" value="搜索" id="search_button"/>
                     <a target="_blank" href="/question/ask.jsp">我要提问</a>
                </span>
            </div>
        </section>

        <h1 class="search-result-num">搜索结果<span>共3条</span></h1>
        <div id="search_result_content"></div>
        <div><a href="javascript:void(0)" class="next-btn">加载更多...</a></div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/question/scripts/question_search_result");
</script>
</body>
</html>
