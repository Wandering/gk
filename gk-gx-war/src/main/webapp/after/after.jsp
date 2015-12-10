<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>志愿指导</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/after/styles/after.min.css"/>
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
<div class="w1000 tips">
    <strong>温馨提示：</strong>
    <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
</div>
<div class="w1000 main-volunteer">
    <div class="fl">
        <div class="img">
            <img src="http://cdn.gaokao360.net/static/global/after/images/zd-pic.png" alt=""/>
            <strong>院校推荐、志愿指导</strong>
            <p>依据总分、位次获取推荐院校及志愿指导</p>
        </div>
    </div>
    <div class="fr">

        <div class="error-tips hide" style="width:316px;"></div>
        <div class="volunteer-flow1" id="volunteer-flow1">
            <form action="" class="zd-form">
                <div class="form-control">
                    <label>您的考号</label>
                    <input type="text" class="input" id="candidateNumber-input" name="" placeholder="请输入您的考号" id=""/>
                </div>
                <div class="form-control">
                    <label>您的分数</label>
                    <input type="text" class="input" id="aggregateScore-input" name="" placeholder="请输入您的分数" id=""/>
                </div>
                <div class="form-control">
                    <label>您的位次</label>
                    <input type="text" class="input" id="ranking-input" name="" placeholder="请输入您的位次" id=""/>
                </div>
                <div class="form-control category mt10">
                    <label class="t">选择文理科</label>
                    <span class="group-radio">
                        <label><input type="radio" name="subjectType" value="文史" /> 文史</label>
                        <label><input type="radio" name="subjectType" value="理工" /> 理工</label>
                    </span>
                </div>
                <div class="form-control yzm-control">
                    <label>验证码</label>
                    <input type="text" id="yzmDreamSchool-input"  class="input yzm" name="" placeholder="请填写验证码" id=""/>
                    <a>
                        <img id="yzmDreamSchool" src="" />
                    </a>
                </div>
                <div class="form-control-btn">
                    <input type="button" class="btn" id="volunteer-flow1-btn" value="下一步"/>
                </div>
            </form>
        </div>

        <div class="volunteer-flow2" id="volunteer-flow2" style="display:none;">
            <div class="info1">
                您的分数是<strong id="scoresNum"></strong>、<strong id="subType"></strong>； <br/>
                依据2015年批次控制线，您的分数达到<strong id="batch"></strong>；<br/>
                请选择一下批次，进入院校推荐。
            </div>
            <div class="school-list-info" id="school-list-info">
                <%--<div class="info2">--%>
                    <%--<h3>普通第二批本科院校</h3>--%>
                    <%--2015年控制线：<strong>文科 467分</strong>，<strong>理科440分</strong>--%>
                    <%--<a href="/after/volunteer-flow3.jsp" class="btn">开始</a>--%>
                <%--</div>--%>
                <%--<div class="info2">--%>
                    <%--<h3>普通第二批本科院校</h3>--%>
                    <%--2015年控制线：<strong>文科 467分</strong>，<strong>理科440分</strong>--%>
                    <%--<a href="/after/volunteer-flow3.jsp" class="btn">开始</a>--%>
                <%--</div>--%>
            </div>
            <div class="form-control-btn">
                <input type="button" class="prev-btn" id="prev-btn" value="返回上一步"/>
            </div>
        </div>
    </div>
</div>
<script>
//    seajs.use("http://cdn.gaokao360.net/static/gx/after/scripts/after.min");
    seajs.use("/static/src/after/scripts/after");
</script>
<%@ include file="/common/footer.jsp"%>
</body>
</html>
