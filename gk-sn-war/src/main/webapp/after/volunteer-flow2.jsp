<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>志愿指导</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/common/styles/comm.css"/>
    <link rel="stylesheet" href="/static/dist/after/styles/after.css"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div class="w1000">
    <div class="content-title">
        <p class="fl w40"></p>

        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>志愿指导</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
</div>
<h6 class="w1000 ta content-title-sub">独创分差位次修正算法，为您科学推荐报考院校。原来填报志愿可以如此简单！</h6>
<div class="w1000 main-volunteer">
    <div class="fl">
        <div class="img">
            <img src="/static/dist/after/images/zd-pic.png" alt=""/>
            <strong>院校推荐、志愿指导</strong>
            <p>依据总分、位次获取推荐院校及志愿指导</p>
        </div>
    </div>
    <div class="fr">
        <div class="volunteer-flow2">
            <div class="info1">
                您的分数是<strong id="scoresNum">450分</strong>、<strong id="subType">理工类</strong>； <br/>
                依据2015年批次控制线，您的分数达到<strong id="batch">二批本科</strong>；<br/>
                请选择一下批次，进入院校推荐。
            </div>
            <div class="info2">
                <h3>普通第二批本科院校</h3>
                2015年控制线：<strog>文科 467分</strog>，<strong>理科440分</strong>
                <input type="button" class="btn" onclick="window.location.href='/after/volunteer-flow3.jsp'" value="开始"/>
            </div>
            <div class="form-control-btn">
                <input type="button" class="prev-btn" onclick="window.location.href='/after/after.jsp'" value="返回上一步"/>
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
</body>
</html>