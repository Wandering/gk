<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线互动</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/question/styles/question.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">
        <div class="content-title mt60">
            <p class="fl w40"></p>
            <p class="fl w20">
                <i class="fl arraw"></i>
                <span>在线互动</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">每周5x8专业客服为您竭诚服务</h6>


        <section class="section-article">
            <div class="search-content ta">
                 <span>
                     <input type="text" placeholder="请输入专业名称进行搜索"/>
                     <input type="button" value="搜索"/>
                     <button>我要提问</button>
                </span>
            </div>

            <ul class="tabs-list mt60">
                <li class="active" data-method="getNew">最新问题</li>
                <li data-method="getHot">热门问题</li>
            </ul>

            <div id="question_content"></div>
        </section>

    </div>
</div>
<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="/static/bower_components/utils/getTime.js"></script>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/question/scripts/question");
</script>
</body>
</html>


