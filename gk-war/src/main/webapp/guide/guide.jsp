<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>政策解读</title>
    <link rel="stylesheet" href="/static/dist/guide/styles/guide.css" />
</head>
<body>
<div class="header">
    <div class="w1000">
        <a href="">
            <img src="/static/dist/common/images/logo-min.png" alt="高考360" class="logo fl"/>
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
                <img src="/static/dist/common/images/avatar.png" alt="avatar" class="user-avatar"/>
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
                <div class="swiper-slide"><img src="/static/dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="/static/dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="/static/dist/before/images/banner1.jpg" alt=""/></div>
            </div>
            <div class="swiper-pagination"></div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-button-next"></div>
        </div>
    </div>
    <div class="w1000">
        <div class="content-title mt60">
            <p class="fl w40"></p>
            <p class="fl w20">
                <i class="fl arraw"></i>
                <span>政策解读</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">专业而通俗地解读陕西省高考招生文件、高考志愿填报和招生的相关政策。</h6>
        <p class="sub-article c888 mt60">
            陕西省招办每年公布的《陕西省普通高等院校招生工作实施办法》，是陕西省高考招生的纲领性文件，家长及考生都应深入了解。2015年陕西省招生政策会陆续公布，请随时关注本平台各批次招生政策，在此我们会对此“办法”进行专业通俗的解读。以便于家长和考生正确把握。
        </p>
        <ul class="tabs-list mt60">
            <li class="active">高考前录取</li>
            <li>提前批</li>
            <li>一批本科</li>
            <li>二批本科</li>
            <li>三批本科</li>
            <li>高职专科</li>
        </ul>

        <ul class="tabs-sub-list mt20">
            <li>保送生</li>
            <li>高职分类招生</li>
            <li>体育单招</li>
        </ul>

        <div class="tab-content">
            <section class="section-article">
                <h6>解读</h6>
                <p><img src="images/jd.jpg" /> </p>
                <p>陕西省招办每年公布的《陕西省普通高等院校招生工作实施办法》，是陕西省高考招生的纲领性文件，家长及考生都应深入了解。2015年陕西省招生政策会陆续公布，请随时关注本平台各批次招生政策，在此我们会对此“办法”进行专业通俗的解读。以便于家长和考生正确把握。陕西省招办每年公布的《陕西省普通高等院校招生工作实施办法》，是陕西省高考招生的纲领性文件.</p>
            </section>
            <section class="section-article">
                <h6>政策文件</h6>
                <p><img src="images/zcwj.jpg" /></p>
                <p>陕西省招办每年公布的《陕西省普通高等院校招生工作实施办法》，是陕西省高考招生的纲领性文件，家长及考生都应深入了解。2015年陕西省招生政策会陆续公布，请随时关注本平台各批次招生政策，在此我们会对此“办法”进行专业通俗的解读。以便于家长和考生正确把握。陕西省招办每年公布的《陕西省普通高等院校招生工作实施办法》，是陕西省高考招生的纲领性文件.</p>
            </section>
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
            <img src="/static/dist/common/images/logo.png" alt="logo" class="logo"/>
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
