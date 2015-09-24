<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>提问页面 </title>
    <link rel="stylesheet" href="../../dist/question/styles/ask.css" />
    <link rel="stylesheet" href="../../bower_components/kindeditor/themes/default/default.css" />
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
                <div class="swiper-slide"><img src="../../dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="../../dist/before/images/banner1.jpg" alt=""/></div>
                <div class="swiper-slide"><img src="../../dist/before/images/banner1.jpg" alt=""/></div>
            </div>
            <div class="swiper-pagination"></div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-button-next"></div>
        </div>
    </div>
    <div class="w1000">

        <section class="section-article">
            <div class="search-content ta">
                 <span>
                     <input type="text" placeholder="请输入专业名称进行搜索"/>
                     <input type="button" value="搜索"/>
                     <button>我要提问</button>
                </span>
            </div>
        </section>

        <h1 class="search-result-num">提问</h1>
        <div class="ask-title">
            <input type="text" placeholder="请填写标题">
            <div class="mt20">
                <textarea name="content"></textarea>
            </div>
            <button>提交</button>
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

<div class="custom-model">
    <div class="custom-content">
        <div class="custom-head"></div>
        <div class="custom-body"></div>
        <div class="custom-footer ta">
            <button>提交</button>
        </div>
    </div>
</div>
<link rel="stylesheet" href="../../bower_components/swiper/dist/css/swiper.css"/>
<script src="../../bower_components/jquery/dist/jquery.min.js"></script>
<script src="../../bower_components/swiper/dist/js/swiper.jquery.min.js"></script>
<script src="../../bower_components/kindeditor/kindeditor-min.js"></script>
<script src="../../bower_components/kindeditor/lang/zh-CN.js"></script>
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

        //编辑框
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="content"]', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                allowImageUpload : false,
                items : [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', '|', 'emoticons', 'image', 'link']
            });
        });
    });
</script>
</body>
</html>
