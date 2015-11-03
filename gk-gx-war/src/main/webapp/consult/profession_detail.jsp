<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>专业基本信息</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/consult/styles/profession_detile.min.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <div class="w1000">
        <div class="basic-info">
            <h1>专业基本信息</h1>
            <div class="info-content" id="info_content">
            </div>
        </div>

        <div class="other-info">
            <h1>往年招生情况</h1>
            <ul class="tabs-list mt20">
                <li class="active">相近专业</li>
                <li>主要课程</li>
                <li>就业方向</li>
                <li>开设院校</li>
            </ul>
            <div class="tabs-content">
                <div id="tab_0" style="display: none"></div>
                <div id="tab_1" style="display: none"></div>
                <div id="tab_2" style="display: none"></div>
                <div id="tab_3" style="display: none"></div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("http://cdn.gaokao360.net/static/global/consult/scripts/profession_detail.min");
</script>
</body>
</html>

