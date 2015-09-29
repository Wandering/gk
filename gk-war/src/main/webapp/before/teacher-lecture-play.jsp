<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>考前备考</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/guide/styles/volunteer_forum_play.css" />
</head>
<body>
<%@ include file="/before/header-before.jsp"%>
<div class="content">
    <div class="banner">
        <div class="video mt60 ta">
            <a href="" style="display:inline-block;width:760px;height:428px;position: relative;top: 32px" id="player"></a>
        </div>
    </div>
    <div class="mian-body w1000">
        <h1>徐老师<span>语文</span></h1>
        <h6>简介：</h6>
        <p>徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手， 陕西省首批学科带头人。陕西省中语会理事。陕西师范大学硕士合作指导教师。先后有15年参加陕西省高考语文评卷工作，曾担任题小组长、题组长。</p>
        <h1>相关视频 <a>more</a></h1>
        <div class="episode-num mt20" id="episode-num"></div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="/static/src/guide/scripts/flowplayer-3.2.13.min.js"></script>
<script>
    seajs.use("/static/src/before/scripts/teacher-lecture-play");
</script>
</body>
</html>
