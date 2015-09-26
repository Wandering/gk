<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>搜索结果页 </title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/question/styles/search_result.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">

        <section class="section-article">
            <div class="search-content ta">
                 <span>
                     <input type="text" placeholder="请输入你要提问的问题关键字"/>
                     <input type="button" value="搜索"/>
                     <a target="_blank" href="/question/ask.jsp">我要提问</a>
                </span>
            </div>
        </section>

        <h1 class="search-result-num">搜索结果<span>共3条</span></h1>
        <div class="detile-content mt20">
            <div class="detile-header">
                <span class="order-number">1</span>
                <span class="detile-title">军队、武警部队院校招生，国防生体格检查标准</span>
                <span class="fr">2015-05-15 09:52</span>
            </div>
            <div class="detile-info mt20">
                徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头
            </div>
        </div>
        <div class="detile-content mt20">
            <div class="detile-header">
                <span class="order-number">2</span>
                <span class="detile-title">军队、武警部队院校招生，国防生体格检查标准</span>
                <span class="fr">2015-05-15 09:52</span>
            </div>
            <div class="detile-info mt20">
                徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/question/scripts/question_search_result");
</script>
</body>
</html>
