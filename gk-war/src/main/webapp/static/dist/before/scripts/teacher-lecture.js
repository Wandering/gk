define(function (require) {
    var $ = require('$');
    require('swiper');
    require('backToTop');
    require('getTime');



    var UI = {
        $listMsgItem: $('#list-msg-item'),
        $nextPage: $('#nextPage')
    };
    var pageSize = 8;
    var searchValUrl = window.location.search;
    var num = searchValUrl.indexOf("?");
    var searchVal = searchValUrl.substr(num+19);
    $('#searchVal').val(searchVal);

    var localhosts = 'http://www.gkzy114.com';

    // 搜索
    $('#search-btn').on('click',function(){
        window.location.href='/before/teacher-lecture.jsp?teacherSearchName='+ $('#searchVal').val()
    });






    // 获取科目
    function getSubjectList(){
        $.getJSON(
            '/before/video/getSubjectList.do',
            function (result) {
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var tabLi = dataJson[i].subjectName,
                            tabId = dataJson[i].subjectId;
                        var subjectListHtml = '<option value="'+ tabId +'">'+ tabLi +'</option>';
                        $('.subjectList').append(subjectListHtml);
                    }
                }
            });
    }
    getSubjectList();

    var searchVals = $('#searchVal').val();
    function getList(pageNo, pageSize,searchVals) {

        $.getJSON(
            "/before/video/getVideoList.do",
            {
                pageNo: 0,
                pageSize: 4,
                classifyType:1,
                sortType:1,
                subjectId:'',
                teacherSearchName:searchVals
            },
            function (result) {
                console.log(result);
                if (result.rtnCode == "0800001") {
                    UI.$listMsgItem.append('<p class="noContent">' + result.msg + '</p>');
                    UI.$nextPage.hide();
                }
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    if(dataJson.length>0){
                        UI.$nextPage.show();
                    }

                    for (var i = 0; i < dataJson.length; i++) {
                        var subjectName = dataJson[i].subjectName,
                            teacherName = dataJson[i].teacherName,
                            hit = dataJson[i].hit,
                            subcontent = dataJson[i].subcontent;
                        var videoUrl='';
                        if (dataJson[i].frontCover == null || dataJson[i].frontCover == "") {
                            videoUrl = '/static/dist/common/images/video-default.png';
                        } else {
                            videoUrl = localhosts + dataJson[i].frontCover;
                        }
                        var listMsgHtml = ''
                            +'<li class="item">'
                            +'<div class="img"><img src="'+ frontCover +'" alt=""/></div>'
                            +'<div class="info">'
                            +'<span class="fl">学科名称:'+ subjectName +'</span>'
                            +'<span class="fr">主讲专家:'+ teacherName +'</span>'
                            +'</div>'
                            +'<div class="num">'
                            +'<span class="fl">点击量:'+ hit +'</span>'
                            +'</div>'
                            +'<p class="txt">'+ subcontent +'</p>'
                            +'<div class="funs">'
                            +'<a href="" class="btn">点击播放</a>'
                            +'</div>'
                            +'</li>';
                        UI.$listMsgItem.append(listMsgHtml);
                    }
                    pageNo++;
                    UI.$listMsgItem.attr('pageNo', pageNo);
                    if (dataJson.length < pageSize) {
                        UI.$nextPage.removeClass('btn-primary').text("没有更多消息了")
                    }
                }
            });
    }

    UI.$nextPage.on('click', function () {
        var pageNo = UI.$listMsgItem.attr('pageNo');
        getList(pageNo, pageSize,searchVal);
    }).click();





});
