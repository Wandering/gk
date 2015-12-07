<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <title>个人信息</title>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/user/styles/personal-info.min.css"/>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/bower_components/uploadify/uploadify.css"/>
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
        <li><a href="/user/my-collect.jsp">我的收藏</a></li>
    </ul>
    <div class="container">
        <div class="content">
            <div class="avatar-box fl">
                <img src="http://cdn.gaokao360.net/static/global/common/images/icon_default.png" class="avatar-img" id="avatar-img"/>
                <div class="btn-upload-img" id="uploadify">上传头像</div>
            </div>
            <div class="user-item fr">
                <div class="error-tips hide"></div>
                <div class="group-comm account-box">
                    登录账号
                    <span class="account-tel" id="account-tel"></span>
                    <a href="/user/modify-psd.jsp" class="modify-psd">修改密码</a>
                </div>
                <div class="group-comm">
                    真实姓名<input type="text" class="user-input-comm name" id="userName" value=""/>
                </div>
                <div class="group-comm">
                    所在地区
                    <select id="cmbProvince" class="user-select-comm ml35">
                        <option>请选择...</option>
                    </select>
                    <select id="cmbCity" class="user-select-comm">
                        <option>请选择...</option>
                    </select>
                    <select id="cmbArea" class="user-select-comm">
                        <option>请选择...</option>
                    </select>
                </div>
                <div class="group-comm">
                    所在学校<input type="text" class="user-input-comm school" id="school" value=""/>
                </div>
                <div class="group-comm">
                    出生日期<input onclick="laydate()" class="user-input-comm birthdayDate laydate-icon" id="birthdayDate" />
                </div>
                <div class="group-comm">
                    性别选择
                    <label class="radius-comm"><input name="sex" type="radio" value="1" id="sex_m"/>男 </label>
                    <label class="radius-comm"><input name="sex" type="radio" value="0" id="sex_w"/>女 </label>
                </div>
                <div class="group-comm">
                    科目选择
                    <label class="radius-comm"><input name="subject" type="radio" value="0" id="subject_w"/>文科 </label>
                    <label class="radius-comm"><input name="subject" type="radio" value="1" id="subject_l"/>理科 </label>
                </div>
                <div class="group-comm">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email<input type="text" class="user-input-comm mail" id="email" />
                </div>
                <div class="group-comm">
                    &nbsp;QQ号码<input type="text" class="user-input-comm qq" id="qq"/>
                </div>
                <div class="btn btn-submit" id="btn-submit">提交</div>
            </div>
        </div>
    </div>
</div>
<script>
    seajs.use("/static/src/user/scripts/personal-info");
</script>
<%@include file="/common/footer.jsp"%>
</body>
</html>
