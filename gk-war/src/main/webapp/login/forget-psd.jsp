<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link rel="stylesheet" href="../../dist/login/styles/login.min.css"/>
</head>
<body>


<div class="container">
    <div class="main w1000">
        <!--<img src="../../dist/common/images/logo-min.png" class="login-logo"/>-->
        <img src="../../dist/login/images/login-font.png" class="login-bg-fonts"/>

        <div class="login-box">
            <div class="login-title find-psd-h40">
                <span class="find-psd">找回密码</span>
            </div>
            <!--找回密码-->
            <div class="register-input">
                <span class="error-tips hide"></span>
                <i class="icon-user-account"></i>
                <input type="tel" class="input-comm" placeholder="注册账户的手机号"/>
                <i class="icon-user-code"></i>
                <input type="text" class="input-comm pdr100" placeholder="验证码"/>
                <span class="code-text">获取验证码</span>
                <i class="icon-user-psd"></i>
                <input type="password" class="input-comm" placeholder="新密码"/>
                <i class="icon-conform-psd"></i>
                <input type="password" class="input-comm" placeholder="确认密码"/>
                <div class="btn-login">登陆</div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <p class="copy">Copyright © 2015.Thinkjoy All rights reserved</p>
    <img src="../login/images/login-logo.png" alt="logo360" class="bottom-logo"/>
</div>


</body>
</html>
