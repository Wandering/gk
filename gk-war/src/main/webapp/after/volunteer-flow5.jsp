<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>志愿指导</title>
    <link rel="stylesheet" href="/static/dist/common/styles/comm.css"/>
    <link rel="stylesheet" href="/static/dist/after/styles/after.css"/>
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
                <div class="swiper-slide" style="background: #da4339"><img src="/static/dist/after/images/banner1.png"
                                                                           alt=""/></div>
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
            <span>志愿指导</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
</div>
<h6 class="w1000 ta content-title-sub">独创分差位次修正算法，为您科学推荐报考院校。原来填报志愿可以如此简单！</h6>

<div class="w1000 main-volunteer">
    <div class="volunteer-flow3">
        <table class="volunteer-flow3-table">
            <thead>
            <tr>
                <th></th>
                <th>院校</th>
                <th>专业</th>
                <th>是否服从其他专业</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td class="item1">A志愿</td>
                <td class="item2">
                    <div class="">
                        <section class="section-article">
                            <div class="search-content ta">
                                 <span>
                                     <input type="text" value="华东理工大学" placeholder="请输入院校名称"/>
                                     <input type="button" value="选择院校"/>
                                </span>
                            </div>
                        </section>
                        <div class="tips" style="display: none;">
                            <strong>温馨提示：</strong>
                            <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                        </div>
                        <div class="result-info-details">
                            <p>
                            院校代码：4004  <br/>
                            院校特征：211 研<br/>
                            院校隶属：教育部直属   <br/>
                            院校类型：工科             <br/>
                            2014年最低投档分：592           <br/>
                            2014年最低位次：8959                 <br/>
                            2014年录取平均分：602                     <br/>
                            2014年平均分位次：7006                         <br/>
                            历年招生情况：实际招生超过计划招生数                    <br/>
                            录取指数：★★
                            </p>
                        </div>
                    </div>
                </td>
                <td class="item3">
                    <ul class="specialty">
                        <li>
                            <span class="num">1</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">2</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">3</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">4</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="">选择专业</a></span>
                        </li>
                    </ul>
                </td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" name="isFun" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun" id=""/> 否
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="item1">B志愿</td>
                <td class="item2">
                    <div class="">
                        <section class="section-article">
                            <div class="search-content ta">
                                 <span>
                                     <input type="text" placeholder="请输入院校名称"/>
                                     <input type="button" value="选择院校"/>
                                </span>
                            </div>
                        </section>
                        <div class="tips">
                            <strong>温馨提示：</strong>
                            <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                        </div>
                    </div>
                </td>
                <td class="item3"></td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" name="isFun" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun" id=""/> 否
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="item1">C志愿</td>
                <td class="item2">
                    <div class="">
                        <section class="section-article">
                            <div class="search-content ta">
                                 <span>
                                     <input type="text" placeholder="请输入院校名称"/>
                                     <input type="button" value="选择院校"/>
                                </span>
                            </div>
                        </section>
                        <div class="tips">
                            <strong>温馨提示：</strong>
                            <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                        </div>
                    </div>
                </td>
                <td class="item3"></td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" name="isFun" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun" id=""/> 否
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="item1">D志愿</td>
                <td class="item2">
                    <div class="">
                        <section class="section-article">
                            <div class="search-content ta">
                                 <span>
                                     <input type="text" placeholder="请输入院校名称"/>
                                     <input type="button" value="选择院校"/>
                                </span>
                            </div>
                        </section>
                        <div class="tips">
                            <strong>温馨提示：</strong>
                            <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                        </div>
                    </div>
                </td>
                <td class="item3"></td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" name="isFun" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun" id=""/> 否
                        </label>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
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
