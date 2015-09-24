<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>专业测评</title>
    <link rel="stylesheet" href="/static/dist/consult/styles/consult.css" />
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
                <span>专业测评</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">最大限度地帮助学生深刻地认识自我，定位我的职业，锁定我的专业</h6>


        <section class="section-article">
            <div class="search-content">
                 <span>
                     <input type="text" placeholder="请输入专业名称进行搜索"/>
                     <input type="button" value="搜索"/>
                </span>
            </div>
            <div class="video">
                <video id="home_video" controls="" preload="none" poster="/img/poster.jpg" class="video-js vjs-default-skin">
                    <source src="http://vjs.zencdn.net/v/oceans.mp4" type="video/mp4">
                    <source src="http://vjs.zencdn.net/v/oceans.webm" type="video/webm">
                    <track kind="captions" src="/vtt/captions.vtt" srclang="en" label="English">
                    </track>
                </video>
            </div>
            <p>专业升学测评是一项专门为中国在校中学生升学择业而设计的专业测评软件，在中学生进行专业抉择的关键时刻给予专业的指导及合理的建议。</p>
            <div class="into-evalution ta">
                <input type="button" value="进入测评" />
            </div>
        </section>


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

