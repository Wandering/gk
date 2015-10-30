<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>志愿讲堂</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/guide/styles/volunteer_forum.min.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
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
        <h6 class="w1000 ta sub-title c888 mt20">全面介绍广西高考志愿填报的基础知识、规则及方法，帮您掌握志愿填报的方法和技巧走出填报误区。</h6>


        <div id="tab_title_content">

        </div>

        <section class="section-article">
        </section>
        <div><a href="javascript:void(0)" class="next-btn">加载更多...</a></div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
//    seajs.use(["/static/src/guide/scripts/volunteer_forum",'http://cdn.gaokao360.net/static/bower_components/utils/getTime.js']);
    seajs.use(["http://cdn.gaokao360.net/static/gx/guide/scripts/volunteer_forum",'http://cdn.gaokao360.net/static/bower_components/utils/getTime.js']);
</script>
</body>
</html>
