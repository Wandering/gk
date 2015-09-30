<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp" %>
    <title>我要预约</title>
    <link rel="stylesheet" href="/static/dist/user/styles/order-expert.min.css"/>
    <%--<link rel="stylesheet" href="/static/bower_components/laydate/need/laydate.css"/>--%>
    <%--<link rel="stylesheet" href="/static/bower_components/laydate/skins/dahong/laydate.css"/>--%>
    <link rel="stylesheet" href="/static/bower_components/kindeditor/themes/default/default.css"/>
    <script src="/static/bower_components/laydate/laydate.js"></script>
</head>
<body>
<%@include file="/common/header.jsp" %>
<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li><a href="personal-info.jsp">个人信息</a></li>
        <li><a href="vip-service.jsp">VIP服务</a></li>
        <li><a href="modify-psd.jsp">修改密码</a></li>
        <li><a href="app-center.jsp">应用中心</a></li>
        <li><a href="online-answer.jsp">在线答疑</a></li>
        <li class="active"><a href="expert-service.jsp">专家服务</a></li>
    </ul>

    <div class="content">
        <div class="search-content">
             <%--<span>--%>
                 <%--<input type="text" placeholder="请输入搜索内容">--%>
                 <%--<input type="button" value="搜索">--%>
            <%--</span>--%>
        </div>
        <div class="input-item-comm">
            <span class="w-title">预约主题</span>
            <input type="text" class="comm-input order-theme">
        </div>
        <div class="input-item-comm">
            <span class="w-title">预约日期</span>
            <input class="comm-input data-start laydate-icon" id="start">
            &nbsp;至
            <input class="comm-input data-end laydate-icon" id="end">
            <span class="des-text">请告诉我们您期望与专家见面沟通的日期</span>
        </div>
        <script>
            var start = {
                elem: '#start',
                format: 'YYYY/MM/DD hh:mm:ss',
                min: laydate.now(), //设定最小日期为当前日期
                max: '2099-06-16 23:59:59', //最大日期
                istime: true,
                istoday: false,
                choose: function(datas){
                    end.min = datas; //开始日选好后，重置结束日的最小日期
                    end.start = datas //将结束日的初始值设定为开始日
                }
            };
            var end = {
                elem: '#end',
                format: 'YYYY/MM/DD hh:mm:ss',
                min: laydate.now(),
                max: '2099-06-16 23:59:59',
                istime: true,
                istoday: false,
                choose: function(datas){
                    start.max = datas; //结束日选好后，重置开始日的最大日期
                }
            };
            laydate(start);
            laydate(end);
        </script>
        <div class="input-item-comm">
            <span class="w-title require">您的需求</span>
            <textarea name="content" id="content"></textarea>
        </div>
        <div class="input-item-comm">
            <span class="w-title">您的姓名</span>
            <input type="text" class="comm-input name">
        </div>
        <div class="input-item-comm">
            <span class="w-title">联系电话</span>
            <input type="text" class="comm-input mobile">
        </div>
        <div class="input-item-comm">
            <span class="w-title">QQ号码</span>
            <input type="text" class="comm-input qq">
        </div>
        <div class="error-tips hide"></div>
        <div class="btn-box">
            <div class="btn btn-submit">提交</div>
            <div class="btn btn-submit">重置</div>
        </div>
    </div>
</div>


<%@include file="/common/footer.jsp" %>

<%--<script src="/static/bower_components/kindeditor/kindeditor.js"></script>--%>
<%--<script src="/static/bower_components/kindeditor/lang/zh-CN.js"></script>--%>

<script>
    seajs.use("/static/src/user/scripts/order-expert");
</script>

</body>
</html>
