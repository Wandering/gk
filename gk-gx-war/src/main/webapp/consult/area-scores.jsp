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
            <th rowspan="2">年份</th>
            <th rowspan="2">种类</th>
            <th rowspan="2">本科一批</th>
            <th rowspan="2">本科二批</th>
            <th rowspan="2">本科三批</th>
            <th rowspan="2">高职专科批</th>
            <th colspan="2">普通本科</th>
            <th colspan="2">独立学院本科</th>
            <th colspan="2">高职高专</th>
        </tr>
        <tr>
            <th>艺术类</th>
            <th>体育类</th>
            <th>艺术类</th>
            <th>体育类</th>
            <th>艺术类</th>
            <th>体育类</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td rowspan="2" class="fwb">2015年</td>
            <td>文科</td>
            <td>530</td>
            <td>380</td>
            <td>-</td>
            <td>180</td>
            <td>247</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>126</td>
            <td>-</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>480</td>
            <td>320</td>
            <td>-</td>
            <td>180</td>
            <td>208</td>
            <td>237</td>
            <td>-</td>
            <td>-</td>
            <td>126</td>
            <td>160</td>
        </tr>
        <tr>
            <td rowspan="2" class="fwb">2014年</td>
            <td>文科</td>
            <td>550</td>
            <td>463</td>
            <td>404</td>
            <td>200</td>
            <td>301</td>
            <td>-</td>
            <td>263</td>
            <td>-</td>
            <td>140</td>
            <td>-</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>520</td>
            <td>407</td>
            <td>326</td>
            <td>200</td>
            <td>265</td>
            <td>260</td>
            <td>212</td>
            <td>233</td>
            <td>140</td>
            <td>180</td>
        </tr>
        <tr>
            <td rowspan="2" class="fwb">2013年</td>
            <td>文科</td>
            <td>541</td>
            <td>467</td>
            <td>395</td>
            <td>200</td>
            <td>304</td>
            <td>-</td>
            <td>257</td>
            <td>-</td>
            <td>140</td>
            <td>-</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>510</td>
            <td>413</td>
            <td>310</td>
            <td>200</td>
            <td>268</td>
            <td>259</td>
            <td>202</td>
            <td>221</td>
            <td>140</td>
            <td>169</td>
        </tr>
        <tr>
            <td rowspan="2" class="fwb">2012年</td>
            <td>文科</td>
            <td>544</td>
            <td>473</td>
            <td>409</td>
            <td>200</td>
            <td>308</td>
            <td>-</td>
            <td>266</td>
            <td>-</td>
            <td>140</td>
            <td>-</td>
        </tr>
        <tr>
            <td>理科</td>
            <td>528</td>
            <td>444</td>
            <td>332</td>
            <td>200</td>
            <td>289</td>
            <td>273</td>
            <td>216</td>
            <td>228</td>
            <td>140</td>
            <td>170</td>
        </tr>

        </tbody>

    </table>
</div>

<%@ include file="/common/footer.jsp" %>
</body>
</html>

