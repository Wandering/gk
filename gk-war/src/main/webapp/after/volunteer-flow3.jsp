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
                                     <input type="text" class="open-flow3" placeholder="请输入院校名称"/>
                                     <input type="button"  class="open-flow3" value="选择院校"/>
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
                                     <input type="text"  class="open-flow3" placeholder="请输入院校名称"/>
                                     <input type="button"  class="open-flow3" value="选择院校"/>
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
                                     <input type="text"  class="open-flow3" placeholder="请输入院校名称"/>
                                     <input type="button"  class="open-flow3" value="选择院校"/>
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
                                     <input type="text"  class="open-flow3" placeholder="请输入院校名称"/>
                                     <input type="button"  class="open-flow3" value="选择院校"/>
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
</div>



<div class="tansLayer" style="display: none;"></div>
<div class="volunteer-flow3-layer" style="display: none;">
    <div class="top-close">
        <a href="javascript:;" class="close-btn close-flow3-layer">x</a>
    </div>
    <div class="tips">
        <strong>温馨提示：</strong>
        <p>依据平行志愿规则，系统推荐遵循分数最大化，为您推荐一下A、B、C、D四档院校；考生可根据个性化需求调整院校顺位。</p>
    </div>
    <div class="volunteer-flow3-body">
        <div class="search-box">
            <span>您可选择地区（省份）活查找专业、进行院校推荐！请搜索:</span>
            <select name="" id="">
                <option value="">所有省</option>
            </select>
            <input type="text" name="" class="search-input" id="" placeholder="请输入专业名称查询" />
            <label>
                <input type="checkbox" name="" id=""/>从我收藏的院校中获得推荐
            </label>
            <input type="button" class="search-btn" value="查询"/>
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
                    <div class="title"  onclick="window.location.href='/after/volunteer-flow5.jsp'">
                        <span class="">A档（冲）</span>
                        <p>录取指数: <i class="starA-icon"></i></p>
                    </div>
                    <div class="school-list">
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                    </div>
                </li>
                <li class="starB">
                    <div class="title">
                        <span>B档（稳）</span>
                        <p>录取指数: <i class="starB-icon"></i></p>
                    </div>
                    <div class="school-list">
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                    </div>
                </li>
                <li class="starC">
                    <div class="title">
                        <span>C档（保）</span>
                        <p>录取指数: <i class="starC-icon"></i></p>
                    </div>
                    <div class="no-school">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真实填报时、可以参考选择其他档中的院校；</p>
                    </div>
                    <div class="school-list">
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                    </div>
                </li>
                <li class="starD">
                    <div class="title">
                        <span>D档（垫）</span>
                        <p>录取指数: <i class="starD-icon"></i></p>
                    </div>
                    <div class="no-school">
                        <p>① 本录取指数下、没有合理院校可推荐；</p>
                        <p>② 在真实填报时、可以参考选择其他档中的院校；</p>
                    </div>
                    <div class="school-list">
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                        <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                    </div>
                </li>
            </ul>
        </div>
    </div>

</div>

<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/after/scripts/after");
</script>


</body>
</html>
