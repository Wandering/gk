<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>智高考专家后台</title>
    <%@ include file="../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management/experts-play.css">
    <link rel="stylesheet" type="text/css" href="http://www.bootcss.com/p/buttons/css/buttons.css">
    <style>
        .m-input {
            margin-bottom: 10px;
        }

        .u-input-name {
            display: inline-block;
            width: 85px;
        }

        .u-input {
            width: 500px;
            height: 25px;
            border: none;
            border-bottom: 1px solid #000;
            outline: none;
            color: #000;
        }

        .testBtn {
            width: 150px;
        }

        .u-status {
            margin-left: 10px;
            display: inline-block;
        }
    </style>
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
                        <div class="main-title">
                            <h3>一对一视频</h3>
                        </div>
                        <div class="fun-set">
                            <div class="m-input" style="display: none">
                                <span class="u-input-name">摄像头：</span>
                                <select class="u-input" id="cameraSelect">
                                </select>
                            </div>
                            <div class="m-input" style="display: none">
                                <span class="u-input-name">麦克风：</span>
                                <select class="u-input" id="microPhoneSelect">
                                </select>
                            </div>
                            <div class="m-input" style="display: none">
                                <span class="u-input-name">清晰度：</span>
                                <select class="u-input" id="qualitySelect">
                                    <option value="0">流畅（480*360@20）</option>
                                    <option value="1">标清（640*480@20）</option>
                                    <option value="2">高清（960*540@20）</option>
                                </select>
                            </div>
                            <div class="m-input" style="display: none">
                                <span class="u-input-name">推流地址：</span>
                                <input class="u-input" type="text" id="publishUrl">
                            </div>
                            <div class="m-input">
                                <%--<button class="button button-primary button-rounded testBtn" id="previewBtn"--%>
                                <%--onclick="startPreview()">预览--%>
                                <%--</button>--%>
                                <button class="button button-primary button-rounded testBtn" id="publishBtn"
                                        onclick="startPublish()">开始直播
                                </button>
                                <button class="button button-primary button-rounded testBtn" id="outChannelBtn">退出直播
                                </button>
                                <span class="u-status"></span>
                            </div>
                        </div>
                        <%-- 播放端开始 --%>
                        <link href="//nos.netease.com/vod163/nep.min.css" rel="stylesheet">
                        <style type="text/css">
                            .g-container-video {
                                margin: 30px auto;
                                overflow: hidden;
                                max-width: 640px;
                            }

                            .g-container-video h1 {
                                margin-bottom: 30px;
                                text-align: center;
                            }

                            .m-button-view {
                                text-align: center;
                            }

                            .g-container-code {
                                margin: 30px auto;
                                max-width: 960px;
                            }

                            .u-button {
                                width: 150px;
                                height: 50px;
                            }

                            .s-code-html {
                                height: 55px;
                            }

                            .s-code-js {
                                height: 70px;
                            }
                        </style>
                        <div class="body-main">
                            <div class="record-main">
                                <%-- 推流开始  --%>
                                <div class="publish-box">
                                    <div id="my-publisher"></div>
                                </div>
                                <%-- 推流结束  --%>
                            </div>
                            <div class="play-main">
                                <div id="neplayer"></div>
                                <script src="//nos.netease.com/vod163/nep.min.js"></script>
                                <%-- 播放端结束 --%>
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
<script src="//nos.netease.com/vod163/nePublisher.min.js"></script>
<script type="text/javascript">
    var expertsId = Common.cookie.getCookie('expertsId');
    var stuId = Common.getLinkey('stuId');
    function Channel() {
        this.init();
        this.cid = ''
        this.pushUrl = ''
        this.rtmpPullUrl = ''
    }
    ;

    Channel.prototype = {
        constructor: Channel,
        init: function () {
            this.getChannelStatus();
        },
        expertChannel: function (expertId, stuId, type) {
            var that = this;
            Common.ajaxFun('/expertChannel/createChannel.do', 'POST', {
                'expertId': expertId,
                'stuId': stuId,
                'type': type
            }, function (res) {
                if (res.rtnCode === '0000000') {
                    that.pushUrl = res.bizData.pushUrl;
                    that.cid = res.bizData.cid;
                }
            }, function (res) {

            }, true);
        },
        playChannel: function (expertId, stuId, type) {
            var that = this;
            Common.ajaxFun('/expertChannel/getChannel.do', 'get', {
                'expertId': expertId,
                'stuId': stuId,
                'type': type
            }, function (res) {
                if (res.rtnCode === '0000000') {
                    that.rtmpPullUrl = res.bizData.rtmpPullUrl;
                }
            }, function (res) {

            }, true);
        },
        outChannel: function (creatorId, cid) {
            Common.ajaxFun('/expertChannel/deleteChannel.do', 'get', {
                'creatorId': creatorId,
                'cid': cid
            }, function (res) {
                if (res.rtnCode === '0000000') {
                    window.location.href = window.location.pathname + '?stuId=' + stuId;
                }
            }, function (res) {

            });
        },
        getChannelStatus: function () {
            Common.ajaxFun('/expertChannel/getChannelStatus.do', 'get', {}, function (res) {
                if (res.rtnCode === '0000000') {
                    var status = res.bizData.status;
                    switch (status) {
                        case 0:
                            console.log('直播处于空闲');
                                $('.body-main').hide();
                            break;
                        case 1:
                            console.log('正在直播');
                            $('.body-main').show();
                            break;
                        case 2:
                            console.log('禁用');
                            $('.body-main').hide();
                            break;
                        case 3:
                            console.log('直播录制');
                            $('.body-main').hide();
                            break;
                        default:
                            break;
                    }
                }
            }, function (res) {

            });
        }
    };

    var ChannelIns = new Channel();


    $('#publishUrl').val(ChannelIns.expertChannel(expertsId, stuId, 0));

    $('#outChannelBtn').on('click', function () {
        ChannelIns.outChannel(expertsId, ChannelIns.cid);
    });

    var cameraList,
            microPhoneList,
            cameraOptions = '',
            microPhoneOptions = '';
    var publishBtn = document.getElementById('publishBtn');
    var previewBtn = document.getElementById('previewBtn')
    var testInput = document.getElementsByClassName('u-input');
    var myPublisher = new nePublisher('my-publisher', {
        //viewOptions
        videoWidth: 960,
        videoHeight: 540,
        fps: 20,
        bitrate: 1500
    }, {
        //flashOptions
        previewWindowWidth: 1020,
        previewWindowHeight: 486,
        wmode: 'transparent',
        quality: 'high',
        allowScriptAccess: 'always'
    }, function () {
        cameraList = this.getCameraList();
        microPhoneList = this.getMicroPhoneList();
        console.log(cameraList, microPhoneList);
        for (var i = cameraList.length - 1; i >= 0; i--) {
            cameraOptions = '<option value="' + i + '">' + cameraList[i] + '</option>' + cameraOptions;
        }
        for (var i = microPhoneList.length - 1; i >= 0; i--) {
            microPhoneOptions = '<option value="' + i + '">' + microPhoneList[i] + '</option>' + microPhoneOptions;
        }
        document.getElementById("cameraSelect").innerHTML = cameraOptions;
        document.getElementById("microPhoneSelect").innerHTML = microPhoneOptions;
    }, function (code, desc) {
        console.log(code, desc);
    });
    var qualityList = [
        {
            //流畅
            fps: 20,
            bitrate: 600,
            videoWidth: 480,
            videoHeight: 360
        },
        {
            //标清
            fps: 20,
            bitrate: 800,
            videoWidth: 640,
            videoHeight: 480
        },
        {
            //高清
            fps: 20,
            bitrate: 1500,
            videoWidth: 960,
            videoHeight: 540
        }
    ];
    var getCameraIndex = function () {
        var cameraSelect = document.getElementById("cameraSelect");
        var cameraIndex = cameraSelect.selectedIndex;
        return cameraSelect.options[cameraIndex].value;
    };
    var getMicroPhoneIndex = function () {
        var microPhoneSelect = document.getElementById("microPhoneSelect");
        var microPhoneIndex = microPhoneSelect.selectedIndex;
        return microPhoneSelect.options[microPhoneIndex].value;
    };
    var getQualityOption = function () {
        var qualitySelect = document.getElementById("qualitySelect");
        var qualityIndex = qualitySelect.selectedIndex;
        return qualityList[qualityIndex];
    };
    var startPreview = function () {
        myPublisher.startPreview(getCameraIndex());
        document.getElementsByClassName('u-status')[0].innerHTML = '预览中';
    };
    var startPublish = function () {
        var publishUrl = document.getElementById("publishUrl").value;
        startPublishCall();
        myPublisher.setCamera(getCameraIndex());
        myPublisher.setMicroPhone(getMicroPhoneIndex());
        myPublisher.startPublish(publishUrl, getQualityOption(), function (code, desc) {
            console.log(code, desc);
            alert(code + '：' + desc);
            stopPublishCall();
        });
    };
    var stopPublish = function () {
        myPublisher.stopPublish();
        stopPublishCall();
    };
    var startPublishCall = function () {
        console.log('推流开始');
        document.getElementsByClassName('u-status')[0].innerHTML = '直播中';
        publishBtn.innerHTML = '停止直播';
        publishBtn.onclick = stopPublish;
        for (var i = testInput.length - 1; i >= 0; i--) {
            testInput[i].disabled = true;
        }
        previewBtn.disabled = true;

    };
    var stopPublishCall = function () {
        console.log('推流结束');
        document.getElementsByClassName('u-status')[0].innerHTML = '预览中';
        publishBtn.innerHTML = '开始直播';
        publishBtn.onclick = startPublish;
        for (var i = testInput.length - 1; i >= 0; i--) {
            testInput[i].disabled = false;
        }
        previewBtn.disabled = false;
    };

    function showVideo(argument) {
        var container = document.getElementById('neplayer');
        container.innerHTML = '<video id="my-video" class="video-js" x-webkit-airplay="allow" webkit-playsinline controls poster="//nos.netease.com/vod163/poster.png" preload="auto" width="320" height="180" data-setup="{}"><source src="' + ChannelIns.playChannel(expertsId, stuId, 1) + '" type="rtmp/flv"></video>';
        neplayer('my-video', {
            "controls": false,
            "autoplay": true
        });
    }
    showVideo();
</script>
<script src="<%=ctx%>/static/src/js/info-management/experts-play.js"></script>
</body>
</html>
