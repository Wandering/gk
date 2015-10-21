define(function (require) {
    var $ = require('$');

    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
    var num = detailsUrl.indexOf("&");
    var courseId = detailsUrl.substr(num + 10);
    (classifyType == 1) ? (window.document.title = "名师学堂详情") : (window.document.title = "高考心理详情");

    var localhosts = 'http://www.gkzy114.com';


    // 获取章节列表
    function getList() {
        $.getJSON(
            '/before/video/getVideoSectionList.do',
            {
                courseId: courseId
            },
            function (result) {
                if (result.rtnCode == "0800001") {
                    $(obj).append('<p class="noContent">' + result.msg + '</p>');
                }
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData.videoSectionList;
                    var videoCourse = result.bizData.videoCourse;
                    if (videoCourse) {
                        $('#title').html(videoCourse.teacher + '<span>' + videoCourse.title + '</span>');
                        $('#subcontent').html(videoCourse.subcontent);
                    }
                    for (var i = 0; i < dataJson.length; i++) {
                        var courseId = dataJson[i].courseId,
                            fileUrl = dataJson[i].fileUrl,
                            isAccept = dataJson[i].isAccept,
                            sectionName = dataJson[i].sectionName;

                        var  listMsgHtml = '';
                        if(isAccept==1){
                            listMsgHtml += '<a href="javascript:;" courseId="' + courseId + '" fileUrl="' + fileUrl + '">' + sectionName + '</a>';
                        }else{
                            listMsgHtml += '<a href="javascript:;" courseId="' + courseId + '" fileUrl="">' + sectionName + '</a>';
                        }
                        $('#episode-num').append(listMsgHtml);
                    }
                    $('#episode-num').find('a:eq(0)').click();
                    var firstFileurl = $('#episode-num').find('a:eq(0)').attr('fileurl');
                    console.log(firstFileurl);
                    $('#player').attr('href', localhosts + firstFileurl);
                    var api = flowplayer(
                        "player",
                        "/static/src/guide/scripts/flowplayer-3.2.18.swf",
                        {
                            clip: {
                                autoPlay: false,       //是否自动播放，默认true
                                autoBuffering: false     //是否自动缓冲视频，默认true
                            }
                        }
                    );
                }
            });
    }
    getList();

    //$.get('/before/video/getVideoSectionList.do?courseId=' + courseId, function(data) {
    //    if ('0000000' === data.rtnCode) {
    //        var videoCourse = data.bizData.videoCourse;
    //        if (videoCourse) {
    //            $('#title').html(videoCourse.teacher + '<span>' + videoCourse.title + '</span>');
    //            $('#subcontent').html(videoCourse.subcontent);
    //        }
    //    }
    //});
    $('#episode-num').on('click', 'a', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var fileurl = $(this).attr('fileurl');
        if(fileurl!="" || fileurl==null){
            $(window).scrollTop(0);
            $('#player').attr('href', localhosts + fileurl);
            var api = flowplayer(
                "player",
                "/static/src/guide/scripts/flowplayer-3.2.18.swf"
            );
        }else{
            $('.error-tips').text('您还不是VIP用户,请升级为VIP后在观看。').fadeIn(1000).fadeOut(2000);
        }
    });


});
