define(function (require) {
    var $ = require('$');

    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
    var num = detailsUrl.indexOf("&");
    var courseId = detailsUrl.substr(num + 10);
    (classifyType == 1) ? (window.document.title = "名师学堂详情") : (window.document.title = "高考心理详情");

    //var localhosts = 'http://www.gkzy114.com';


    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    if (GetCookie("gxuser")) {
        $('#logoutStatus').hide();
        $('#videoHtml').show();
    } else {
        $('#logoutStatus').show();
        $('#videoHtml').hide();
    }


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

                        var listMsgHtml = '';
                        if (isAccept == 1) {
                            listMsgHtml += '<a href="javascript:;" courseId="' + courseId + '" fileUrl="' + fileUrl + '">' + sectionName + '</a>';
                        } else {
                            listMsgHtml += '<a href="javascript:;" courseId="' + courseId + '" fileUrl="">' + sectionName + '</a>';
                        }
                        $('#episode-num').append(listMsgHtml);
                    }
                    $('#episode-num').find('a:eq(0)').click();
                    var firstFileurl = $('#episode-num').find('a:eq(0)').attr('fileurl');
                    console.log(firstFileurl);

                    var videoHtml = ''
                        + '<video class="video" poster="//media.html5media.info/poster.jpg" width="759" height="427" controls preload>'
                        + '<source src="' + firstFileurl + '" media="only screen and (min-device-width: 568px)"></source>'
                        + '</video>';
                    $('#videoHtml').html(videoHtml);

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
        if (fileurl != "" || fileurl == null) {
            $(window).scrollTop(0);

            var videoHtml = ''
                + '<video class="video" poster="//media.html5media.info/poster.jpg" width="618" height="347" controls preload>'
                + '<source src="' + fileurl + '" media="only screen and (min-device-width: 568px)"></source>'
                + '</video>';
            $('#videoHtml').html(videoHtml);

        } else {
            $('.error-tips').text('您还不是VIP用户,请升级为VIP后在观看。').fadeIn(1000).fadeOut(2000);
        }
    });


});
