<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>高考心理</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/before/styles/before.min.css"/>
</head>
<body>
<%@ include file="/before/header-before.jsp"%>

<div class="w1000">
    <div class="content-title">
        <p class="fl w40"></p>
        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>高考心理</span>
            <i class="fr arraw"></i>
        </p>
        <p class="fl w40"></p>
    </div>
</div>
<h6 class="w1000 ta content-title-sub">分数不理想也没有关系，专家告诉您如何调整，以最好的状态应对高考，没什么了不起的。</h6>

<div class="w1000 main-video">
    <div class="tabs">
        <div class="filtrate">
            <select class="sel-item subjectList" name="" id="">
                <option value="">全部科目</option>
            </select>
            <select class="sel-item subject-fun" name="" id="">
                <option value="1">按上传顺序</option>
                <option value="2">按播放次数</option>
            </select>
        </div>
        <div class="into-search">
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" onkeydown="if(event.keyCode==13)return false;" id="searchVal" placeholder="请输入搜索内容"/>
                     <input type="button" id="search-btn" value="搜索"/>
                </span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <div class="tabs-content">
        <div class="tabs-content-list">
            <ul class="main-video-list" id="list-msg-item" pageNo="0"></ul>
            <a href="javascript:;" class="next-btn" id="nextPage">加载更多...</a>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("http://cdn.gaokao360.net/static/global/before/scripts/mentality.min");
</script>
</body>
</html>
