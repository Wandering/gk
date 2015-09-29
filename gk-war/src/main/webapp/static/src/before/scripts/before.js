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
    function getList(obj, subjectId, classifyType) {
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
                            videoUrl = localhosts + dataJson[i].frontCover;
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
        window.open('/before/teacher-lecture.jsp?classifyType=1&searchV=' + teacherSearchName);
    });
    //真题密卷
    $('#exam-search-btn').on('click', function () {
        var examSearchName = $('#exam-search-input').val();
        window.open('/before/exam.jsp?searchV=' + examSearchName);
    });
    // 高考心理
    $('#mentality-search-btn').on('click', function () {
        var mentalitySearchName = $('#mentality-search-input').val();
        window.open('/before/mentality.jsp?classifyType=2&searchV=' + mentalitySearchName);
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
    // 院校推荐
    $('#yxtj-sub').on('click', function () {
        var scoreV = $('#score-input').val().trim();
        var batchV = $('input[name="batch"]:checked').val();
        var subjectTypeV = $('input[name="subjectType"]:checked').val();
        if (scoreV == '') {
            $('.error-tips').text('请输入分数').fadeIn(1000).fadeOut(1000);
            return false;
        }
        if (batchV == undefined) {
            $('.error-tips').text('请选择批次').fadeIn(1000).fadeOut(1000);
            return false;
        }
        if (subjectTypeV == undefined) {
            $('.error-tips').text('请选择文理科').fadeIn(1000).fadeOut(1000);
            return false;
        }
        $.ajax({
            url: '/before/collegeRecommend/getCollegeList.do',
            type: 'GET',
            dataType: 'JSON',
            data: {
                "m_aggregateScore": scoreV,
                "m_batch": batchV,
                "m_kelei": subjectTypeV
            },
            success: function (res) {
                //console.log(res)
                if (res.rtnCode == "0000000") {
                    $('#volunteer-flow3-layer,.tansLayer').show();
                    $('#score-num').text(scoreV+"分");
                    $('#batchV').text(batchV);
                    $('#subjectTypeV').text(subjectTypeV);
                    var dataJson = res.bizData.result.data;
                    for (var i = 0; i < dataJson.length; i++) {
                        if (dataJson[i].status == 0) {
                            $('#no-school' + i).show();
                        } else {
                            var schoolData = dataJson[i].data;
                            for (var j = 0; j < schoolData.length; j++) {
                                var m_university_code = schoolData[j].m_university_code;
                                var m_university_name = schoolData[j].m_university_name;
                                var schoolList = '<div><a target="_blank" href="/consult/school_detile.jsp?id='+ m_university_code +'">'+ m_university_name +'</a></div>';
                                $('#school-list'+i).append(schoolList).show();
                            }
                        }
                    }
                }
            }
        });
    })

    $('#volunteer-flow3-layer').on('click','.close-btn',function(){
        $('#volunteer-flow3-layer,.tansLayer').hide();
    });



    // 验证码
    //$.ajax({
    //    url: '/verification/code/randomVerifyCode.do',
    //    type: 'GET',
    //    data: {
    //        type:2
    //    },
    //    success: function (res) {
    //        console.log(res)
    //    }
    //});






    $('#yzmDreamSchool').on('click',function(){
        $('#yzmDreamSchool').attr('src','/verification/code/randomVerifyCode.do?type=2&code=' + Math.random());
    });
    function RefreshCode(obj){
        obj.src = obj.src + "?code=" + Math.random();
    }
    $('#yzmDreamSchool').attr('src','/verification/code/randomVerifyCode.do?type=2');





    // 院校测评
    $('#evaluating-sub').on('click', function () {
        var dreamScoreV = $('#dream-score-input').val().trim();
        var dreamSchoolV = $('#dream-school-input').val().trim();
        var dreamSubjectTypeV = $('input[name="dreamSubjectType"]:checked').val();
        var yzmDreamV = $('#yzmDream').val();
        if (dreamScoreV == '') {
            $('.error-tips').text('请输入分数').fadeIn(1000).fadeOut(1000);
            return false;
        }
        if (dreamSchoolV == '') {
            $('.error-tips').text('请输入院校').fadeIn(1000).fadeOut(1000);
            return false;
        }
        if (dreamSubjectTypeV == undefined) {
            $('.error-tips').text('请选择文理科').fadeIn(1000).fadeOut(1000);
            return false;
        }
        if (yzmDreamV == '') {
            $('.error-tips').text('请填写验证码').fadeIn(1000).fadeOut(1000);
            return false;
        }
        $.ajax({
            url: '/appraisal/schoolTest.do',
            type: 'GET',
            dataType: 'JSON',
            data: {
                "m_aggregateScore": dreamScoreV,
                "m_university_name": dreamSchoolV,
                "m_kelei": dreamSubjectTypeV
            },
            success: function (res) {
                var data = $.parseJSON(res.bizData);
                console.log(data);
                if (res.rtnCode == "0000000") {
                    $('#dream-school-layer,.tansLayer').show();
                     $('#dreamScoreInfo').text(dreamScoreV);
                     $('#dreamSubjectTypeInfo').text(dreamSubjectTypeV);
                     $('#dreamSchoolInfo').text(dreamSchoolV);

                    for(var i=0;i<data.data.length;i++){
                        var dreamSchoolList = ''
                            +'<ul>'
                            +'<li class="pc">三批本科</li>'
                            +'<li class="result1">'
                            +'<span class="t">所需最低分数</span>'
                            +'<span class="num"><strong>639</strong>分</span>'
                            +'</li>'
                            +'<li class="result2">'
                            +'<span class="t">所需平均分数</span>'
                            +'<span class="num"><strong>652</strong>分</span>'
                            +'</li>'
                            +'</ul>';
                         $('#dream-list').append(dreamSchoolList);
                    }

                }
            }
        });
    });

    $('#dream-school-layer').on('click','.close-btn',function(){
        $('#volunteer-flow3-layer,.tansLayer').hide();
    })




});
