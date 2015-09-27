<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <%@include file="/common/meta.jsp"%>
    <title>个人信息</title>
    <link rel="stylesheet" href="/static/dist/user/styles/personal-info.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li class="active"><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="avatar-box fl">
            <img src="/static/dist/common/images/icon_default.png" class="avatar-img"/>
            <div class="btn-upload-img">上传头像</div>
        </div>
        <div class="user-item fr">
            <div class="group-comm account">
                <span class="account-tel">登陆账号</span>
                <span class="account"></span>
                <a href="/user/modify-psd.jsp" class="modify-psd">修改密码</a>
            </div>
            <div class="group-comm">
                真实姓名<input type="text" class="user-input-comm name" value=""/>
            </div>
            <div class="group-comm">
                所在地区
                <select id="cmbProvince" class="user-select-comm ml35"></select>
                <select id="cmbCity" class="user-select-comm"></select>
                <select id="cmbArea" class="user-select-comm"></select>
            </div>
            <div class="group-comm">
                所在学校<input type="text" class="user-input-comm school" value=""/>
            </div>
            <div class="group-comm">
                出生日期<input type="text" class="user-input-comm birthdayDate" />
            </div>
            <div class="group-comm">
                性别选择
                <input type="text" class="user-input-comm sex" />
                <label><input name="sex" type="radio" value="" />男 </label>
                <label><input name="sex" type="radio" value="" />女 </label>
            </div>
            <div class="group-comm">
                科目选择<input type="text" class="user-input-comm subject" />
            </div>
            <div class="group-comm">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email<input type="text" class="user-input-comm mail" />
            </div>
            <div class="group-comm">
                &nbsp;QQ号码<input type="text" class="user-input-comm qq" />
            </div>
            <div class="btn btn-submit">提交</div>
        </div>
    </div>

</div>
<%@include file="/common/footer.jsp"%>
<script>
    seajs.use("/static/src/user/scripts/personal-info");
</script>
</body>
</html>
