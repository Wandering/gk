define(function (require) {
    var $ = require('$');
    require('swiper');
    require('getTime');
    require('backToTop');

    // 切换tab
    $('.tabs-list').on('click', 'li', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var subjectId = $(this).attr('id');
        var classifyType = $(this).parent().attr('classifyType');
        var subject = $(this).text();
        if (classifyType == '1') {
            getList('#teacher-lecture-main', subjectId, 1);
        }
        if (classifyType == '2') {
            getList('#mentality-main', subjectId, 2);
        }
        if (classifyType == '3') {
            getExamList('#exam-main', subjectId, subject);
        }
    });


    var localhosts = 'http://www.gkzy114.com';

    // 获取科目
    function getSubjectList() {
        $.getJSON(
            '/before/video/getSubjectList.do',
            function (result) {
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var tabLi = dataJson[i].subjectName,
                            tabId = dataJson[i].subjectId;
                        var subjectListHtml = '<li id="' + tabId + '">' + tabLi + '</li>';
                        $('.subjectList').append(subjectListHtml);
                    }
                    $('.subjectList').find('li:eq(0)').click();
                }
            });
    }

    getSubjectList();


    // 获取名师讲堂 & 高考心理
    function getList(obj, subjectId, classifyType, searchName) {
        $(obj).html('').hide();
        $.getJSON(
            '/before/video/getVideoList.do',
            {
                pageNo: 0,
                pageSize: 4,
                classifyType: classifyType,
                sortType: 1,
                subjectId: subjectId
            },
            function (result) {
                if (result.rtnCode == "0800001") {
                    $(obj).append('<p class="noContent">' + result.msg + '</p>').fadeIn(500);
                }
                if (result.rtnCode == "0000000") {

                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var subjectName = dataJson[i].subjectName,
                            teacherName = dataJson[i].teacherName,
                            courseId = dataJson[i].courseId,
                            hit = dataJson[i].hit,
                            subcontent = dataJson[i].subcontent;
                        var detailsUrl = '/before/teacher-lecture-play.jsp?classifyType=' + classifyType + '&courseId=' + courseId;
                        var videoUrl = '';
                        if (dataJson[i].frontCover == null || dataJson[i].frontCover == "") {
                            videoUrl = '/static/dist/common/images/video-default.png';
                        } else {
                            videoUrl = localhosts+dataJson[i].frontCover;
                        }
                        var listMsgHtml = ''
                            + '<li class="item">'
                            + '<div class="img"><img src="' + videoUrl + '" alt=""/></div>'
                            + '<div class="info">'
                            + '<span class="fl">学科名称:' + subjectName + '</span>'
                            + '<span class="fr">主讲专家:' + teacherName + '</span>'
                            + '</div>'
                            + '<div class="num">'
                            + '<span class="fl">点击量:' + hit + '</span>'
                            + '</div>'
                            + '<p class="txt">' + subcontent + '</p>'
                            + '<div class="funs">'
                            + '<a target="_blank" href="' + detailsUrl + '" class="btn">点击播放</a>'
                            + '</div>'
                            + '</li>';
                        $(obj).append(listMsgHtml).fadeIn(500);
                    }
                }
            });
    }


    //名师讲堂搜索
    $('#teacher-lecture-search-btn').on('click', function () {
        var teacherSearchName = $('#teacher-lecture-search-input').val();
        window.open('/before/teacher-lecture.jsp?teacherSearchName=' + teacherSearchName);
    });
    //真题密卷
    $('#exam-search-btn').on('click', function () {
        var examSearchName = $('#exam-search-input').val();
        window.open('/before/exam.jsp?examSearchName=' + examSearchName);
    });
    // 高考心理
    $('#mentality-search-btn').on('click', function () {
        var mentalitySearchName = $('#mentality-search-input').val();
        window.open('/before/mentality.jsp?mentalitySearchName=' + mentalitySearchName);
    });


    // 获取真题密卷
    function getExamList(obj, subjectId, subject) {
        $(obj).html('').hide();
        $.getJSON(
            '/before/paper/getPaperList.do',
            {
                pageNo: 0,
                pageSize: 8,
                years: "2014",
                sortType: 1,
                subjectId: subjectId
            },
            function (result) {
                if (result.rtnCode == "0800001") {
                    $(obj).append('<p class="noContent">' + result.msg + '</p>').fadeIn(500);
                }
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var paperName = dataJson[i].paperName,
                            createDate = dataJson[i].createDate,
                            resources = dataJson[i].resources;
                        var listMsgHtml = ''
                            + '<li class="item">'
                            + '<a target="_blank" href="' + resources + '">'
                            + '<span class="subject-n"><strong>' + subject + '</strong></span>'
                            + '<span class="subject-t">' + paperName + '</span>'
                            + '<span class="subject-d">上传时间：' + getTime(createDate) + '</span>'
                            + '</a>'
                            + '</li>';
                        $(obj).append(listMsgHtml).fadeIn(500);
                    }
                }
            });
    }


    //院校推荐
    $('#main-volunteer-tabs').on('click', 'li', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var index = $(this).index();
        $('.main-volunteer-box:eq(' + index + ')').show().siblings('.main-volunteer-box').hide();
    });
    // 院校评测提交
    $('#evaluating-sub').on('click', function () {
        $('.tansLayer,.evaluating-layer').show();
    });
    $('.evaluating-layer').on('click', '.close-btn', function () {
        $('.tansLayer,.evaluating-layer').hide();
    });
    // 院校推荐 锚点
    $('#volunteer-flow').on('click', function () {
        $('#main-volunteer-tabs li').eq(1).click();
        $('html,body').animate({scrollTop: ($('#main-volunteer-box').offset().top)}, 800);
    });



    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
    if (classifyType == '4') {
        $('#main-volunteer-tabs li').eq(1).click();
        $('html,body').animate({scrollTop: ($('#main-volunteer-box').offset().top)}, 800);
    }

});
