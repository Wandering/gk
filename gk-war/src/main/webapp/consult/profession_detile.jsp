<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>院校信息</title>
    <link rel="stylesheet" href="/static/dist/consult/styles/profession_detile.css" />
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
        <div class="basic-info">
            <h1>专业基本信息</h1>
            <div class="info-content">
                <img class="fl" src="images/bjdx.jpg" />
                <div class="info">
                    <ul>
                        <li class="school-name">北京大学</li>
                        <li>所在省份：北京</li>
                        <li>院校隶属：教育部直属</li>
                        <li>学历层次：本科</li>
                        <li>院校特征：985 211 研</li>
                        <li>院校类型：综合</li>
                        <li>院校网址：http://www.pku.edu.cn</li>
                        <li>院校地址：北京市海淀区颐和园路5号</li>
                        <li>联系电话：<span>010-62751407</span></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="other-info">
            <h1>往年招生情况</h1>
            <ul class="tabs-list mt20">
                <li class="active">相近专业</li>
                <li>主要课程</li>
                <li>就业方向</li>
                <li>开设院校</li>
            </ul>

            <div class="tabs-content">
                <ul>
                    <li>国际经济与贸易专业</li>
                    <li>测控技术与仪器专业</li>
                    <li>国际经济与贸易专业</li>
                    <li>测控技术与仪器专业</li>
                    <li>国际经济与贸易专业</li>
                    <li>测控技术与仪器专业</li>
                    <li>国际经济与贸易专业</li>
                    <li>测控技术与仪器专业</li>
                    <li>国际经济与贸易专业</li>
                    <li>测控技术与仪器专业</li>
                    <li>国际经济与贸易专业</li>
                    <li>测控技术与仪器专业</li>
                </ul>
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

