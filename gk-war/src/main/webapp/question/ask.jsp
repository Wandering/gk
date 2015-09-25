<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>提问页面 </title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/question/styles/ask.css" />
    <link rel="stylesheet" href="/static/bower_components/kindeditor/themes/default/default.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">

        <section class="section-article">
            <div class="search-content ta">
                 <span>
                     <input type="text" placeholder=""/>
                     <input type="button" value="搜索"/>
                     <button>我要提问</button>
                </span>
            </div>
        </section>

        <h1 class="search-result-num">提问</h1>
        <div class="ask-title">
            <p style="display: none;color: #f00;margin: 20px 0" id="error"></p>
            <input type="text" placeholder="请填写标题" id="title">
            <div class="mt20">
                <textarea name="content" id="content"></textarea>
            </div>
            <button id="submit_question">提交</button>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>

<div class="custom-model" style="display: none">
    <div class="custom-content">
        <div class="custom-head"></div>
        <div class="custom-body"></div>
        <div class="custom-footer ta">
            <button>提交</button>
        </div>
    </div>
</div>
<script src="/static/bower_components/kindeditor/kindeditor.js"></script>
<script src="/static/bower_components/kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/question/scripts/ask");
</script>
</body>
</html>
