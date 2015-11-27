<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>详情页</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/consult/styles/gk_hot_detile.min.css" />
</head>
<style>
    .section-article article p span{
        white-space: inherit!important;
    }
    .section-article article{
        width: 760px;
        margin: 20px;
    }
    #section_article_c table{
        background: #ffff00!important: ;
    }
</style>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <div class="w1000">

        <%--<div class="search-content ta mt20">--%>
                 <%--<span>--%>
                     <%--<input type="text" placeholder="请输入搜索关键字" id="keywords"/>--%>
                     <%--<input type="button" value="搜索" id="search"/>--%>
                     <%--&lt;%&ndash;<a target="_blank" href="/question/ask.jsp">我要提问</a>&ndash;%&gt;--%>
                <%--</span>--%>
        <%--</div>--%>

        <div class="main-body">
            <section class="section-article" id="section_article">
                <h1 id="section_article_t"></h1>
                <article id="section_article_c">

                </article>
            </section>
            <section class="ask-list" id="ask_list">
            </section>
        </div>
    </div>
</div>
<script>
    document.getElementById('iframe').contentWindow.document.body.style.backgroundColor="#f00";
</script>
<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
//    seajs.use(["http://cdn.gaokao360.net/static/global/consult/scripts/gk_hot_detail.min",'http://cdn.gaokao360.net/static/bower_components/utils/getTime.js']);
    seajs.use(["/static/src/consult/scripts/gk_hot_detail",'http://cdn.gaokao360.net/static/bower_components/utils/getTime.js']);
</script>
</body>
</html>


