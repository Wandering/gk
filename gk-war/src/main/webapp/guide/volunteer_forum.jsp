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
            <div class="play-video" id="play-video">
                <object class id="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="760" height="428">
                    <param name="movie" value="/static/src/before/scripts/flvplayer.swf">
                    <param name="quality" value="high">
                    <param name="allowFullScreen" value="true">
                    <param name="IsAutoPlay" value="0" />
                    <param name="BufferTime" value="3" />
                    <param name="FlashVars" value="/static/src/before/images/1234.flv">
                    <embed src="/static/src/before/scripts/flvplayer.swf" allowfullscreen="true" flashvars="/static/src/before/images/1234.flv" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="760" height="428"></embed>
                </object>
            </div>
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
