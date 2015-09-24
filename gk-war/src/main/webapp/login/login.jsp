<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>登陆</title>
    <link rel="stylesheet" href="/static/dist/login/styles/login.min.css"/>
</head>
<body>


<div class="container">
    <div class="main w1000">
        <!--<img src="/static/dist/common/images/logo-min.png" class="login-logo"/>-->
        <img src="/static/dist/login/images/login-font.png" class="login-bg-fonts"/>

        <div class="login-box">
            <div class="login-title">
                <span class="active">登录</span>
                <span>注册</span>
            </div>
            <!--登陆-->
            <div class="login-input ">
                <span class="error-tips hide"></span>
                <i class="icon-user-account"></i>
                <input type="text" class="input-comm" placeholder="账号"/>
                <span class="error-tips hide"></span>
                <i class="icon-user-psd"></i>
                <input type="password" class="input-comm" placeholder="密码"/>
                <a href="/forget-psd.html" class="forget-psd">忘记密码？</a>
                <div class="btn-login">登陆</div>
            </div>
            <!--注册-->
            <div class="register-input hide">
                <span class="error-tips hide"></span>
                <i class="icon-user-account"></i>
                <input type="tel" class="input-comm" placeholder="手机号"/>
                <i class="icon-user-code"></i>
                <input type="text" class="input-comm pdr100" placeholder="验证码"/>
                <span class="code-text">获取验证码</span>
                <i class="icon-user-psd"></i>
                <input type="password" class="input-comm" placeholder="密码"/>
                <i class="icon-conform-psd"></i>
                <input type="password" class="input-comm" placeholder="确认密码"/>
                <div class="btn-login">登陆</div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <p class="copy">Copyright © 2015.Thinkjoy All rights reserved</p>
    <img src="/static/dist/common/images/login-logo.png" alt="logo360" class="bottom-logo"/>
</div>


</body>
</html>
