<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>名师讲堂</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/before/styles/before.css"/>
</head>
<body>
<%@ include file="/before/header-before.jsp"%>

<div class="w1000">
    <div class="content-title">
        <p class="fl w40"></p>

        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>名师讲堂</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
</div>
<h6 class="w1000 ta content-title-sub">让我们的专家给您传授高考答题技巧</h6>

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
    seajs.use("/static/src/before/scripts/teacher-lecture");
</script>
</body>
</html>
