<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>院校信息详情页</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/consult/styles/school_detail.min.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
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
            <div id="select_batch">
            </div>
            <div id="last_content">

            </div>


            <ul class="tabs-list mt20" id="tabs_list_enroll">
            </ul>

            <div>
                <div id="category">
                    <button data-id="1">文史</button>
                    <button data-id="2">理工</button>
                </div>
                <div id="enroll_content"></div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("/static/src/consult/scripts/school_info_detail");
//    seajs.use("http://cdn.gaokao360.net/static/global/consult/scripts/school_info_detail");
</script>
</body>
</html>

