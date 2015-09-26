<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>专业测评</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/consult/styles/consult.css" />
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
                <span>专业测评</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">最大限度地帮助学生深刻地认识自我，定位我的职业，锁定我的专业</h6>


        <section class="section-article">
            <div class="search-content">
                 <span>
                     <input type="text" placeholder="请输入专业名称进行搜索"/>
                     <input type="button" value="搜索"/>
                </span>
            </div>
            <div class="video">
                <video id="home_video" controls="" preload="none" poster="/img/poster.jpg" class="video-js vjs-default-skin">
                    <source src="http://vjs.zencdn.net/v/oceans.mp4" type="video/mp4">
                    <source src="http://vjs.zencdn.net/v/oceans.webm" type="video/webm">
                    <track kind="captions" src="/vtt/captions.vtt" srclang="en" label="English">
                    </track>
                </video>
            </div>
            <p>专业升学测评是一项专门为中国在校中学生升学择业而设计的专业测评软件，在中学生进行专业抉择的关键时刻给予专业的指导及合理的建议。</p>
            <div class="into-evalution ta">
                <a>进入测评</a>
            </div>
        </section>


    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/consult/scripts/consult");
</script>
</body>
</html>

