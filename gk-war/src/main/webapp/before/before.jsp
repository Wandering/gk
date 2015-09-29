<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>考前备考</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/before/styles/before.css"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp" %>

</div>

<div class="w1000">
    <div class="content-title">
        <p class="fl w40"></p>

        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>指导流程</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
</div>
<div class="line-auto"></div>
<div class="w1000 flow-main">
    <ul class="flow-main-list">
        <li class="item">
            <img src="/static/dist/before/images/flow-img1.png" alt=""/>
            <a target="_blank" href="/before/teacher-lecture.jsp?classifyType=1&searchV=" class="name">名师讲堂</a>
            <span class="num">1</span>
            <p class="txt">名师精品课程，全面剖析高考知识点以及提分技巧。</p>
        </li>
        <li class="item">
            <img src="/static/dist/before/images/flow-img1.png" alt=""/>
            <a target="_blank" href="/before/exam.jsp?classifyType=3&searchV=" class="name">真题密卷</a>
            <span class="num">2</span>
            <p class="txt">著名示范性高中高考模拟试题和自主命制的高考真题密卷</p>
        </li>
        <li class="item">
            <img src="/static/dist/before/images/flow-img1.png" alt=""/>
            <a target="_blank" href="/before/mentality.jsp?classifyType=2&searchV=" class="name">高考心理</a>
            <span class="num">3</span>
            <p class="txt">著名示范性高中高考模拟试题和自主命制的高考真题密卷</p>
        </li>
        <li class="item">
            <img src="/static/dist/before/images/flow-img1.png" alt=""/>
            <a href="javascript:;" class="name" id="volunteer-flow">院校推荐</a>
            <span class="num">4</span>
            <p class="txt">根据分数快速定位院校及推荐院校</p>
        </li>
    </ul>
</div>
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
        <ul class="tabs-list mt20 subjectList" classifyType="1">
            <li id="">全部</li>
        </ul>
        <div class="into-search">
            <a href="/before/teacher-lecture.jsp?classifyType=1&searchV=" target="_blank" class="into-btn">进入名师讲堂</a>
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" onkeydown="if(event.keyCode==13)return false;" id="teacher-lecture-search-input" placeholder="请输入搜索内容"/>
                     <input type="button" id="teacher-lecture-search-btn" value="搜索"/>
                </span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <div class="tabs-content">
        <div class="tabs-content-list main-video-list-box">
            <ul class="main-video-list" id="teacher-lecture-main"></ul>
        </div>
    </div>
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
        <ul class="tabs-list mt20 subjectList" classifyType="3"><li id="">全部</li></ul>
        <div class="into-search">
            <a href="/before/exam.jsp?classifyType=3&searchV=" target="_blank" class="into-btn">真题密卷</a>
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" onkeydown="if(event.keyCode==13)return false;" id="exam-search-input" placeholder="请输入搜索内容"/>
                     <input type="button" id="exam-search-btn" value="搜索"/>
                </span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <div class="tabs-content">
        <div class="tabs-content-list">
            <ul class="main-exam-list main-exam-list-box" id="exam-main"></ul>
        </div>
    </div>
</div>
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
        <ul class="tabs-list mt20 subjectList" classifyType="2"><li id="">全部</li></ul>
        <div class="into-search">
            <a href="/before/mentality.jsp?classifyType=2&searchV=" target="_blank" class="into-btn">进入高考心理</a>
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" onkeydown="if(event.keyCode==13)return false;" id="mentality-search-input" placeholder="请输入搜索内容"/>
                     <input type="button" id="mentality-search-btn" value="搜索"/>
                </span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <div class="tabs-content">
        <div class="tabs-content-list main-video-list-box">
            <ul class="main-video-list " id="mentality-main"></ul>
        </div>
    </div>
</div>
<div id="main-volunteer-box"></div>
<div class="w1000">
    <div class="content-title">
        <p class="fl w40"></p>

        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>院校推荐</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
</div>
<div id="main-volunteer-box"></div>
<h6 class="w1000 ta content-title-sub">几次测试的分数出来了，这分数在去年可以报考什么学校，我能报我喜欢的学校吗？</h6>
<div class="w1000">
    <div class="tabs">
        <ul class="tabs-list mt20" id="main-volunteer-tabs">
            <li class="active">院校评测</li>
            <li>院校推荐</li>
        </ul>
    </div>
</div>


<div class="main-volunteer-box" id="dream-school">
    <div class="school-eval w1000">
        <div class="tip">
            <p>院校评测使用的分数，位次数据，招生计划为往年数据，结果仅供参考，且系统推荐学校有限制，若要使用最新，更全面，更多推荐学校，请升级为VIP。</p>
        </div>
        <div class="eval-left">
            <img src="/static/dist/before/images/bottom-banner.jpg" />
            <h3>为我推荐院校</h3>
            <h6>直接系统告诉我可以报的院校名称，容我考虑考虑</h6>
        </div>
        <div class="eval-right">
            <div class="error-tips hide"></div>
            <div class="form-control">
                <label>分数</label>
                <input type="text" class="score-input" id="dream-score-input" placeholder="请输入分数"/>
            </div>
            <div class="form-control">
                <label>院校</label>
                <input type="text" class="school-input" id="dream-school-input" placeholder="请填写理想院校"/>
            </div>
            <div class="category mt10" style="height: 40px;">
                <span class="t">选择文理科：</span>
                <div class="group-radio">
                    <label><input type="radio" name="dreamSubjectType" value="文史" />文史</label>
                    <label><input type="radio" name="dreamSubjectType" value="理工" />理工</label>
                </div>
            </div>
            <div class="indent mt10">
                <span class="ident-code">
                    <label>验证码</label>
                    <input type="text" id="yzmDream" placeholder="请输入分数"/>
                </span>
                <a>
                    <img id="yzmDreamSchool" src=""   />
                </a>
            </div>

            <div class="submit mt20">
                <input type="submit" id="evaluating-sub" value="提交测评"/>
            </div>

        </div>
    </div>
</div>

<div class="main-volunteer-box" style="display: none;">

    <div class="school-eval w1000">
        <div class="tip">
            <p>依据考分估算出我的位次、获得推荐院校信息、以及我的理想院校测评</p>
        </div>
        <div class="eval-left yztj-fl">
            <div class="img">
                <img src="/static/dist/before/images/zd-pic.png" alt=""/>
                <strong>院校推荐、志愿指导</strong>
                <p>依据总分、位次获取推荐院校及志愿指导</p>
            </div>
        </div>
        <div class="eval-right">
            <div class="error-tips hide"></div>
            <div class="form-control">
                <label>分数</label>
                <input type="text" class="score-input" id="score-input" placeholder="请输入分数"/>
            </div>

            <div class="category mt10">
                <span class="t">选择批次：</span>
                <div class="group-radio">
                    <label><input type="radio" name="batch" value="一批本科" />一批本科</label>
                    <label><input type="radio" name="batch" value="二批本科" />二批本科</label>
                    <label><input type="radio" name="batch" value="三批本科" />三批本科</label>
                    <label><input type="radio" name="batch" value="高职（专科）" />高职（专科）</label>
                </div>
            </div>
            <div class="category mt10">
                <span class="t">选择文理科：</span>
                <div class="group-radio">
                    <label><input type="radio" name="subjectType" value="文史" />文史</label>
                    <label><input type="radio" name="subjectType" value="理工" />理工</label>
                </div>
            </div>

            <div class="indent mt10">
                <span class="ident-code">
                    <label>验证码</label>
                    <input type="text" placeholder="请输入分数"/>
                </span>
                <a>
                    <img src="/static/dist/before/images/yzm.png" />
                </a>
            </div>

            <div class="submit mt20">
                <input type="submit" id="yxtj-sub" value="提交"/>
            </div>

        </div>
    </div>
</div>

<div class="tansLayer" style="display: none;"></div>
<div class="evaluating-layer" id="dream-school-layer" style="display: none;">
    <div class="top-close">
        <a href="javascript:;" class="close-btn">x</a>
    </div>

    <div class="evaluating-body">
        <div class="evaluating-result-tips">
            <p>您本次录入的分数是：<strong><span id="dreamScoreInfo"></span>分、<span id="dreamSubjectTypeInfo"></span>类</strong></p>
            <p>所测评的目标院校是：<strong id="dreamSchoolInfo"></strong></p>
        </div>
        <div class="evaluating-result">
            <p class="tips">依据2015年的录取情况、在陕西省填报该院校您需要以下分数(在陕）：</p>
            <div class="evaluating-result-num" id="dream-list">
                <%--<ul>--%>
                <%--<li class="pc">三批本科</li>--%>
                <%--<li class="result1">--%>
                <%--<span class="t">所需最低分数</span>--%>
                <%--<span class="num"><strong>639</strong>分</span>--%>
                <%--</li>--%>
                <%--<li class="result2">--%>
                <%--<span class="t">所需平均分数</span>--%>
                <%--<span class="num"><strong>652</strong>分</span>--%>
                <%--</li>--%>
                <%--</ul>--%>
            </div>
        </div>
    </div>
</div>



<div class="volunteer-flow3-layer" id="volunteer-flow3-layer" style="display: none;">
    <div class="top-close">
        <a href="javascript:;" class="close-btn">x</a>
    </div>

    <div class="tips">
        <strong>温馨提示：</strong>
        <p>本页推荐结果、依据您的分数（模考成绩）进行评估获得。如果您想获得更为精确的志愿指导，请使用我们为您提供的志愿指导服务。</p>
    </div>
    <div class="volunteer-flow3-body">
        <div class="info-explain">
            <strong>录取指数说明</strong>
            <ul>
                <li>
                    <span class="starA-icon"></span>可以冲一冲的院校；
                </li>
                <li>
                    <span class="starB-icon"></span>可以稳一稳的院校；
                </li>
                <li>
                    <span class="starC-icon"></span>可以保一保的院校；
                </li>
                <li>
                    <span class="starD-icon"></span>可以垫一垫的院校；
                </li>
            </ul>
        </div>
        <div class="info-result">
            <ul>
                <li class="starA">
                    <div class="title">
                        <span class="">A档（冲）</span>
                        <p>录取指数: <i class="starA-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school0">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真是填报时、可以参开选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list0"></div>
                </li>
                <li class="starB">
                    <div class="title">
                        <span>B档（稳）</span>
                        <p>录取指数: <i class="starB-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school1">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真是填报时、可以参开选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list1"></div>
                </li>
                <li class="starC">
                    <div class="title">
                        <span>C档（保）</span>
                        <p>录取指数: <i class="starC-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school2">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真是填报时、可以参开选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list2"></div>
                </li>
                <li class="starD">
                    <div class="title">
                        <span>D档（垫）</span>
                        <p>录取指数: <i class="starD-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school3">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真是填报时、可以参开选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list3"></div>
                </li>
            </ul>
        </div>
        <div class="result-info">
            <ul>
                <li>您录入的分数是<strong id="score-num">666分</strong>、<strong id="subjectTypeV">文史类</strong>，依据2015年招生情况，您的分数是<strong id="batchV">一批本科</strong>；</li>
                <li>如果您想选择<strong>其他批次院校</strong>，请返回重新选择，或根据本人志愿进行填报；</li>
                <li>如果未能推荐出您的意向院校，请使用我们为您提供的<span>理想院校测评</span>服务.</li>
            </ul>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/before/scripts/before");
</script>
</body>
</html>
