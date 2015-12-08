define(function (require) {
    var $ = require('$');


    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
    var num = detailsUrl.indexOf("&");
    var courseId = detailsUrl.substr(num + 10);
    (classifyType == 1) ? (window.document.title = "名师学堂详情") : (window.document.title = "高考心理详情");



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
                    var urls = dataJson[0].fileUrl;
                    var videoHtml = ''
                        +'<video class="video" id="video" poster="http://media.html5media.info/poster.jpg" controls preload>'
                        +'<source id="source" src="'+urls+'" media="only screen and (min-device-width: 568px)"></source>'
                        +'</video>';
                    $('#videoHtml').html(videoHtml);
                }
            });
    }
    getList();
    $('#episode-num').on('click', 'a', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var fileurl = $(this).attr('fileurl');
        if (fileurl != "" || fileurl == null) {
            $(window).scrollTop(0);
            var videoHtml = ''
                + '<video class="video" poster="http://cdn.gaokao360.net/static/gx/before/images/poster.png" width="759" height="427" controls preload>'
                + '<source src="' + fileurl + '" media="only screen and (min-device-width: 568px)"></source>'
                + '</video>';
            $('#videoHtml').html(videoHtml);

        } else {
            $('.error-tips').text('您还不是VIP用户,请升级为VIP后在观看。').fadeIn(1000).fadeOut(2000);
        }
    });






});
