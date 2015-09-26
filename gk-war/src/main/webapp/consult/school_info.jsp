<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>院校信息</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="/static/dist/consult/styles/school_info.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <%@ include file="/common/banner-wipe.jsp"%>
    <div class="w1000">
        <div class="content-title mt60">
            <p class="fl w40"></p>
            <p class="fl w20">
                <i class="fl arraw"></i>
                <span>院校信息</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">及时、准确发布在陕招生的相关院校、专业及招生计划信息，方便您查询、筛选、了解。</h6>

        <div class="search-content ta mt60">
            <span>
                 <input type="text" placeholder="请输入学院名称" id="school_serach"/>
                 <input type="button" value="搜索" id="search_button"/>
            </span>
        </div>

        <ul class="select-option">
            <li class="lable">所在省份：</li>
            <li class="options" id="provinces">
            </li>
        </ul>
        <ul class="select-option">
            <li class="lable">院校类型：</li>
            <li class="options" id="universityType">
            </li>
        </ul>
        <ul class="select-option">
            <li class="lable">院校批次：</li>
            <li class="options" id="universityBatch">
            </li>
        </ul>
        <ul class="select-option">
            <li class="lable">院校特征：</li>
            <li class="options" id="universityFeature">
            </li>
        </ul>

        <div class="school-table mt20" id="school_list">
        </div>
        <div class="page" id="page">
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>
<script type="text/javascript">
    seajs.use("${ctx}/static/src/consult/scripts/school_info");
</script>
</body>
</html>

