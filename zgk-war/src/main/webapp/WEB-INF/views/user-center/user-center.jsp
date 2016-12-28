<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>智高考专家后台</title>
    <%@ include file="../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static-expert/src/css/user-info/user-center.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@ include file="../common/sidebar.jsp" %>
        <div class="main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main" id="user-center">
                            <div class="user-img-upload">
                                <img src="<%=ctx%>/static-expert/src/img/avatar-icon.png" class="avatar-img" id="avatar-img"/>
                                <div id="uploader1" class="wu-example">
                                    <div class="uploader-tips">(仅支持JPG、JPEG、PNG格式（3M以下）尺寸 100px * 100px)</div>
                                    <div class="queueList">
                                        <div id="dndArea" class="placeholder">
                                            <div id="uploaderBtn1" class="upload-avatar">上传头像</div>
                                        </div>
                                    </div>
                                    <div id="fileLi"></div>
                                    <div class="statusBar" style="display:none;">
                                        <div class="progress">
                                            <span class="text">0%</span>
                                            <span class="percentage"></span>
                                        </div>
                                        <div class="info"></div>
                                        <div class="btns">
                                            <div class="uploadBtn1" id="uploadBtn1">开始上传</div>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" value="" id="imgUrlData">
                            </div>

                            <div class="user-info-list">
                                <div class="account-number">
                                    <span class="w80">登录账号</span>
                                    <span class="number" id="user-phone"></span>
                                    <span class="modify-pwd" id="modify-pwd">修改密码</span>
                                </div>
                                <div class="common-control">
                                    <label for="user-name">用户名称</label>
                                    <input type="text" class="user-name" id="user-name" placeholder="用户名称">
                                </div>
                                <div class="common-control">
                                    <label for="user-qq">QQ账号&nbsp;&nbsp;</label>
                                    <input type="text" class="user-qq" id="user-qq" placeholder="QQ账号">
                                </div>
                                <div class="common-control">
                                    <label for="user-qq">微信账号&nbsp;&nbsp;</label>
                                    <input type="text" class="user-wx" id="user-wx" placeholder="微信账号">
                                </div>
                                <button class="btn btn-submit" id="submit-btn">保存</button>
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="../common/footer.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=ctx%>/static-expert/src/lib/webuploader-0.1.5 2/webuploader.css">
<script src="<%=ctx%>/static-expert/src/lib/webuploader-0.1.5 2/webuploader.js"></script>
<script>
    var BASE_URL = '<%=ctx%>/static-expert/src/lib/';
    var rootPath = '<%=ctx%>';
</script>
<script src="<%=ctx%>/static-expert/src/js/user-info/user-center.js"></script>
</body>
</html>
