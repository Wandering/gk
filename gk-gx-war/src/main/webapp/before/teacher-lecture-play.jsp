<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>考前备考</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/guide/styles/volunteer_forum_play.min.css" />
</head>
<body>
<link href="http://cdn.gaokao360.net/static/bower_components/video-js/video-js.css" rel="stylesheet" type="text/css">
<script src="http://cdn.gaokao360.net/static/bower_components/video-js/video.js"></script>
<script>
    videojs.options.flash.swf = "http://cdn.gaokao360.net/static/bower_components/video-js/video-js.swf";
</script>


<%@ include file="/common/header.jsp"%>
<div class="content">
    <div class="banner">
        <div class="video ta">
            <div id="logoutStatus"></div>
            <div class="videoHtml" style="display:inline-block;width:760px;height:428px;position: relative;top: -22px" id="videoHtml"></div>
        </div>
    </div>
    <div class="mian-body w1000">
        <h1 id="title"></h1>
        <h6>简介：</h6>
        <p id="subcontent"></p>
        <h1>相关视频 <a>more</a></h1>
        <div class="error-tips hide"></div>
        <div class="episode-num mt20" id="episode-num"></div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="http://cdn.gaokao360.net/static/global/guide/scripts/flowplayer-3.2.13.min.js"></script>
<script>
//    seajs.use(["http://cdn.gaokao360.net/static/global/before/scripts/teacher-lecture-play.min","http://cdn.gaokao360.net/static/gx/before/scripts/teacher-lecture-play-user.min"]);
    seajs.use(["/static/src/before/scripts/teacher-lecture-play","http://cdn.gaokao360.net/static/global/before/scripts/teacher-lecture-play-user.min"]);
</script>
</body>
</html>




