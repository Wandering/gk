<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>专家评测</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/after/styles/expert-evaluating.css" />
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
                <span>评测结果</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <p class="second-title">独创分差位次修正算法，为您科学推荐报考院校。原来填报志愿可以如此简单！</p>
    </div>
    <div class="w1000">
        <img src="/static/dist/after/images/step-banner.png" class="step-banner"/>
        <table border="1" cellspacing="0">
            <tr class="t-title">
                <th>姓名</th>
                <th>性别</th>
                <th>学校</th>
                <th>年级</th>
                <th>科类</th>
                <th>准考证</th>
                <th>高考分数</th>
                <th>一批本科省控线</th>
            </tr>
            <tr>
                <td>一班</td>
                <td>2012-5-10</td>
                <td>标题1</td>
                <td>标题1</td>
                <td>标题1</td>
                <td>标题1</td>
                <td>标题1</td>
                <td>标题1</td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/consult/scripts/expert-evaluating");
</script>
</body>
</html>
