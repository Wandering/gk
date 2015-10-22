<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线互动详情页</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/question/styles/question_detile.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <div class="w1000">

        <div class="main-body">
            <section class="section-article" id="section_article">
            </section>

            <section class="ask-list">
                <h3>最新问题</h3>
                <ul id="new">
                </ul>
                <h3>热门问题</h3>
                <ul id="hot">
                </ul>
            </section>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use(["http://cdn.gaokao360.net/static/global/question/scripts/question_detile.min","/static/bower_components/utils/getTime.js"]);
</script>
</body>
</html>


