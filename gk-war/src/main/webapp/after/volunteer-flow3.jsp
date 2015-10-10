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
                        <div class="result-info-details" id="result-info1">
                            <%--<p>--%>
                                <%--院校代码：4004  <br/>--%>
                                <%--院校特征：211 研<br/>--%>
                                <%--院校隶属：教育部直属   <br/>--%>
                                <%--院校类型：工科             <br/>--%>
                                <%--2014年最低投档分：592           <br/>--%>
                                <%--2014年最低位次：8959                 <br/>--%>
                                <%--2014年录取平均分：602                     <br/>--%>
                                <%--2014年平均分位次：7006                         <br/>--%>
                                <%--历年招生情况：实际招生超过计划招生数                    <br/>--%>
                                <%--录取指数：★★--%>
                            <%--</p>--%>
                        </div>
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
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">3.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">4.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">5.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">6.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                    </ul>
                </td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" checked name="isFun1" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun1" id=""/> 否
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
                        <div class="result-info-details" id="result-info2">

                        </div>
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
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">3.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">4.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">5.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">6.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                    </ul>
                </td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" checked name="isFun2" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun2" id=""/> 否
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
                        <div class="result-info-details" id="result-info3">

                        </div>
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
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">3.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">4.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">5.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">6.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                    </ul>
                </td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" checked name="isFun3" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun3" id=""/> 否
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
                        <div class="result-info-details" id="result-info4">

                        </div>
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
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">3.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">4.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">5.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                        <li>
                            <span class="num">6.</span>
                            <span class="input"><input type="text" name="" id=""/></span>
                            <span class="sel-num"><a href="javascript:;">选择专业</a></span>
                        </li>
                    </ul>
                </td>
                <td class="item4">
                    <div class="">
                        <label>
                            <input type="radio" checked name="isFun4" id=""/> 是
                        </label>
                        <p>（建议服从）</p>
                        <label>
                            <input type="radio" name="isFun4" id=""/> 否
                        </label>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="form-control-btn">
        <input type="button" class="btn" id="volunteer-flow3-btn" value="下一步"/>
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
    <div class="specialty-body school-table">
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
            <tbody id="specialty-content">

            </tbody>

        </table>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/after/scripts/after-flow3");
    var params = decodeURIComponent('${params}');
</script>


</body>
</html>
