define(function (require) {
    var $ = require('$');
    require('swiper');
    require('getTime');
    require('backToTop');
    var pageEroorTip = require('pageErrorTip');

    var UI = {
        $tabsList: $('#tabs-list'),
        $evaluatingSub: $('#evaluating-sub')
    };
    var pattern = /^[0-9]*[1-9][0-9]*$/;

    // 警告提示
    function errorTips(txt) {
        $('.error-tips').text(txt).fadeIn(1000).fadeOut(1000);
    }


    // 切换tab
    UI.$tabsList.on('click', 'li', function () {
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
                    //$(obj).append('<p class="noContent">' + result.msg + '</p>').fadeIn(500);
                    $(obj).append(pageEroorTip('暂时没有数据，请耐心等待哦')).fadeIn(500);
                }
                if (result.rtnCode == "0000000") {

                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var subjectName = dataJson[i].subjectName || '',
                            teacherName = dataJson[i].teacherName || '',
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

                        var tmpContent = subcontent;
                        if (tmpContent.length > 150) {
                            tmpContent = tmpContent.substring(0, 150) + '...';
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
                            + '<p class="txt">' + tmpContent + '</p>'
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
                    //$(obj).append('<p class="noContent">' + result.msg + '</p>').fadeIn(500);
                    $(obj).append(pageEroorTip('暂时没有数据，请耐心等待哦')).fadeIn(500);
                }
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var paperName = dataJson[i].paperName,
                            createDate = dataJson[i].createDate,
                            resources = dataJson[i].resources;
                        var listMsgHtml = ''
                            + '<li class="item">'
                            + '<a target="_blank" href="' + localhosts + resources + '">'
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
    $('#volunteer-flow, #volunteer-flow1').on('click', function () {
        $('#main-volunteer-tabs li').eq(1).click();
        $('html,body').animate({scrollTop: ($('#main-volunteer-box').offset().top)}, 800);
    });
    var detailsUrl = window.location.search;
    var classifyType = detailsUrl.substr(14, 1);
    if (classifyType == '4') {
        $('#main-volunteer-tabs li').eq(1).click();
        $('html,body').animate({scrollTop: ($('#main-volunteer-box').offset().top)}, 800);
    }

    $('#college-yzm').on('click', function () {
        $('#college-yzm').attr('src', '/verifyCode/randomVerifyCode.do?type=1&code=' + Math.random());
    }).attr('src', '/verifyCode/randomVerifyCode.do?type=1');


    // 院校推荐
    $('#yxtj-sub').on('click', function () {
        var scoreV = $('#score-input').val().trim()
            , batchV = $('input[name="batch"]:checked').val()
            , subjectTypeV = $('input[name="subjectType"]:checked').val()
            , yzmDreamV = $('#yzmCollege').val().trim();
        if (scoreV == '') {
            errorTips('请输入分数');
            return false;
        }
        if (pattern.test(scoreV) == false || scoreV.length != 3) {
            errorTips('请输入3位数字分数');
            return false;
        }
        if (batchV == undefined) {
            errorTips('请选择批次');
            return false;
        }
        if (subjectTypeV == undefined) {
            errorTips('请选择文理科');
            return false;
        }
        if (yzmDreamV == '') {
            errorTips('请填写验证码');
            return false;
        }
        $.ajax({
            url: '/before/collegeRecommend/getCollegeList.do',
            type: 'GET',
            dataType: 'JSON',
            data: {
                "m_aggregateScore": scoreV,
                "m_batch": batchV,
                "m_kelei": subjectTypeV,
                "code": yzmDreamV
            },
            success: function (res) {
                console.log(res)
                if (res.rtnCode == "0100006" || res.rtnCode == "1000004" || res.rtnCode == "0100005") {
                    errorTips(res.msg);
                    return false;
                }
                if (res.rtnCode == "0000000") {
                    $('.school-list').html('');
                    $('#volunteer-flow3-layer,.tansLayer').show();
                    $('#volunteer-flow3-layer .no-school').hide();
                    $('#score-num').text(scoreV + "分");
                    $('#batchV').text(batchV);
                    $('#subjectTypeV').text(subjectTypeV);
                    var data = $.parseJSON(res.bizData);
                    var dataJson = data.result.data;

                    if (!dataJson) {
                        errorTips(res.msg);
                        return false;
                    }
                    for (var i = 0; i < dataJson.length; i++) {
                        if (dataJson[i].status == 0) {
                            $('#no-school' + i).show();
                        } else {
                            var schoolData = dataJson[i].data;
                            for (var j = 0; j < schoolData.length; j++) {
                                var m_university_code = schoolData[j].m_university_code;
                                var m_university_name = schoolData[j].m_university_name;
                                var schoolList = '<div><a target="_blank" href="/consult/school_detile.jsp?id=' + m_university_code + '">' + m_university_name + '</a></div>';
                                $('#school-list' + i).append(schoolList).show();
                            }
                        }
                    }
                }
            }
        });
    })

    $('#volunteer-flow3-layer').on('click', '.close-btn', function () {
        $('#volunteer-flow3-layer,.tansLayer').hide();
        $('#college-yzm').attr('src', '/verifyCode/randomVerifyCode.do?type=1&code=' + Math.random());
    });


    $('#dreanSchoolBtn').on('click', function () {
        $('#volunteer-flow3-layer,.tansLayer').hide();
        $('#college-yzm').attr('src', '/verifyCode/randomVerifyCode.do?type=1&code=' + Math.random());
        $('#main-volunteer-tabs li:eq(0)').click();
    });


    // 院校评测验证码
    $('#yzmDreamSchool').on('click', function () {
        $('#yzmDreamSchool').attr('src', '/verifyCode/randomVerifyCode.do?type=2&code=' + Math.random());
    }).attr('src', '/verifyCode/randomVerifyCode.do?type=2');

    // 院校测评
    UI.$evaluatingSub.on('click', function () {
        var dreamScoreV = $('#dream-score-input').val().trim()
            , dreamSchoolV = $('#dream-school-input').val().trim()
            , dreamSubjectTypeV = $('input[name="dreamSubjectType"]:checked').val()
            , yzmDreamV = $('#yzmDream').val();
        if (dreamScoreV == '') {
            errorTips('请输入分数');
            return false;
        }
        if (pattern.test(dreamScoreV) == false || dreamScoreV.length != 3) {
            errorTips('请输入3位数字分数');
            return false;
        }
        if (dreamSchoolV == '') {
            errorTips('请输入院校');
            return false;
        }
        if (dreamSchoolV.length > 50) {
            errorTips('院校名称最多50个字');
            return false;
        }
        if (dreamSubjectTypeV == undefined) {
            errorTips('请选择文理科');
            return false;
        }
        if (yzmDreamV == '') {
            errorTips('请填写验证码');
            return false;
        }
        $.ajax({
            url: '/appraisal/schoolTest.do',
            type: 'GET',
            dataType: 'JSON',
            data: {
                "m_aggregateScore": dreamScoreV,
                "m_university_name": dreamSchoolV,
                "m_kelei": dreamSubjectTypeV,
                "code": yzmDreamV
            },
            success: function (res) {
                if (res.rtnCode == "1000006" || res.rtnCode == "1000004") {
                    errorTips(res.msg);
                    return false;
                }
                var data = $.parseJSON(res.bizData);
                console.log(data)
                if (!data) {
                    errorTips(res.msg);
                    return false;
                }

                if (res.rtnCode == "0000000") {
                    $('#dream-list').html('');
                    if(data.data.length==0){
                        errorTips('暂无数据,请检查输入信息');
                        return false;
                    }
                    $('#dream-school-layer,.tansLayer').show();
                    $('#dreamScoreInfo').text(dreamScoreV);
                    $('#dreamSubjectTypeInfo').text(dreamSubjectTypeV);
                    $('#dreamSchoolInfo').text(dreamSchoolV);

                    for (var i = 0; i < data.data.length; i++) {
                        var m_averagescores = data.data[i].m_averagescores;
                        var m_batch = data.data[i].m_batch;
                        var m_lowestscore = data.data[i].m_lowestscore;
                        var dreamSchoolList = ''
                            + '<ul>'
                            + '<li class="pc">' + m_batch + '</li>'
                            + '<li class="result1">'
                            + '<span class="t">所需最低分数</span>'
                            + '<span class="num"><strong>' + m_lowestscore + '</strong>分</span>'
                            + '</li>'
                            + '<li class="result2">'
                            + '<span class="t">所需平均分数</span>'
                            + '<span class="num"><strong>' + m_averagescores + '</strong>分</span>'
                            + '</li>'
                            + '</ul>';
                        $('#dream-list').append(dreamSchoolList);
                    }

                }
            }
        });
    });

    $('#dream-school-layer').on('click', '.close-btn', function () {
        $('#dream-school-layer,.tansLayer').hide();
        $('#yzmDreamSchool').attr('src', '/verifyCode/randomVerifyCode.do?type=2&code=' + Math.random());
    });

    $('#precedence-yzmDreamSchool').on('click', function () {
        $('#precedence-yzmDreamSchool').attr('src', '/verifyCode/randomVerifyCode.do?type=3&code=' + Math.random());
    }).attr('src', '/verifyCode/randomVerifyCode.do?type=3');

    //获得位次
    $('#precedence-sub').on('click', function (e) {

        var dreamScoreV = $('#precedence-score-input').val().trim()
            , yzmDreamV = $('#precedence-yzmDream').val();
        if (dreamScoreV == '') {
            errorTips('请输入分数');
            return false;
        }
        if (pattern.test(dreamScoreV) == false || dreamScoreV.length != 3) {
            errorTips('请输入3位数字分数');
            return false;
        }
        if (yzmDreamV == '') {
            $('.error-tips').text('请填写验证码').fadeIn(1000).fadeOut(1000);
            return false;
        }
        $.ajax({
            url: '/appraisal/findRanking.do',
            type: 'GET',
            dataType: 'JSON',
            data: {
                "m_aggregateScore": dreamScoreV,
                "code": yzmDreamV
            },
            success: function (res) {
                if (res.rtnCode == "1000006" || res.rtnCode == "1000004") {
                    $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);
                    return;
                }
                var data = $.parseJSON(res.bizData);
                if (!data) {
                    $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);
                    return;
                }
                if (res.rtnCode == "0000000") {
                    $('#precedence-list').html('');
                    $('#precedence-school-layer,.tansLayer').show();
                    $('#precedenceScoreInfo').text(dreamScoreV);
                    $('#current-year').text(data.m_years);
                    var dreamSchoolList = ''
                        + '<ul>'
                        + '<li class="result1">'
                        + '<span class="t">文史类</span>'
                        + '<span class="num"><b>' + (data.m_ws_ranking || '') + '</b>位</span>'
                        + '</li>'
                        + '<li class="result2">'
                        + '<span class="t">理工类</span>'
                        + '<span class="num"><b>' + (data.m_lg_ranking || '') + '</b>位</span>'
                        + '</li>'
                        + '</ul>';
                    $('#precedence-list').append(dreamSchoolList);

                    $('#confirm').on('click', function (e) {
                        $('#precedence-school-layer,.tansLayer').hide();
                        $('#precedence-yzmDreamSchool').attr('src', '/verifyCode/randomVerifyCode.do?type=3&code=' + Math.random());
                    });

                }
            }
        });
    });

    $('#precedence-school-layer').on('click', '.close-btn', function () {
        $('#precedence-school-layer,.tansLayer').hide();
        $('#precedence-yzmDreamSchool').attr('src', '/verifyCode/randomVerifyCode.do?type=3&code=' + Math.random());
    });


});
