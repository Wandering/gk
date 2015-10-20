<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>vip服务</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/user/styles/vip-service.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>
<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="/user/personal-info.jsp">个人信息</a></li>
        <li class="active"><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="banner-box">
            <img src="/static/dist/user/images/vip-service-banner.png" class="banner-img"/>
        </div>
    </div>
</div>
<div class="section">
    <h1>马上升级为VIP</h1>

    <div class="choose-pay-box">
        <div class="w1000">
            <div class="box-left">
                <img src="/static/dist/user/images/online-pay-img.png" class="pay-title"/>

                <div class="pay-box">
                    <p class="pay-li">充值账号：<span class="accountNum"></span></p>

                    <p>支付金额： <span class="pay-money">498</span> 元</p>

                    <p>付款方式：</p><img src="/static/dist/user/images/icon-pay-zfb.png" class="pay-ab"/>
                </div>
                <div class="btn-upgrade btn-red" id="createOrderBtn">升级</div>
            </div>
            <div class="box-right">
                <img src="/static/dist/user/images/online-card-img.png" class="pay-title"/>

                <div class="pay-box">
                    <p>充值账号：<span class="accountNum"></span></p>
                    <div class="input-box">
                        <img src="/static/dist/user/images/icon-card-pay.png" alt=""/>
                        卡号：<input type="text" id="pay-card" class="input-pay-comm"/>
                    </div>
                    <div class="input-box">
                        <img src="/static/dist/user/images/icon-pay-lock.png" alt=""/>
                        密码：<input type="text" id="pay-password"  class="input-pay-comm"/>
                    </div>
                </div>
                <div class="error-tips hide"></div>
                <div class="btn-upgrade btn-green" id="accountBtn">升级</div>
            </div>
        </div>
    </div>
</div>
<div class="section w1000">
    <p class="link-tel-title">全国服务商联系方式及地址 </p>
    <ul class="tab-info ">
        <div class="tel-content-box">
            <div class="row" id='address-box'>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>
                <%--<div class="col-3">--%>
                <%--<p class="area-name">陕西省延安市</p>--%>

                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                <%--陕西省延安市--%>
                <%--</p>--%>
                <%--</div>--%>

            </div>
        </div>
    </ul>
</div>


<%@include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/user/scripts/vip-service");
</script>

</body>
</html>