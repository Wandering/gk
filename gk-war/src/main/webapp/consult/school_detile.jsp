<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>院校信息详情页</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/consult/styles/school_detile.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">
        <div class="basic-info">
            <h1>院校基本信息</h1>
            <div class="info-content" id="info_content">
            </div>
        </div>

        <div class="other-info">
            <h1>往年招生情况</h1>
            <ul class="tabs-list mt20" id="tabs_list_last">
            </ul>

            <div id="last_content">

            </div>


            <ul class="tabs-list mt20" id="tabs_list_enroll">
            </ul>

            <div>
                <div id="category">
                    <button data-id="1">文科</button>
                    <button data-id="2">理科</button>
                </div>
                <div id="enroll_content"></div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/consult/scripts/school_info_detile");
</script>
</body>
</html>

