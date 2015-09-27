<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>真题密卷</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/before/styles/before.css"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
</div>
<div class="w1000">
    <div class="content-title">
        <p class="fl w40"></p>

        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>真题密卷</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
</div>
<h6 class="w1000 ta content-title-sub">听完专家的解读，接下来可以练练手了，我们为您准备了著名示范性高中高考模拟试题和自主命制的高考真题密卷</h6>

<div class="w1000 main-video">
    <div class="tabs">
        <div class="filtrate">
            <select class="sel-item subjectList" name="" id="">
                <option value="">全部</option>
            </select>
            <%--<select class="sel-item" name="" id="">--%>
                <%--<option value="">模</option>--%>
            <%--</select>--%>
            <%--<select class="sel-item" name="" id="">--%>
                <%--<option value="">西安铁一中</option>--%>
            <%--</select>--%>
            <select class="sel-item" name="" id="">
                <option value="">2015年(vip用户专享)</option>
            </select>
        </div>
        <div class="into-search">
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" id="searchVal" placeholder="请输入搜索内容"/>
                     <input type="button" id="search-btn" value="搜索"/>
                </span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <div class="tabs-content">
        <div class="tabs-content-list">
            <ul class="main-exam-list" id="list-msg-item" pageNo="0"></ul>
            <a href="javascript:;" class="next-btn" id="nextPage">下一页</a>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/before/scripts/exam");
</script>
</body>
</html>