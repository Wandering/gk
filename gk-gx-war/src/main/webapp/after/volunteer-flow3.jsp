<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
   <title>志愿指导</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/after/styles/after.min.css"/>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/after/styles/expert-evaluating.min.css"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div id="main1">
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
        <div class="error-tips hide"></div>
        <div class="volunteer-flow3">
            <style>
                .volunteer-flow3-table .txt{
                    text-align: center;
                    height: 35px;
                    line-height: 35px;
                }
                .volunteer-flow3-table .specialty-list{
                    width: 420px;
                    margin: 0 auto;
                    padding: 5px 0 15px;
                }
            </style>
            <table class="volunteer-flow3-table">
                <thead>
                <tr>
                    <th>A志愿</th>
                    <th>B志愿</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="item2">
                        <div class="">
                            <section class="section-article">
                                <div class="search-content ta">
                                     <span>
                                         <input type="text" dataType="1" class="open-flow3" placeholder="请输入院校名称"/>
                                         <input type="button" dataType="1"  class="open-flow3" value="选择院校"/>
                                    </span>
                                </div>
                            </section>
                            <div class="tips" id="tips1">
                                <strong>温馨提示：</strong>
                                <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                            </div>
                            <div class="result-info-details" id="result-info1"></div>
                        </div>
                    </td>
                    <td class="item2">
                        <div class="">
                            <section class="section-article">
                                <div class="search-content ta">
                                     <span>
                                         <input type="text" dataType="1" class="open-flow3" placeholder="请输入院校名称"/>
                                         <input type="button" dataType="1"  class="open-flow3" value="选择院校"/>
                                    </span>
                                </div>
                            </section>
                            <div class="tips" id="tips2">
                                <strong>温馨提示：</strong>
                                <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                            </div>
                            <div class="result-info-details" id="result-info2"></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="txt">专业选择</td>
                    <td class="txt">专业选择</td>
                </tr>
                <tr>
                    <td class="item3">
                        <div class="specialty-list">
                            <ul class="specialty " id="specialty1">
                                <li>
                                    <span class="num">1.</span>
                                    <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                    <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                                </li>
                                <li>
                                    <span class="num">2.</span>
                                    <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                    <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                                </li>
                                <li>
                                    <span class="num">3.</span>
                                    <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                    <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                                </li>
                                <li>
                                    <span class="num">4.</span>
                                    <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                    <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                                </li>
                                <li>
                                    <span class="num">5.</span>
                                    <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                    <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                                </li>
                                <li>
                                    <span class="num">6.</span>
                                    <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                    <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                                </li>
                            </ul>
                        </div>
                    </td>
                    <td class="item3">
                        <ul class="specialty hide" id="specialty2">
                            <li>
                                <span class="num">1.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">2.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">3.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">4.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">5.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">6.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td class="item3">
                        <div class="">
                            <label><input type="radio" checked name="isFun1" id=""/>全部服从专业调剂</label>
                            <label><input type="radio" name="isFun1" id=""/>全部不服从专业调剂</label>
                            <label><input type="radio" name="isFun1" id=""/>部分服从专业调剂</label>
                        </div>
                    </td>
                    <td class="item3">
                        <div class="">
                            <label><input type="radio" checked name="isFun2" id=""/>全部服从专业调剂</label>
                            <label><input type="radio" name="isFun2" id=""/>全部不服从专业调剂</label>
                            <label><input type="radio" name="isFun2" id=""/>部分服从专业调剂</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="item3">
                        <div class="">
                            <label><input type="radio" checked name="isFun1" id=""/>全部服从专业调剂</label>
                            <label><input type="radio" name="isFun1" id=""/>全部不服从专业调剂</label>
                            <label><input type="radio" name="isFun1" id=""/>部分服从专业调剂</label>
                        </div>
                    </td>
                    <td class="item3">
                        <div class="">
                            <label><input type="radio" checked name="isFun2" id=""/>全部服从专业调剂</label>
                            <label><input type="radio" name="isFun2" id=""/>全部不服从专业调剂</label>
                            <label><input type="radio" name="isFun2" id=""/>部分服从专业调剂</label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>















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
                                         <input type="text" dataType="1" class="open-flow3" placeholder="请输入院校名称"/>
                                         <input type="button" dataType="1"  class="open-flow3" value="选择院校"/>
                                    </span>
                                </div>
                            </section>
                            <div class="tips" id="tips1">
                                <strong>温馨提示：</strong>
                                <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                            </div>
                            <div class="result-info-details" id="result-info1"></div>
                        </div>
                    </td>
                    <td class="item3">
                        <ul class="specialty hide" id="specialty1">
                            <li>
                                <span class="num">1.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">2.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">3.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">4.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">5.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">6.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                        </ul>
                    </td>
                    <td class="item4">
                        <div class="">
                            <label>
                                <input type="radio" checked name="isFun1" value="是" id=""/> 是
                            </label>
                            <p>（建议服从）</p>
                            <label>
                                <input type="radio" name="isFun1" value="否" id=""/> 否
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
                                         <input type="text" dataType="2" class="open-flow3" placeholder="请输入院校名称"/>
                                         <input type="button" dataType="2"  class="open-flow3" value="选择院校"/>
                                    </span>
                                </div>
                            </section>
                            <div class="tips"  id="tips2">
                                <strong>温馨提示：</strong>
                                <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                            </div>
                            <div class="result-info-details" id="result-info2"></div>
                        </div>
                    </td>
                    <td class="item3">
                        <ul class="specialty hide" id="specialty2">
                            <li>
                                <span class="num">1.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">2.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">3.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">4.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">5.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">6.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                        </ul>
                    </td>
                    <td class="item4">
                        <div class="">
                            <label>
                                <input type="radio" checked name="isFun2" value="是" id=""/> 是
                            </label>
                            <p>（建议服从）</p>
                            <label>
                                <input type="radio" name="isFun2" value="否" id=""/> 否
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
                                         <input type="text" dataType="3"  class="open-flow3" placeholder="请输入院校名称"/>
                                         <input type="button" dataType="3"  class="open-flow3" value="选择院校"/>
                                    </span>
                                </div>
                            </section>
                            <div class="tips"  id="tips3">
                                <strong>温馨提示：</strong>
                                <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                            </div>
                            <div class="result-info-details" id="result-info3"></div>
                        </div>
                    </td>
                    <td class="item3">
                        <ul class="specialty hide" id="specialty3">
                            <li>
                                <span class="num">1.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">2.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">3.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">4.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">5.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">6.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                        </ul>
                    </td>
                    <td class="item4">
                        <div class="">
                            <label>
                                <input type="radio" checked name="isFun3" value="是" id=""/> 是
                            </label>
                            <p>（建议服从）</p>
                            <label>
                                <input type="radio" name="isFun3" value="否" id=""/> 否
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
                                         <input type="text" dataType="4"  class="open-flow3" placeholder="请输入院校名称"/>
                                         <input type="button" dataType="4"  class="open-flow3" value="选择院校"/>
                                    </span>
                                </div>
                            </section>
                            <div class="tips"  id="tips4">
                                <strong>温馨提示：</strong>
                                <p>请准确无误的填写考号、分数及位次；所录入的信息一旦提交将不可更改。</p>
                            </div>
                            <div class="result-info-details" id="result-info4"></div>
                        </div>
                    </td>
                    <td class="item3">
                        <ul class="specialty hide" id="specialty4">
                            <li>
                                <span class="num">1.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">2.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">3.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">4.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">5.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                            <li>
                                <span class="num">6.</span>
                                <span class="input"><input type="text" class="specialty-click" name="" id=""/></span>
                                <span class="sel-num"><a href="javascript:;" class="specialty-click">选择专业</a></span>
                            </li>
                        </ul>
                    </td>
                    <td class="item4">
                        <div class="">
                            <label>
                                <input type="radio" checked name="isFun4" value="是" id=""/> 是
                            </label>
                            <p>（建议服从）</p>
                            <label>
                                <input type="radio" name="isFun4" value="否" id=""/> 否
                            </label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="form-control-btn">
            <div class="error-tips2 hide"></div>
            <input type="button" class="btn" id="volunteer-flow3-btn" value="下一步"/>
        </div>
    </div>
</div>
<div id="main2" style="display: none;">
    <div class="content">
        <div class="w1000">
            <div class="content-title mt60">
                <p class="fl w40"></p>

                <p class="fl w20">
                    <i class="fl arraw"></i>
                    <span>评测结果</span>
                    <i class="fr arraw"></i>
                </p>

                <p class="fl w40"></p>
            </div>
            <p class="second-title">独创分差位次修正算法，为您科学推荐报考院校。原来填报志愿可以如此简单！</p>
        </div>
        <div class="w1000">
            <img src="http://cdn.gaokao360.net/static/global/after/images/step-banner.png" class="step-banner"/>
            <table border="1" cellspacing="0">
                <tr class="t-title">
                    <th>姓名</th>
                    <th>性别</th>
                    <th>学校</th>
                    <th>年级</th>
                    <th>科类</th>
                    <th>准考证</th>
                    <th>高考分数</th>
                    <th>一批本科省控线</th>
                </tr>
                <tr class="t-content">
                    <td><b id="studentName"></b></td>
                    <td><span id="sexT"></span></td>
                    <td><span id="schoolName"></span></td>
                    <td></td>
                    <td id="subjectTypeT"></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
            <div class="tip-board">
                <h2>温馨提示</h2>

                <p>隐私权：本报告所含信息属于个人资料，未经许可不得阅读，复制或泄漏个人内容</p>

                <p>说明：填报志愿是一个动态变化的过程，本报告仅作为填报志愿参考，请综合各种信息填报志愿。</p>

                <p>版权：本版权归属陕西省高考360志愿指导平台。未经授权不得拷贝，引用报告中的相关信息。</p>
            </div>
            <table border="1" cellspacing="0">
                <tr class="t-title">
                    <th colspan="2">院校</th>
                    <th>专业</th>
                    <th>是否服从其它专业调试</th>
                </tr>
                <tr class="t-content">
                    <td width="15%"><b>A志愿</b></td>
                    <td width="45%">
                        <div id="print-result-info1" class="print-result-info"></div>
                    </td>
                    <td width="30%">
                        <div class="specialty-list-info" id="specialty-list-info1"></div>
                    </td>
                    <td width="10%">
                        <div id="isFunRadio1" class="isFunRadio"></div>
                    </td>
                </tr>
                <tr class="t-content">
                    <td><b>B志愿</b></td>
                    <td>
                        <div id="print-result-info2" class="print-result-info"></div>
                    </td>
                    <td>
                        <div class="specialty-list-info" id="specialty-list-info2"></div>
                    </td>
                    <td>
                        <div id="isFunRadio2" class="isFunRadio"></div>
                    </td>
                </tr>
                <tr class="t-content">
                    <td><b>C志愿</b></td>
                    <td>
                        <div id="print-result-info3" class="print-result-info"></div>
                    </td>
                    <td>
                        <div class="specialty-list-info" id="specialty-list-info3"></div>
                    </td>
                    <td>
                        <div id="isFunRadio3" class="isFunRadio"></div>
                    </td>
                </tr>
                <tr class="t-content">
                    <td><b>D志愿</b></td>
                    <td>
                        <div id="print-result-info4" class="print-result-info"></div>
                    </td>
                    <td>
                        <div class="specialty-list-info" id="specialty-list-info4"></div>
                    </td>
                    <td>
                        <div id="isFunRadio4" class="isFunRadio"></div>
                    </td>
                </tr>
            </table>
            <div class="evaluate-count">
                <h3 class="eva-title">志愿梯度合理性评估</h3>
                <div class="row">
                    <div class="col-1-img">
                        <img src="http://cdn.gaokao360.net/static/global/after/images/eva-left-line1.png" class="eva-left-bg"/>
                    </div>
                    <div class="school-list-col"></div>
                    <div class="eva" id="eva"></div>
                </div>
            </div>
            <div class="evaluate-count">
                <h3 class="eva-title color-green">去年招生情况</h3>
                <div class="row">
                    <div class="col-1-img">
                        <img src="http://cdn.gaokao360.net/static/global/after/images/eva-left-line2.png" class="eva-left-bg"/>
                    </div>
                    <div id="enrollmentSchool" class="enrollmentSchool"></div>
                    <div id="enrollment"></div>
                </div>
            </div>
            <div class="evaluate-count">
                <h3 class="eva-title color-red">志愿完整度评估</h3>
                <div class="row">
                    <div class="col-1-img">
                        <img src="http://cdn.gaokao360.net/static/global/after/images/eva-left-line3.png" class="eva-left-bg"/>
                    </div>
                    <div class="school-list-col"></div>
                    <div id="integrity"></div>
                </div>
            </div>
            <div class="evaluate-count">
                <h3 class="eva-title color-red">志愿专业是否服从调剂</h3>
                <div class="row">
                    <div class="col-1-img">
                        <img src="http://cdn.gaokao360.net/static/global/after/images/eva-left-line3.png" class="eva-left-bg"/>
                    </div>
                    <div class="school-list-col"></div>
                    <div id="exchange"></div>
                </div>
                <div class="eva-text">
                    作为压线生，心理是比较复杂的，首先是怕从本批次落榜，降档到下一个批次，“上”与“下”的矛盾和不平衡、不甘愿降档的心理始终横跨在自己眼前。其
                    次 担心在本批次中选不到好院校本批次中选不到好的院校，读不到称心如意的专业。因为一本和二本采用的是平行志愿的录取规则，这种规则对于高分考生有
                    利，反之，增加了压线生甚至低分考生的填报风险。是否与其他的考生“撞车”，是否合理地避开热门专业或绕开人们追捧地院校，等等，这里压线生志愿填
                    报时最常问地问题。第三是带点赌博意味地“退尔求其次”地想法。能冲被本批次院校录取那是运气，若不能如愿，反正还有下一个批次地院校因为一本压线
                    生地分数在二本批次还是有较大优势和竞争力地，同理，二本压线生地分数相对于三本，三本压线生相对于高职高专。我提出的三点建议：
                    1  　对于压线考生来说，不要放弃本批次地录用。充分利用高考志愿卡,筛选出合理地高校，志愿高校一定要填满，专业服从调剂，费省会或者热点城市地高
                    校，录取的机会大。
                    2  　关于征集志愿。压线生本批次落档后，要及时留意征集志愿的信息，把握机会。
                    3　心仪的专业所录取，同理女部分考生可以“退而求其次”，“宁单鸡头不当凤尾巴”。以一本考生的成绩来说，在二批本科的录取时候就很有优势，很有
                    可能被自己心仪的专业录取，同理，也试用二本和三本。
                </div>
                <div class="navigation-box">
                    <span class="tip">正式填报志愿请登录网址：<a target="_blank" href="http://sn.gaokao360.net">sn.gaokao360.net</a></span>
                    <div class="btn btn-before" id="prev-btn">返回上一步</div>
                    <div class="btn btn-next" id="print-btn">打印</div>
                </div>
            </div>

        </div>
    </div>
</div>









<div class="tansLayer" style="display: none;"></div>
<div class="volunteer-flow3-layer"  id="volunteer-flow3-layer" style="display: none;">
    <div class="top-close">
        <a href="javascript:;" class="close-btn close-flow3-layer">x</a>
    </div>
    <div class="tips" style="width: 900px;">
        <strong>温馨提示：</strong>
        <p>依据平行志愿规则，系统推荐遵循分数最大化，为您推荐一下A、B、C、D四档院校；考生可根据个性化需求调整院校顺位。</p>
    </div>
    <div class="volunteer-flow3-body">
        <div class="search-box">
            <span>您可选择地区（省份）活查找专业、进行院校推荐！请搜索:</span>
            <select name="" id="province-list">
                <option value="">所有省</option>
            </select>
            <input type="text" name="" class="search-input" id="specialty-input" placeholder="请输入专业名称查询" />
            <%--<label>--%>
                <%--<input type="checkbox" name="" id=""/>从我收藏的院校中获得推荐--%>
            <%--</label>--%>
            <input type="button" class="search-btn" id="specialty-search-btn" value="查询"/>
        </div>
        <div class="info-explain">
            <strong>录取指数说明</strong>
            <ul>
                <li>
                    <span class="starA-icon"></span>可以冲一冲的院校；
                </li>
                <li>
                    <span class="starB-icon"></span>可以稳一稳的院校；
                </li>
                <li>
                    <span class="starC-icon"></span>可以保一保的院校；
                </li>
                <li>
                    <span class="starD-icon"></span>可以垫一垫的院校；
                </li>
            </ul>
        </div>
        <div class="info-result">
            <ul>
                <li class="starA">
                    <div class="title">
                        <span class="">A档（冲）</span>
                        <p>录取指数: <i class="starA-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school0">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真实填报时、可以参考选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list0" dataType="A"></div>
                </li>
                <li class="starB">
                    <div class="title">
                        <span>B档（稳）</span>
                        <p>录取指数: <i class="starB-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school1">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真实填报时、可以参考选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list1"  dataType="B"></div>
                </li>
                <li class="starC">
                    <div class="title">
                        <span>C档（保）</span>
                        <p>录取指数: <i class="starC-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school2">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真实填报时、可以参考选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list2"  dataType="C"></div>
                </li>
                <li class="starD">
                    <div class="title">
                        <span>D档（垫）</span>
                        <p>录取指数: <i class="starD-icon"></i></p>
                    </div>
                    <div class="no-school hide" id="no-school3">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真实填报时、可以参考选择其他档中的院校；</p>
                    </div>
                    <div class="school-list hide" id="school-list3"  dataType="D"></div>
                </li>
            </ul>
        </div>
    </div>
</div>






<div class="specialty-layer"  id="specialty-layer" style="display: none;">
    <div class="top-close">
        <a href="javascript:;" class="close-btn close-specialty-layer">x</a>
    </div>
    <div class="specialty-body school-table" id="school-table">
        <table class="">
            <thead>
                <tr>
                    <th>专业名称</th>
                    <th>批次</th>
                    <th>科类</th>
                    <th>计划人数</th>
                    <th>学制</th>
                    <th>收费标准</th>
                </tr>
            </thead>
            <tbody id="specialty-content"></tbody>
        </table>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("http://cdn.gaokao360.net/static/global/after/scripts/after-flow3.min");
    var params = decodeURIComponent('${params}');
</script>


</body>
</html>
