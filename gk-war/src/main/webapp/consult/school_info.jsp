<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>院校信息</title>
    <link rel="stylesheet" href="../../dist/consult/styles/school_info.css" />
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
        <div class="content-title mt60">
            <p class="fl w40"></p>
            <p class="fl w20">
                <i class="fl arraw"></i>
                <span>院校信息</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">及时、准确发布在陕招生的相关院校、专业及招生计划信息，方便您查询、筛选、了解。</h6>

        <div class="search-content ta mt60">
            <span>
                 <input type="text" placeholder="请输入专业名称进行搜索"/>
                 <input type="button" value="搜索"/>
            </span>
        </div>

        <ul class="select-option">
            <li class="lable">所在省份：</li>
            <li class="options">
                <a class="active">全部</a>
                <a>北京</a>
                <a>天津</a>
                <a>河北</a>
                <a>山西</a>
                <a>内蒙古</a>
                <a>辽宁</a>
                <a>吉林</a>
                <a>黑龙江</a>
                <a>上海</a>
                <a>江苏</a>
                <a>浙江</a>
                <a>安徽</a>
                <a>福建</a>
                <a>江西</a>
                <a>山东</a>
                <a>河南</a>
                <a>湖北</a>
                <a>湖南</a>
                <a>广东</a>
                <a>广西</a>
                <a>海南</a>
                <a>重庆</a>
                <a>四川</a>
                <a>贵州</a>
                <a>云南</a>
                <a>西藏</a>
                <a>陕西</a>
                <a>甘肃</a>
                <a>青海</a>
                <a>宁夏</a>
                <a>新疆</a>
            </li>
        </ul>
        <ul class="select-option">
            <li class="lable">院校类型：</li>
            <li class="options">
                <a class="active">全部</a>
                <a>综合</a>
                <a>工科</a>
                <a>农业</a>
                <a>林业</a>
                <a>医药</a>
                <a>师范</a>
                <a>语言</a>
                <a>财经</a>
            </li>
        </ul>
        <ul class="select-option">
            <li class="lable">院校批次：</li>
            <li class="options">
                <a class="active">全部</a>
                <a>一批本科</a>
                <a>二批本科</a>
                <a>三批本科</a>
                <a>高职（专科）</a>
            </li>
        </ul>
        <ul class="select-option">
            <li class="lable">院校特征：</li>
            <li class="options">
                <a class="active">全部</a>
                <a>985</a>
                <a>211</a>
                <a>研究所 </a>
            </li>
        </ul>

        <div class="school-table mt20">
            <table border="0" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th class="name">院校名称</th>
                        <th>所在地区</th>
                        <th>院校类型</th>
                        <th>院校隶属</th>
                        <th>院校特征</th>
                        <th>院校信息</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="name">陕西电子科技大学</td>
                        <td>北京</td>
                        <td>综合</td>
                        <td>教育部直属</td>
                        <td>985、211、研</td>
                        <td>
                            <a>查看详情</a>
                        </td>
                    </tr>
                    <tr class="active">
                        <td class="name">陕西电子科技大学</td>
                        <td>北京</td>
                        <td>综合</td>
                        <td>教育部直属</td>
                        <td>985、211、研</td>
                        <td>
                            <a>查看详情</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="name">陕西电子科技大学</td>
                        <td>北京</td>
                        <td>综合</td>
                        <td>教育部直属</td>
                        <td>985、211、研</td>
                        <td>
                            <a>查看详情</a>
                        </td>
                    </tr>
                </tbody>
            </table>
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

