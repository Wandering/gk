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
<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
</div>
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
<div class="w1000 tips">
    <strong>温馨提示：</strong>
    <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
</div>
<div class="w1000 main-volunteer">
    <div class="fl">
        <div class="img">
            <img src="/static/dist/after/images/zd-pic.png" alt=""/>
            <strong>院校推荐、志愿指导</strong>
            <p>依据总分、位次获取推荐院校及志愿指导</p>
        </div>
    </div>
    <div class="fr">
        <form action="" class="zd-form">
            <div class="form-control">
                <label>您的考号</label>
                <input type="text" class="input" name="" placeholder="请输入您的考号" id=""/>
            </div>
            <div class="form-control">
                <label>您的分数</label>
                <input type="text" class="input" name="" placeholder="请输入您的分数" id=""/>
            </div>
            <div class="form-control">
                <label>您的位次</label>
                <input type="text" class="input" name="" placeholder="请输入您的位次" id=""/>
            </div>
            <div class="form-control yzm-control">
                <label>验证码</label>
                <input type="text" class="input yzm" name="" placeholder="请填写验证码" id=""/>
                <img src="/static/dist/after/images/yzm.png" alt=""/>
            </div>
            <div class="form-control-btn">
                <input type="button" class="btn" id="volunteer-flow1-btn" onclick="window.location.href='/after/volunteer-flow2.jsp'" value="下一步"/>
            </div>

        </form>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>

</body>
</html>
