<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>考前备考</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/before/styles/before.css"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>



<div class="w1000">
    <div class="volunteer-flow3-layer">
        <div class="tips">
            <strong>温馨提示：</strong>
            <p>本页推荐结果、依据您的分数（模考成绩）进行评估获得。如果您想获得更为精确的志愿指导，请使用我们为您提供的志愿指导服务。</p>
        </div>
        <div class="volunteer-flow3-body">
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
                            <span>B档（稳）</span>
                            <p>录取指数: <i class="starC-icon"></i></p>
                        </div>
                        <div class="no-school">
                            <p>① 本录取指数下、没有合理院校可推荐；</p>
                            <p>② 在真是填报时、可以参开选择其他档中的院校；</p>
                        </div>
                        <div class="school-list">
                            <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                            <div><a href="" class="school-name">西安交通大学</a><a href="" class="school-sel">选择</a></div>
                        </div>
                    </li>
                    <li class="starD">
                        <div class="title">
                            <span>B档（稳）</span>
                            <p>录取指数: <i class="starD-icon"></i></p>
                        </div>
                        <div class="no-school">
                            <p>① 本录取指数下、没有合理院校可推荐；</p>
                            <p>② 在真是填报时、可以参开选择其他档中的院校；</p>
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
</div>


<%@ include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/before/scripts/result");
</script>
</body>
</html>
