<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>志愿讲堂</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/guide/styles/volunteer_forum.css" />
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
                <span>志愿讲堂</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">全面介绍陕西省高考志愿填报的基础知识、规则及方法，帮您掌握志愿填报的方法和技巧走出填报误区。</h6>

        <div class="video mt60">
            <video id="home_video" controls="" preload="none" poster="/img/poster.jpg" class="video-js vjs-default-skin">
                <source src="http://vjs.zencdn.net/v/oceans.mp4" type="video/mp4">
                <source src="http://vjs.zencdn.net/v/oceans.webm" type="video/webm">
                <track kind="captions" src="/vtt/captions.vtt" srclang="en" label="English">
                </track>
            </video>
        </div>

        <div id="tab_title_content">

        </div>

        <section class="section-article">
        </section>
        <div><a href="javascript:void(0)" class="next-btn">加载更多...</a></div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="/static/bower_components/utils/getTime.js"></script>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/guide/scripts/volunteer_forum");
</script>
</body>
</html>
