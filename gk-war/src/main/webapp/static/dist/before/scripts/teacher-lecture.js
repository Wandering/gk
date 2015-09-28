define(function (require) {
    var $ = require('$');
    require('swiper');
    require('backToTop');
    require('getTime');

    var UI = {
        $listMsgItem: $('#list-msg-item'),
        $nextPage: $('#nextPage')
    };
    var pageSize = 4;


    var detailsUrl = decodeURIComponent(window.location.search);
    var classifyType = detailsUrl.substr(14, 1);
    var num = detailsUrl.indexOf('&');
    var searchV = detailsUrl.substr(num + 9);

    console.log(classifyType)
    console.log(searchV)

    $('#searchVal').val(searchV);



    var localhosts = 'http://www.gkzy114.com';
    //
    // 搜索
    $('#search-btn').on('click',function(){
        window.location.href='/before/teacher-lecture.jsp?classifyType=1&searchV='+ encodeURIComponent($('#searchVal').val());
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
                        var subjectListHtml = '<option value="'+ tabId +'" data="'+ tabLi +'">'+ tabLi +'</option>';
                        $('.subjectList').append(subjectListHtml);
                    }
                }
            });
    }
    getSubjectList();
    //
    //
    function getList(pageNo, pageSize,sortType,subjectId,searchVals) {
        $.getJSON(
            "/before/video/getVideoList.do",
            {
                pageNo: pageNo,
                pageSize: pageSize,
                classifyType:1,
                sortType:sortType,
                subjectId:subjectId,
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
                        var subjectName = dataJson[i].title,
                            teacherName = dataJson[i].teacherName,
                            hit = dataJson[i].hit,
                            subcontent = dataJson[i].subcontent,
                            courseId = dataJson[i].courseId;
                        var detailsUrl = '/before/teacher-lecture-play.jsp?classifyType='+ classifyType +'&courseId=' + courseId;
                        var videoUrl='';
                        if (dataJson[i].frontCover == null || dataJson[i].frontCover == "") {
                            videoUrl = '/static/dist/common/images/video-default.png';
                        } else {
                            videoUrl = localhosts + dataJson[i].frontCover;
                        }
                        var listMsgHtml = ''
                            +'<li class="item">'
                            +'<div class="img"><img src="'+ videoUrl +'" alt=""/></div>'
                            +'<div class="info">'
                            +'<span class="fl">课程名称:'+ subjectName +'</span>'
                            +'<span class="fr">主讲专家:'+ teacherName +'</span>'
                            +'</div>'
                            +'<div class="num">'
                            +'<span class="fl">点击量:'+ hit +'</span>'
                            +'</div>'
                            +'<p class="txt">'+ subcontent +'</p>'
                            +'<div class="funs">'
                            +'<a target="_blank" href="'+ detailsUrl +'" class="btn">点击播放</a>'
                            +'</div>'
                            +'</li>';
                        UI.$listMsgItem.append(listMsgHtml);
                    }
                    pageNo++;
                    UI.$listMsgItem.attr('pageNo', pageNo);
                    if (dataJson.length < pageSize) {
                        UI.$nextPage.hide();
                    }
                }
            });
    }


    var searchs=$('#searchVal').val();

    UI.$nextPage.on('click', function () {
        var pageNo = UI.$listMsgItem.attr('pageNo');
        getList(pageNo, pageSize,1,"",searchs);
    }).click();
    //
    //
    // 科目筛选
    $(".subjectList").change(function(){
        var subjectId = $(this).find("option:selected").attr('value');
        var sortType = $('.subject-fun').find("option:selected").attr('value');
        UI.$listMsgItem.attr('pageNo',0);
        var pageNo = UI.$listMsgItem.attr('pageNo');
        UI.$listMsgItem.html('');
        getList(pageNo, pageSize,sortType,subjectId,searchs);
    });
    //
    //
    // 播放类型
    $(".subject-fun").change(function(){
        var sortType = $(this).find("option:selected").attr('value');
        var subjectId = $('.subjectList').find("option:selected").attr('value');
        UI.$listMsgItem.attr('pageNo',0);
        var pageNo = UI.$listMsgItem.attr('pageNo');
        UI.$listMsgItem.html('');
        getList(pageNo, pageSize,sortType,subjectId,searchs);
    });
    //







});
