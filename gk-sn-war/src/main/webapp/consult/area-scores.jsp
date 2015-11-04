<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>地区批次线</title>
    <%@ include file="/common/meta.jsp" %>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/consult/styles/consult.min.css"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div class="content w1000">
    <div class="content-title mt60">
        <p class="fl w40"></p>

        <p class="fl w20">
            <i class="fl arraw"></i>
            <span>地区批次线</span>
            <i class="fr arraw"></i>
        </p>

        <p class="fl w40"></p>
    </div>
    <table id="area-scores-table">
        <thead>
        <tr>
            <th>年份</th>
            <th>种类</th>
            <th colspan="4">各批次控制线</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td rowspan="3" class="fwb">2015年</td>
            <td></td>
            <td>本科一批</td>
            <td>本科二批</td>
            <td>本科三批</td>
            <td>高专高职</td>
        </tr>
        <tr>
            <td>文科</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
        </tr>
        <tr>
            <td rowspan="3" class="fwb">2014年</td>
            <td></td>
            <td>本科一批</td>
            <td>本科二批</td>
            <td>本科三批</td>
            <td>高专高职</td>
        </tr>
        <tr>
            <td>文科</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
        </tr>
        <tr>
            <td rowspan="3" class="fwb">2013年</td>
            <td></td>
            <td>本科一批</td>
            <td>本科二批</td>
            <td>本科三批</td>
            <td>高专高职</td>
        </tr>
        <tr>
            <td>文科</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
            <td>633</td>
        </tr>
        </tbody>

    </table>
</div>

<%@ include file="/common/footer.jsp" %>
<script type="text/javascript"
        src="http://cdn.gaokao360.net/static/global/guide/scripts/flowplayer-3.2.13.min.js"></script>
<script type="text/javascript">
    seajs.use("http://cdn.gaokao360.net/static/global/consult/scripts/consult.min");
</script>
</body>
</html>

