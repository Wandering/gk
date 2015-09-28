define(function (require) {
    var $ = require('$');

    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
    var num = detailsUrl.indexOf("&");
    var courseId = detailsUrl.substr(num + 10);
    (classifyType == 1)?(window.document.title = "名师学堂详情"):(window.document.title = "高考心理详情");

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
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var courseId = dataJson[i].courseId,
                            fileUrl = dataJson[i].fileUrl,
                            isAccept = dataJson[i].isAccept,
                            sectionName = dataJson[i].sectionName;
                        var listMsgHtml = '<a href="javascript:;" courseId="' + courseId + '" fileUrl="' + fileUrl + '">' + sectionName + '</a>';
                        $('#episode-num').append(listMsgHtml);
                    }
                    $('#episode-num').find('a:eq(0)').click();
                }
            });
    }

    getList();
    $('#episode-num').on('click', 'a', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var fileurl = $(this).attr('fileurl');
        var videoUrl ='vcastr_file='+localhosts+fileurl;
        console.log(videoUrl)
        $('#play-video').find('[name="FlashVars"]').attr('value','vcastr_file=/static/src/before/images/01.flv');
        $('#play-video').find('embed').attr('flashvars','vcastr_file=/static/src/before/images/01.flv');
    });


});
