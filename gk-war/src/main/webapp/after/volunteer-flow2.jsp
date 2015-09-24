<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>志愿指导</title>
    <link rel="stylesheet" href="../../dist/common/styles/comm.css"/>
    <link rel="stylesheet" href="../../dist/after/styles/after.css"/>
</head>
<body>
<div class="header">
    <div class="w1000">
        <a href="">
            <img src="../../dist/common/images/logo-min.png" alt="高考360" class="logo fl"/>
        </a>
        <ul class="main-menu fl">
            <li><a href="">首页</a></li>
            <li><a href="">报考指南</a></li>
            <li><a href="">报考咨询</a></li>
            <li><a href="">考前备考</a></li>
            <li><a href="">考后报考</a></li>
        </ul>
        <div class="user-info-list fr">
            <div class="user">
                <img src="../../dist/common/images/avatar.png" alt="avatar" class="user-avatar"/>
                <a href="">韩小寒</a>
            </div>
            <ul class="menu hide">
                <li><a href="">个人信息</a></li>
                <li><a href="">修改密码</a></li>
                <li><a href="">退出</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="content">
    <div class="wipe">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide" style="background: #da4339"><img src="../../dist/after/images/banner1.png" alt=""/></div>
                <div class="swiper-slide"><img src="../../dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="../../dist/before/images/banner1.jpg" alt=""/></div>
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
            <img src="images/zd-pic.png" alt=""/>
            <strong>院校推荐、志愿指导</strong>
            <p>依据总分、位次获取推荐院校及志愿指导</p>
        </div>
    </div>
    <div class="fr">
        <div class="volunteer-flow2">
            <div class="info1">
                您的分数是<strong>450分</strong>、<strong>理工类</strong>； <br/>
                依据2015年批次控制线，您的分数达到<strong>二批本科</strong>；<br/>
                请选择一下批次，进入院校推荐。
            </div>
            <div class="info2">
                <h3>普通第二批本科院校</h3>
                2015年控制线：<strog>文科 467分</strog>，<strong>理科440分</strong>
            </div>
            <div class="form-control-btn">
                <input type="button" class="btn" value="开始"/>
                <input type="button" class="prev-btn" value="返回上一步"/>
            </div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="bottom-nav">
        <div class="w1000">
            <ul>
                <li><a href="" class="nav-title">关于高考360</a></li>
                <li><a href="">联系我们</a></li>
                <li><a href="">使用协议</a></li>
            </ul>
            <ul>
                <li><a href="" class="nav-title">帮助中心</a></li>
                <li><a href="">常见问题</a></li>
                <li><a href="">意见反馈</a></li>
            </ul>
            <ul>
                <li><a href="" class="nav-title">关注我们</a></li>
                <li><a href="">官方微信</a></li>
                <li><a href="">新浪微博</a></li>
            </ul>
            <img src="../../dist/common/images/logo.png" alt="logo" class="logo"/>
        </div>
    </div>
    <div class="copy-right">
        <p class="w1000">联系信息:E-mail:cewfefe@163.com QQ:5454555 备案号:陕ICP备343454135-1号 [站长统计]</p>
    </div>
</div>
<link rel="stylesheet" href="../../bower_components/swiper/dist/css/swiper.css"/>
<script src="../../bower_components/jquery/dist/jquery.min.js"></script>
<script src="../../bower_components/swiper/dist/js/swiper.jquery.min.js"></script>
<script>
    $(function () {
        //幻灯片
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            nextButton: '.swiper-button-next',
            prevButton: '.swiper-button-prev',
            loop: true
        });
    });
</script>
</body>
</html>
