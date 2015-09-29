<!DOCTYPE html>
<html>
<head>
    <title>在线互动详情页</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="../../dist/question/styles/question_detile.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">

        <%--<div class="search-content ta mt20">--%>
                 <%--<span>--%>
                     <%--<input type="text" placeholder="请输入专业名称进行搜索"/>--%>
                     <%--<input type="button" value="搜索"/>--%>
                     <%--<button>我要提问</button>--%>
                <%--</span>--%>
        <%--</div>--%>

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
<script type="text/javascript" src="/static/bower_components/utils/getTime.js"></script>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/question/scripts/question_detile");
</script>
</body>
</html>


