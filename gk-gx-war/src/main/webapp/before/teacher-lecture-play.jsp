<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>考前备考</title>
    <%@ include file="/common/meta.jsp"%>
    <%--<link rel="stylesheet" href="static/global/guide/styles/volunteer_forum_play.min.css" />--%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/guide/styles/volunteer_forum_play.min.css" />
    <style>
        #video{
            width: 760px;
            height: 428px;
            position: absolute;
            left: 50%;
            margin-left: -380px;
            top: 33px;
            background: #000;
        }
    </style>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div class="content">
    <div class="banner">
        <div class="video ta">
            <div id="logoutStatus">
                <img src="http://cdn.gaokao360.net/static/global/before/images/defualt-video.jpg"/>
                <p><a target="_blank" href="/login/login.jsp">登录</a>后,才可以正常播放</p>
            </div>
            <div class="videoHtml" id="videoHtml">
                <%--<video class="video" poster="http://media.html5media.info/poster.jpg" width="618" height="347" controls preload>--%>
                <%--<source id="source" src="" media="only screen and (min-device-width: 568px)"></source>--%>
                <%--</video>--%>
            </div>
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
<%--<script type="text/javascript" src="http://cdn.gaokao360.net/static/bower_components/html5media/html5media.min.js"></script>--%>
<script src="http://api.html5media.info/1.1.8/html5media.min.js"></script>
<script>
    //    seajs.use(["http://cdn.gaokao360.net/static/global/before/scripts/teacher-lecture-play.min","http://cdn.gaokao360.net/static/gx/before/scripts/teacher-lecture-play-user.min"]);
    seajs.use("/static/src/before/scripts/teacher-lecture-play");
</script>
<%@ include file="/common/footer.jsp"%>
</body>
</html>




