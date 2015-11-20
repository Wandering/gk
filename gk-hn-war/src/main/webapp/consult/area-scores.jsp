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
            <th>本科一批</th>
            <th>本科二批</th>
            <th>本科三批</th>
            <th>高专高职</th>
            <th>普通本科艺术类</th>
            <th>高职高专艺术类</th>
            <th>体育类</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td rowspan="2" class="fwb">2015年</td>
            <td>文科</td>
            <td>510</td>
            <td>467</td>
            <td>382</td>
            <td>220</td>
            <td>304</td>
            <td>154</td>
            <td></td>
        </tr>
        <tr>
            <td>理科</td>
            <td>480</td>
            <td>440</td>
            <td>350</td>
            <td>200</td>
            <td>286</td>
            <td>140</td>
            <td>278</td>
        </tr>
        <tr>
            <td rowspan="2" class="fwb">2014年</td>
            <td>文科</td>
            <td>548</td>
            <td>492</td>
            <td>387</td>
            <td>273</td>
            <td>320</td>
            <td>191</td>
            <td></td>
        </tr>
        <tr>
            <td>理科</td>
            <td>503</td>
            <td>452</td>
            <td>342</td>
            <td>256</td>
            <td>294</td>
            <td>179</td>
            <td>286</td>
        </tr>
        <tr>
            <td rowspan="2" class="fwb">2013年</td>
            <td>文科</td>
            <td>540</td>
            <td>486</td>
            <td>386</td>
            <td>200</td>
            <td>316</td>
            <td>140</td>
            <td></td>
        </tr>
        <tr>
            <td>理科</td>
            <td>485</td>
            <td>435</td>
            <td>330</td>
            <td>150</td>
            <td>283</td>
            <td>105</td>
            <td>280</td>
        </tr>
        <tr>
            <td rowspan="2" class="fwb">2012年</td>
            <td>文科</td>
            <td>556</td>
            <td>497</td>
            <td>377</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>理科</td>
            <td>517</td>
            <td>461</td>
            <td>331</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>

    </table>
</div>

<%@ include file="/common/footer.jsp" %>
</body>
</html>

