define(function (require) {
    var $ = require('$');
    require('swiper');


    // 切换tab
    $('.tabs-list').on('click','li',function () {
        $(this).addClass('active').siblings().removeClass('active');
        var n = $(this).index();
        $(this).parents('.tabs').next('.tabs-content').find('.tabs-content-list').hide();
        $(this).parents('.tabs').next('.tabs-content').find('.tabs-content-list:eq(' + n + ')').show()
    });

    // 获取科目
    $.getJSON(
        '/before/video/getSubjectList.do',
        function (result) {
              console.log(result)
            if (result.rtnCode == "0000000") {
                var dataJson = result.bizData;
                for (var i = 0; i < dataJson.length; i++) {
                    var tabLi = dataJson[i].subjectName,
                        tabId = dataJson[i].subjectId;
                    var subjectListHtml = '<li id="'+ tabId +'">'+ tabLi +'</li>';
                    $('.subjectList').append(subjectListHtml);
                }
            }
        });

    // 获取名师讲堂


    function getList(subjectId,classifyType){
        $.getJSON(
            '/before/video/getVideoList.do',
            {
                pageNo: 0,
                pageSize: 3,
                classifyType:classifyType,
                sortType:1,
                subjectId:subjectId
            },
            function (result) {
                console.log(result);
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.messages.length; i++) {
                        var listMsgHtml = ''
                    }
                }
            });
    }





});
