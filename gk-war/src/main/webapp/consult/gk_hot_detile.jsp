<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>高考热点详情</title>
    <link rel="stylesheet" href="/static/dist/consult/styles/gk_hot_detile.css" />
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

        <div class="search-content ta mt20">
                 <span>
                     <input type="text" placeholder="请输入专业名称进行搜索"/>
                     <input type="button" value="搜索"/>
                     <button>我要提问</button>
                </span>
        </div>

        <div class="main-body">
            <section class="section-article">
                <h1>帮你走出平行志愿的认识误区，圆一个大学梦</h1>
                <h6>2015-05-15 09:52</h6>
                <article>
                    <p>2010年至今，陕西省普通高校招生过程中， 一直采取“ 平行志愿 ”的投档模式虽然官方媒体
                        每年在做平行志愿的宣传和解读 ，但从每年的录取情况来看 ， 一批本科 、二批本科 、三批本科 、
                        高职批次都会有一部考生因志愿填报不合理而落选 。究其原因一是受高校招生计划数的限制必定会
                        有部分“ 压线  ” 考生（ 即批次最低控制分数线附近的考生 ）落选 ， 二是部分考生对平行志的认
                        识存在误区而导致志愿填报不合理造成的 。因此走出平行志愿的识误区，科学  、合理 、理性填报
                        高考志愿、从而实现考生的大学梦，就显得尤为重要 。考生对平行志愿的认识误区主要有以下几个
                        方面：</p>
                    <img src="images/bjdx.jpg" />
                    <p>2010年至今，陕西省普通高校招生过程中， 一直采取“ 平行志愿 ”的投档模式虽然官方媒体
                        每年在做平行志愿的宣传和解读 ，但从每年的录取情况来看 ， 一批本科 、二批本科 、三批本科 、
                        高职批次都会有一部考生因志愿填报不合理而落选 。究其原因一是受高校招生计划数的限制必定会
                        有部分“ 压线  ” 考生（ 即批次最低控制分数线附近的考生 ）落选 ， 二是部分考生对平行志的认
                        识存在误区而导致志愿填报不合理造成的 。因此走出平行志愿的识误区，科学  、合理 、理性填报
                        高考志愿、从而实现考生的大学梦，就显得尤为重要 。考生对平行志愿的认识误区主要有以下几个
                        方面：</p>
                </article>
            </section>

            <section class="ask-list">
                <h3>高考热点</h3>
                <ul>
                    <li>综合评价生是统招生吗？</li>
                    <li>综合评价生是统招生吗？</li>
                    <li>综合评价生是统招生吗？</li>
                </ul>
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


