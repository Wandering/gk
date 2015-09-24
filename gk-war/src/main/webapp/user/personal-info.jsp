<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>个人信息</title>
    <link rel="stylesheet" href="/static/dist/user/styles/personal-info.min.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li class="active"><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li><a href="modify-psd.jsp">修改密码</a></li>
        <li><a href="">应用中心</a></li>
        <li><a href="">在线答疑</a></li>
        <li><a href="">专家服务</a></li>
    </ul>
    <div class="content">
        <div class="avatar-box fl">
            <img src="/static/dist/common/images/avatar.png" class="avatar-img"/>
            <div class="btn-upload-img">上传头像</div>
        </div>
        <div class="user-item fr">
            <div class="group-comm account">
                <span class="account-tel">登陆账号</span>
                <span>18710921677</span>
                <a href="" class="modify-psd">修改密码</a>
            </div>
            <div class="group-comm">
                真实姓名<input type="text" class="user-input-comm" value="韩小寒"/>
            </div>
            <div class="group-comm">
                所在地区
                <select id="cmbProvince" class="user-select-comm ml35"></select>
                <select id="cmbCity" class="user-select-comm"></select>
                <select id="cmbArea" class="user-select-comm"></select>
            </div>
            <div class="group-comm">
                所在学校<input type="text" class="user-input-comm" value="高新一小"/>
            </div>
            <div class="group-comm">
                出生日期<input type="text" class="user-input-comm" />
            </div>
            <div class="group-comm">
                性别选择
                <input type="text" class="user-input-comm" />
                <!--<label><input name="sex" type="radio" value="" />男 </label>-->
                <!--<label><input name="sex" type="radio" value="" />女 </label>-->
            </div>
            <div class="group-comm">
                科目选择<input type="text" class="user-input-comm" />
            </div>
            <div class="group-comm">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email<input type="text" class="user-input-comm" />
            </div>
            <div class="group-comm">
                &nbsp;QQ号码<input type="text" class="user-input-comm" />
            </div>

        </div>
    </div>

</div>


<%@include file="/common/footer.jsp"%>

<script src="/static/dist/user/scripts/jq-address.js"></script>
<script type="text/javascript">
    addressInit('cmbProvince', 'cmbCity', 'cmbArea', '陕西', '西安市', '高新区');
</script>
</body>
</html>
