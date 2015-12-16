<%--
  Created by IntelliJ IDEA.
  User: sfdeng
  Date: 15/12/16
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>支付宝分账数据查询</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1 class="text-center">支付宝分账数据查询</h1>
    <div id="">
        <table class="table table-bordered ">
            <thead>
            <tr>
                <th class="text-center">省份</th>
                <th class="text-center">销售总金额</th>
            </tr>
            </thead>
            <tbody id="list-msg-item2"></tbody>
        </table>
    </div>
    <br>
    <form class="form-inline">
        <div class="form-group">
            <label class="sr-only" for="userAccount">用户账户(手机号)</label>
            <input type="text" class="form-control" id="userAccount" placeholder="请输入用户账号">
        </div>
        <div class="form-group">
            <label class="sr-only" for="userOrder">订单号</label>
            <input type="text" class="form-control" id="userOrder" placeholder="请输入订单号">
        </div>
        <button type="button" class="btn btn-primary" id="searchBtn">查询</button>
    </form>
    <br>
    <div id="">
        <table class="table table-bordered ">
            <thead>
            <tr>
                <th class="text-center">激活状态</th>
                <th class="text-center">订单金额</th>
                <th class="text-center">渠道</th>
                <th class="text-center">创建日期</th>
                <th class="text-center">订单ID</th>
                <th class="text-center">订单号</th>
                <th class="text-center">支付状态</th>
                <th class="text-center">省份</th>
                <th class="text-center">用户账户</th>
                <th class="text-center">用户ID</th>
            </tr>
            </thead>
            <tbody id="list-msg-item" pageNo="0"></tbody>
        </table>
        <button type="button" class="btn btn-primary" style="display: block;width:120px;margin: 0 auto;"  id="nextPage">加载更多</button>
    </div>







</div>
<script type="text/javascript">
    $(function () {
        var UI = {
            $listMsgItem: $('#list-msg-item'),
            $listMsgItem2: $('#list-msg-item2'),
            $nextPage: $('#nextPage'),
            $searchBtn: $('#searchBtn')

        };

        UI.$nextPage.on('click', function () {
            var pageNo = UI.$listMsgItem.attr('pageNo');
            var userAccountV = $('#userAccount').val();
            var userOrderV = $('#userOrder').val();
            getList(pageNo, 8, userAccountV, userOrderV);
        }).click();
        UI.$searchBtn.on('click', function () {
            UI.$listMsgItem.attr('pageNo','0').html('');
            var pageNo = UI.$listMsgItem.attr('pageNo');
            var userAccountV = $('#userAccount').val();
            var userOrderV = $('#userOrder').val();
            getList(pageNo, 8, userAccountV, userOrderV);
        });

        function getList(pageNo, pageSize, userAccount, orderNo) {
            $.getJSON(
                    "/orders/getOrderDetail.do",
                    {
                        "pageNo": pageNo, //页码号
                        "pageSize": pageSize, //每页显示数目
                        "userAccount": userAccount, //用户账户
                        "orderNo": orderNo //订单号
                    },
                    function (result) {
                        for (var i = 0; i < result.length; i++) {
                            var tableTr = [];
                            tableTr.push('<tr><td class="text-center">'+ result[i].activeStatus +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].amount +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].channel +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].createDate +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].id +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].orderNo +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].payStatus +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].provinceName +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].userAccount +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].userId +'</td></tr>');
                            UI.$listMsgItem.append(tableTr.join(''));
                        }
                        pageNo++;
                        UI.$listMsgItem.attr('pageNo', pageNo);
                    });
        }

        getList2()
        function getList2() {
            $.getJSON(
                    "/orders/getOrderStatisticsData.do",
                    function (result) {
                        console.log(result)
                        for (var i = 0; i < result.length; i++) {
                            var tableTr = [];
                            tableTr.push('<tr><td class="text-center">'+ result[i].provinceName +'</td>');
                            tableTr.push('<td class="text-center">'+ result[i].totleAmount +'</td></tr>');
                            UI.$listMsgItem2.append(tableTr.join(''));
                        }
                    });
        }
    });
</script>


</body>
</html>
