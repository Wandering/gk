<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <title>个人信息</title>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/user/styles/personal-info.min.css"/>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/bower_components/laydate/need/laydate.css"/>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/bower_components/laydate/skins/dahong/laydate.css"/>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/bower_components/uploadify/uploadify.css"/>
</head>
<body>
<%@include file="/common/header.jsp"%>


<div class="section w1000">
    <ul class="tabs-list personal-tab">
        <li class="active"><a href="/user/personal-info.jsp">个人信息</a></li>
        <li><a href="/user/vip-service.jsp">VIP服务</a></li>
        <li><a href="/user/modify-psd.jsp">修改密码</a></li>
        <li><a href="/user/app-center.jsp">应用中心</a></li>
        <li><a href="/user/online-answer.jsp">在线答疑</a></li>
        <li><a href="/user/expert-service.jsp">专家服务</a></li>
    </ul>
    <div class="container">
        <div class="content hide">
            <div class="avatar-box hide fl">
                <img src="" class="avatar-img "/>
                <div class="btn-upload-img" id="uploadify">上传头像</div>
            </div>
            <div class="user-item fr">
                <div class="error-tips hide"></div>
                <div class="group-comm account-box">
                    登录账号
                    <span class="account-tel"></span>
                    <a href="/user/modify-psd.jsp" class="modify-psd">修改密码</a>
                </div>
                <div class="group-comm">
                    真实姓名<input type="text" class="user-input-comm name" value=""/>
                </div>
                <div class="group-comm">
                    所在地区
                    <select id="cmbProvince" class="user-select-comm ml35">
                        <option>请选择...</option>
                    </select>
                    <select id="cmbCity" class="user-select-comm">
                        <option>请选择...</option>
                    </select>
                    <select id="cmbArea" class="user-select-comm">
                        <option>请选择...</option>
                    </select>
                </div>
                <div class="group-comm">
                    所在学校<input type="text" class="user-input-comm school" value=""/>
                </div>
                <div class="group-comm">
                    出生日期<input onclick="laydate()" class="user-input-comm birthdayDate laydate-icon" />
                </div>
                <div class="group-comm">
                    性别选择
                    <label class="radius-comm"><input name="sex" type="radio" value="1" id="sex_m"/>男 </label>
                    <label class="radius-comm"><input name="sex" type="radio" value="0" id="sex_w"/>女 </label>
                </div>
                <div class="group-comm">
                    科目选择
                    <label class="radius-comm"><input name="subject" type="radio" value="0" id="subject_w"/>文科 </label>
                    <label class="radius-comm"><input name="subject" type="radio" value="1" id="subject_l"/>理科 </label>
                </div>
                <div class="group-comm">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email<input type="text" class="user-input-comm mail" />
                </div>
                <div class="group-comm">
                    &nbsp;QQ号码<input type="text" class="user-input-comm qq"/>
                </div>
                <div class="btn btn-submit">提交</div>
            </div>
        </div>
    </div>
</div>

<%@include file="/common/footer.jsp"%>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="http://cdn.gaokao360.net/static/bower_components/uploadify/jquery.uploadify.js"></script>
<script>
//    seajs.use("/static/src/user/scripts/personal-info");
    seajs.use("http://cdn.gaokao360.net/static/global/user/scripts/personal-info");
    //头像上传
    setTimeout(function () {
        //初始化文件上传
        var errorCodes = ["-100", "-110", "-120", "-130"];
        var errorMsgs = ["文件数量不能超过(5)", "文件超过大小限制(10MB)", "零字节的文件", "无效的文件类型"];
        $("#uploadify").uploadify({
            'swf': "/static/src/user/scripts/uploadify.swf",
            'fileObjName': 'file',
            'uploader': "http://pre.file.xy189.cn/file/upload/savefile.shtml",
            'auto': true,
            'removeTimeout': 0,
            'multi': false,
            'uploadLimit': 0,
            'fileSizeLimit': "10MB",
            'fileTypeDesc': '图片文件(*.jpg;*.png;*.gif;*.jpeg)',
            'buttonText': '点击上传',
            'fileTypeExts': "*.jpg;*.png;*.gif;*.jpeg",
            'progressData': 'percentage',
            'speed': 'percentage',
            'queueSizeLimit': 5,
            'removeCompleted': true,
            'onSelect': function (file) {

                this.queueData.filesErrored = 0;
            },
            'onOpen': function (event, ID, fileObj) {
            },
            'onSelectError': function (file, errorCode, errorMsg) {

                for (var i = 0; i < errorCodes.length; i++) {
                    if (errorCodes[i] == errorCode) {
                        this.queueData.errorMsg = errorMsgs[i];
                    }
                }
            },
            'onCancel': function (file) {
                //alert(file.name);
            },
            'onFallback': function () {
                alert("浏览器不能兼容Flash,请下载最新版!");
            },
            'onClearQueue': function (queueItemCount) {
            },
            'onUploadStart': function (file) {
                console.log(file)
            },
            'onUploadSuccess': function (file, data, response) {
                //获取到data处理
                console.log(data);
                var obj = JSON.parse(data);
                var data = {'userIcon': obj.data.url};
                //$.ajax({
                //    type: "post",
                //    url: "/expert/updateUser.do",
                //    dataType: "json",
                //    data: data,
                //    async: false,
                //    success: function (res) {
                //        if (res.rtnCode !== '0000000') {
                //            alert(res.msg);
                //        } else {
                //            //修改成功
                //            console.info(res);
                //            $('.expert-avatar').find('.tips').html('更新成功').fadeOut(5000);
                //        }
                //    }
                //});

                $('.avatar-img').attr('src', obj.data.url);
                var id = this.wrapper.selector;
                $(id).uploadify('settings', 'buttonText', '正在加载');
                $("img[data-icon]").each(function () {
                    $(this).attr("src", obj.data.url + "!100?t=" + new Date().getTime());
                });
                $(id).uploadify('settings', 'buttonText', '换一个');
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                switch (errorMsg) {
                    case '400':
                        $('#' + file.id).find('.data').html(" - 上传失败，文件超过大小限制(2MB)");
                        break;
                    case '401':
                        $('#' + file.id).find('.data').html(" - 上传失败，零字节的文件");
                        break;
                    case '402':
                        $('#' + file.id).find('.data').html(" - 上传失败，无效的文件类型");
                        break;
                    case '500':
                        $('#' + file.id).find('.data').html(" - 上传失败，服务器问题");
                        break;
                }
            },
            'onDialogClose': function (queueDat) {
            }
        });
    }, 10);
</script>
</body>
</html>
