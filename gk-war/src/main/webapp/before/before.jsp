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
    <div class="wipe">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><img src="/static/dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="/static/dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="/static/dist/before/images/banner1.jpg" alt=""/></div>
            </div>
            <div class="swiper-pagination"></div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-button-next"></div>
        </div>
    </div>

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
            <a target="_blank" href="/before/teacher-lecture.jsp?classifyType=1" class="name">名师讲堂</a>
            <span class="num">1</span>
            <p class="txt">名师精品课程，全面剖析高考知识点以及提分技巧。</p>
        </li>
        <li class="item">
            <img src="/static/dist/before/images/flow-img1.png" alt=""/>
            <a target="_blank" href="/before/exam.jsp?classifyType=3" class="name">真题密卷</a>
            <span class="num">2</span>
            <p class="txt">著名示范性高中高考模拟试题和自主命制的高考真题密卷</p>
        </li>
        <li class="item">
            <img src="/static/dist/before/images/flow-img1.png" alt=""/>
            <a target="_blank" href="/before/mentality.jsp?classifyType=2" class="name">高考心理</a>
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
        <ul class="tabs-list mt20 subjectList" classifyType="1"></ul>
        <div class="into-search">
            <a href="/before/teacher-lecture.jsp?classifyType=1" target="_blank" class="into-btn">进入名师讲堂</a>
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" id="teacher-lecture-search-input" placeholder="请输入搜索内容"/>
                     <input type="button" id="teacher-lecture-search-btn" value="搜索"/>
                </span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <div class="tabs-content">
        <div class="tabs-content-list main-video-list-box">
            <ul class="main-video-list" id="teacher-lecture-main">

            </ul>
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
        <ul class="tabs-list mt20 subjectList" classifyType="3"></ul>
        <div class="into-search">
            <a href="/before/exam.jsp?classifyType=3" target="_blank" class="into-btn">真题密卷</a>
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" id="exam-search-input" placeholder="请输入搜索内容"/>
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
        <ul class="tabs-list mt20 subjectList" classifyType="2"></ul>
        <div class="into-search">
            <a href="/before/mentality.jsp?classifyType=2" target="_blank" class="into-btn">进入高考心理</a>
            <form action="">
                <section class="section-article">
                    <div class="search-content ta">
                 <span>
                     <input type="text" id="mentality-search-input" placeholder="请输入搜索内容"/>
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


<div class="main-volunteer-box">
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
            <div class="form-control">
                <label>分数</label>
                <input type="text" placeholder="请输入分数"/>
            </div>
            <div class="form-control">
                <label>院校</label>
                <input type="text" placeholder="请填写理想院校"/>
            </div>
            <div class="category mt10">
                <label>选择文理科：</label>
                <ul class="group-radio">
                    <li>
                        <span><input type="radio" name="category" value="1" />文科</span>
                        <span><input type="radio" name="category" value="3" />理科</span>
                    </li>
                </ul>
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
                <input type="submit" id="evaluating-sub" value="提交测评"/>
            </div>

        </div>
    </div>
</div>




<%--<div class="main-volunteer-box">--%>
    <%--<div class="school-eval w1000">--%>
        <%--<div class="tip">--%>
            <%--<p>院校评测使用的分数，位次数据，招生计划为往年数据，结果仅供参考，且系统推荐学校有限制，若要使用最新，更全面，更多推荐学校，请升级为VIP。</p>--%>
        <%--</div>--%>
        <%--<div class="eval-left">--%>
            <%--<img src="/static/dist/before/images/bottom-banner.jpg" />--%>
            <%--<h3>为我推荐院校</h3>--%>
            <%--<h6>直接系统告诉我可以报的院校名称，容我考虑考虑</h6>--%>
        <%--</div>--%>
        <%--<div class="eval-right">--%>
            <%--<div class="score">--%>
                <%--<label>分数</label>--%>
                <%--<input type="text" placeholder="请输入分数"/>--%>
            <%--</div>--%>

            <%--<div class="category mt10">--%>
                <%--<label>选择文理科：</label>--%>
                <%--<ul class="group-radio">--%>
                    <%--<li>--%>
                        <%--<span><input type="radio" name="category" value="1" />一批本科</span>--%>
                        <%--<span><input type="radio" name="category" value="3" />三批本科</span>--%>
                    <%--</li>--%>
                    <%--<li class="tar">--%>
                        <%--<span><input type="radio" name="category" value="2" />二批本科</span>--%>
                        <%--<span><input type="radio" name="category" value="4" />高职(专科)</span>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</div>--%>

            <%--<div class="indent mt10">--%>
                <%--<span class="ident-code">--%>
                    <%--<label>验证码</label>--%>
                    <%--<input type="text" placeholder="请输入分数"/>--%>
                <%--</span>--%>
                <%--<a>--%>
                    <%--<img src="/static/dist/before/images/bottom-banner.jpg" />--%>
                <%--</a>--%>
            <%--</div>--%>

            <%--<div class="submit mt20">--%>
                <%--<input type="submit" id="evaluating-sub" value="提交测评"/>--%>
            <%--</div>--%>

        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>





<div class="main-volunteer-box" style="display: none;">
    <div class="w1000 main-volunteer">
        <div class="fl">
            <div class="img">
                <img src="/static/dist/before/images/zd-pic.png" alt=""/>
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
                    <img src="/static/dist/before/images/yzm.png" alt=""/>
                </div>
                <div class="form-control-btn">
                    <input type="button" class="btn" value="下一步"/>
                </div>

            </form>
        </div>
    </div>
</div>






<div class="tansLayer" style="display: none;"></div>
<div class="volunteer-flow3-layer" style="display: none;">
    <div class="top-close">
        <a href="" class="close-btn">x</a>
    </div>
    <div class="tips">
        <strong>温馨提示：</strong>
        <p>依据平行志愿规则，系统推荐遵循分数最大化，为您推荐一下A、B、C、D四挡院校；考生可根据个性化需求调整院校顺位；</p>
    </div>
    <div class="volunteer-flow3-body">

    </div>

</div>



<div class="evaluating-layer" style="display: none;">
    <div class="top-close">
        <a href="javascript:;" class="close-btn">x</a>
    </div>

    <div class="evaluating-body">
        <div class="evaluating-result-tips">
            <p>您本次录入的分数是：<strong>600分、理工类</strong></p>
            <p>所测评的目标院校是：<strong>西安交通大学</strong></p>
        </div>
        <div class="evaluating-result">
            <p class="tips">依据2015年的录取情况、在陕西省填报该院校您需要以下分数(在陕一批本科）：</p>
            <div class="evaluating-result-num">
                <ul>
                    <li class="result1">
                        <span class="t">所需最低分数</span>
                        <span class="num"><strong>639</strong>分</span>
                    </li>
                    <li class="result2">
                        <span class="t">所需平均分数</span>
                        <span class="num"><strong>652</strong>分</span>
                    </li>
                </ul>
            </div>
        </div>
        <a href="javascript:;" class="evaluating-result-close close-btn">返回上一步</a>
    </div>

</div>















<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/before/scripts/before");
</script>
</body>
</html>
