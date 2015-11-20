<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>登录</title>
    <%@include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/login/styles/login.min.css"/>

</head>
<body>


<div class="container">
    <div class="main w1000">
        <!--<img src="/static/dist/common/images/logo-min.png" class="login-logo"/>-->
        <img src="http://cdn.gaokao360.net/static/global/login/images/login-font.png" class="login-bg-fonts"/>

        <div class="login-box">
            <div class="login-title">
                <span class="active tab-login">登录</span>
                <span class="tab-register">注册</span>
            </div>
            <!--登陆-->
            <div class="login-input">
                <span class="error-tip1"></span>
                <i class="icon-user-account"></i>
                <input type="text" class="input-comm login-account" placeholder="账号"/>
                <div style="height: 25px;"></div>
                <i class="icon-user-psd"></i>
                <input type="password" class="input-comm login-password" placeholder="密码"/>
                <a href="/login/forget-psd.jsp" class="forget-psd">忘记密码？</a>
                <div class="btn-login">登录</div>
            </div>
            <!--注册-->
            <div class="register-input hide">
                <span class="error-tip2 "></span>
                <i class="icon-user-account"></i>
                <input type="tel" class="input-comm tel-number" placeholder="手机号"/>
                <i class="icon-user-code"></i>
                <input type="text" class="input-comm pdr100 captcha-code" placeholder="验证码"/>
                <button class="code-text">获取验证码</button>
                <i class="icon-user-psd"></i>
                <input type="password" class="input-comm reg-password" placeholder="密码"/>
                <i class="icon-conform-psd"></i>
                <input type="password" class="input-comm reg-password-confirm" placeholder="确认密码"/>
                <div class="btn-login-register">注册</div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <p class="copy">Copyright © 2015.Thinkjoy All rights reserved</p>
    <img src="http://cdn.gaokao360.net/static/global/common/images/login-logo.png" alt="logo360" class="bottom-logo"/>
</div>
<script>
    seajs.use("http://cdn.gaokao360.net/static/global/login/scripts/login.min");
</script>
</body>
</html>
