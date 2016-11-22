<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>智高考专家后台</title>
    <%@ include file="../common/meta.jsp"%>
    <link href="http://nos.netease.com/vod163/nep.min.css" rel="stylesheet">
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
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main-title">
                            <h3>一对一视频</h3>
                        </div>
                        <div class="">
                            <video id="my-video" class="video-js" x-webkit-airplay="allow" webkit-playsinline controls poster="//nos.netease.com/vod163/poster.png" preload="auto" width="640" height="360" data-setup="{}">
                                <source src="http://nos.netease.com/vod163/demo.mp4" type="video/mp4">
                            </video>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="../common/footer.jsp"%>
<script src="http://nos.netease.com/vod163/nep.min.js"></script>
</body>
</html>
