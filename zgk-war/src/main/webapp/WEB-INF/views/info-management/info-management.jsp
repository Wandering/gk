<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>智高考专家后台</title>
        <%@ include file="../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management/info-management.css" />
    </head>
    <body>
        <%@ include file="../common/header.jsp"%>
        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try{ace.settings.check('main-container' , 'fixed')}catch(e){}
            </script>
            <div class="main-container-inner">
                <a class="menu-toggler" id="menu-toggler" href="#">
                    <span class="menu-text"></span>
                </a>
                <%@ include file="../common/sidebar.jsp"%>
                <div class="main-content">

                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>我的预约</h3>
                                </div>
                                <table class="booking-table" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>服务内容</th>
                                            <th width="100px">剩余服务次数</th>
                                            <th width="200px">预约状态</th>
                                            <th width="90px">服务专家</th>
                                            <th width="160px">预约时间</th>
                                            <th width="100px">视频方式</th>
                                            <th width="90px"></th>
                                            <th width="90px"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>选课指导服务</td>
                                            <td>1次</td>
                                            <td><span class="cur-book-status">预约成功</span>——<span>服务中</span>——<span>结束</span></td>
                                            <td><a href="javascript: void(0);" class="expert-btn">莫英强</a></td>
                                            <td>2016-04-15  12:00-13：00</td>
                                            <td>QQ</td>
                                            <td><a href="javascript: void(0);" class="video-btn">进入视频</a></td>
                                            <td><a href="javascript: void(0);" class="evaluate-btn">评价</a></td>
                                        </tr>
                                        <tr>
                                            <td>选课指导服务</td>
                                            <td>0次</td>
                                            <td><span>预约成功</span>——<span  class="cur-book-status">服务中</span>——<span>结束</span></td>
                                            <td><a href="javascript: void(0);" class="expert-btn">莫英强</a></td>
                                            <td>2016-04-15  12:00-13：00</td>
                                            <td>微信</td>
                                            <td><a href="javascript: void(0);" class="video-btn">进入视频</a></td>
                                            <td><a href="javascript: void(0);" class="evaluate-btn">评价</a></td>
                                        </tr>
                                        <tr>
                                            <td>选课指导服务</td>
                                            <td>0次</td>
                                            <td><span>预约成功</span>——<span>服务中</span>——<span  class="cur-book-status">结束</span></td>
                                            <td><a href="javascript: void(0);" class="expert-btn">莫英强</a></td>
                                            <td>2016-04-15  12:00-13：00</td>
                                            <td>智高考</td>
                                            <td><a href="javascript: void(0);" class="video-btn">进入视频</a></td>
                                            <td><a href="javascript: void(0);" class="evaluate-btn">评价</a></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/js/info-management/info-management.js"></script>
    </body>
</html>
