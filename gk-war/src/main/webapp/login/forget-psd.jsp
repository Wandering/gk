<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>找回密码</title>
    <%@include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/login/styles/login.min.css"/>
</head>
<body>


<div class="container">
    <div class="main w1000">
        <!--<img src="/static/dist/common/images/logo-min.png" class="login-logo"/>-->
        <img src="/static/dist/login/images/login-font.png" class="login-bg-fonts"/>

        <div class="login-box">
            <div class="login-title find-psd-h40">
                <span class="find-psd">找回密码</span>
            </div>
            <!--找回密码-->
            <div class="register-input">
                <span class="error-tip1">&nbsp;</span>
                <i class="icon-user-account icon-user-account-50"></i>
                <input type="tel" class="input-comm tel" placeholder="注册账户的手机号"/>
                <i class="icon-user-code icon-user-code-105"></i>
                <input type="text" class="input-comm pdr100 code" placeholder="验证码"/>
                <button class="code-text top77">获取验证码</button>
                <i class="icon-user-psd icon-user-psd-164"></i>
                <input type="password" class="input-comm new-psd" placeholder="新密码"/>
                <i class="icon-conform-psd icon-user-psd-221"></i>
                <input type="password" class="input-comm confirm-psd" placeholder="确认密码"/>
                <span class="error-tip2"></span>
                <div class="btn-login" style="margin-top: 0">确定</div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <p class="copy">Copyright © 2015.Thinkjoy All rights reserved</p>
    <img src="/static/dist/common/images/login-logo.png" alt="logo360" class="bottom-logo"/>
</div>
<script>
    seajs.use("${ctx}/static/src/login/scripts/forget-psd");
</script>

</body>
</html>
