<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>专业测评</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/consult/styles/consult.min.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <div class="w1000 hide">
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
                     <input type="text" placeholder="请输入专业名称进行搜索" id="keywords"/>
                     <input type="button" value="搜索" id="search"/>
                </span>
            </div>
            <div class="video ta">
                <a href="http://www.gkzy114.com/Public/Uploads/zhuanyeceping/zhuanyeceping-2015-03-05.flv" style="display:inline-block;width:80%;height:460px" id="player"></a>
            </div>
            <p>专业升学测评是一项专门为中国在校中学生升学择业而设计的专业测评软件，在中学生进行专业抉择的关键时刻给予专业的指导及合理的建议。</p>
            <div id="error_tip"></div>
            <div class="into-evalution ta">
                <a>进入测评</a>
            </div>
        </section>


    </div>
    <div class="loader">
        <div class="loader-inner line-scale">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript" src="http://cdn.gaokao360.net/static/global/guide/scripts/flowplayer-3.2.13.min.js"></script>
<script type="text/javascript">
    seajs.use("/static/src/consult/scripts/consult");
//    seajs.use("http://cdn.gaokao360.net/static/global/consult/scripts/consult.min");
</script>
</body>
</html>

