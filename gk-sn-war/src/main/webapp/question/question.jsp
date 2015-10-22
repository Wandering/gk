<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线互动</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/question/styles/question.min.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
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
                     <input type="text" placeholder="请输入你要提问的问题关键字" id="keywords"/>
                     <input type="button" value="搜索" id="search"/>
                     <a target="_blank" href="/question/ask.jsp">我要提问</a>
                </span>
            </div>

            <ul class="tabs-list mt60" id="tabs_list">
                <li class="active" data-method="getNew">最新解答</li>
                <li data-method="getHot">热门解答</li>
            </ul>

            <div id="question_content"></div>
            <div id="more_loading" style="display: none;"><a href="javascript:void(0)" class="next-btn">加载更多...</a></div>
        </section>

    </div>
</div>
<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use(["http://cdn.gaokao360.net/static/global/question/scripts/question","http://cdn.gaokao360.net/static/bower_components/utils/getTime.js"]);
</script>
</body>
</html>


