define(function (require) {
    var $ = require('$');

    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14,1);
    var num = detailsUrl.indexOf("&");
    var courseId = detailsUrl.substr(num+10);


    console.log(classifyType)



    // 获取章节列表
    function getList(){
        $.getJSON(
            '/before/video/getVideoSectionList.do',
            {
                courseId: courseId
            },
            function (result) {
                  console.log(result)
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
                        var listMsgHtml = '<a href="javascript:;" courseId="'+ courseId +'" fileUrl="'+ fileUrl +'">'+ sectionName  +'</a>';
                        $('#episode-num').append(listMsgHtml);
                    }
                    $('#episode-num').find('a:eq(0)').click()
                }
            });
    }
    getList();

    $('#episode-num').on('click','a',function () {
        $(this).addClass('active').siblings().removeClass('active');
    });


});
