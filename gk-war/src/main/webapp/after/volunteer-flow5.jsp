<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>志愿指导</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/common/styles/comm.css"/>
    <link rel="stylesheet" href="/static/dist/after/styles/after.css"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
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

<%@ include file="/common/footer.jsp"%>




<link rel="stylesheet" href="/static/bower_components/swiper/dist/css/swiper.css"/>
<script src="/static/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/static/bower_components/swiper/dist/js/swiper.jquery.min.js"></script>
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
