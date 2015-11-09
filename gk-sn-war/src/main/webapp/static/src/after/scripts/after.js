define(function (require) {
    var $ = require('$');
    require('backToTop');
    $(function () {
        var UI = {
            yzmDreamSchool: $('#yzmDreamSchool'),
            volunteerBtn: $('#volunteer-flow1-btn'),
            schoolListinfo : $('#school-list-info')
        };

        // 验证码
        function validateCode(obj) {
            obj.on('click', function () {
                obj.attr('src', '/verifyCode/randomVerifyCode.do?type=4&code=' + Math.random());
            }).attr('src', '/verifyCode/randomVerifyCode.do?type=4');
        };
        validateCode(UI.yzmDreamSchool);

        // 警告提示
        function errorTips(txt) {
            $('.error-tips').text(txt).fadeIn(1000).fadeOut(1000);
        }

        function GetCookie(sMainName, sSubName) {
            var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
            return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
        }


        //判断当前用户cookie是否存在
        //if (GetCookie("snuser")) {
        //    $.ajax({
        //        url: '/info/getUserAccount.do',
        //        type: 'GET',
        //        dataType: 'JSON',
        //        success: function (res) {
        //            console.log("获取用户信息:");
        //            console.log(res);
        //            if(res.rtnCode=="0000000" && res.bizData.isReported!=null){
        //                if (res.bizData.isReported == 0) {
        //                    $('#aggregateScore-input').removeAttr('readonly').val('');
        //                    $('#ranking-input').removeAttr('readonly').val('');
        //                } else {
        //                    $('#aggregateScore-input').attr('readonly', 'readonly');
        //                    $('#ranking-input').attr('readonly', 'readonly');
        //                }
        //            }
        //
        //
        //        }
        //    });
        //}


        // 志愿指导第一步
        UI.volunteerBtn.on('click', function () {
            var candidateNumberV = $('#candidateNumber-input').val().trim()
                , aggregateScoreV = $('#aggregateScore-input').val().trim()
                , rankingV = $('#ranking-input').val().trim()
                , subjectTypeV = $('input[name="subjectType"]:checked').val()
                , yzmDreamV = $('#yzmDreamSchool-input').val().trim()
                , pattern = /^[0-9]*[1-9][0-9]*$/;
            if (candidateNumberV == '') {
                errorTips('请输入考号');
                return false;
            }
            if (pattern.test(candidateNumberV) == false || candidateNumberV.length != 10) {
                errorTips('请输入10位数字考号');
                return false;
            }

            if (aggregateScoreV == '') {
                errorTips('请输入分数');
                return false;
            }
            if (pattern.test(aggregateScoreV) == false || aggregateScoreV.length != 3) {
                errorTips('请输入3位数字分数');
                return false;
            }
            if (rankingV == '') {
                errorTips('请输入位次');
                return false;
            }
            if (pattern.test(rankingV) == false || rankingV.length > 6) {
                errorTips('请输入小于6位数字位次');
                return false;
            }
            if (subjectTypeV == undefined) {
                errorTips('选择文理科');
                return false;
            }
            if (yzmDreamV == '') {
                errorTips('请填写验证码');
                return false;
            }

            $.ajax({
                url: '/guide/batch.do',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    "m_candidateNumber": candidateNumberV,
                    "m_aggregateScore": aggregateScoreV,
                    "m_ranking": rankingV,
                    "m_kelei": subjectTypeV,
                    "code": yzmDreamV
                },
                success: function (res) {
                    //console.log(res)
                    if (res.rtnCode == "0100006" || res.rtnCode == "1000004" || res.rtnCode == "0100005") {
                        errorTips(res.msg);
                        return false;
                    }
                    if (res.rtnCode == "0000000") {
                        UI.schoolListinfo.html('');
                        var data = $.parseJSON(res.bizData);
                        //console.log(res);
                        //console.log(data.data);
                        //console.log(data.related);
                        if(!data.data){
                            errorTips("该分数暂无数据");
                            return false;
                        }
                        $('#volunteer-flow1').hide();
                        $('#volunteer-flow2').show();
                        $('#scoresNum').text(aggregateScoreV + "分");
                        $('#subType').text(subjectTypeV + "类");
                        $.each(data.data, function (i, v) {
                            $('#batch').text(data.data[0].m_batch);
                            var params = {
                                "m_candidateNumber": candidateNumberV,
                                "m_aggregateScore": aggregateScoreV,
                                "m_ranking": rankingV,
                                "m_kelei": subjectTypeV,
                                "m_batch_id": v.m_batch_id,
                                "m_batch": v.m_batch,
                                "m_province_id": "",
                                "m_province": "陕西",
                                "m_specialty_id": "",
                                "m_specialty_name": "",
                                "m_favorites_by_university_codes": ""
                            };
                            var jsons = JSON.stringify(params);
                            var schoolListHtml = ''
                                + '<div class="info2">'
                                + '<h3>普通' + v.m_batch + '院校</h3>'
                                + v.m_years_fl + '：<strong>' + v.m_liberalarts + '分</strong>，<strong>' + v.m_science + '分</strong>'
                                + '<form id="forwardForm" method="post" action="/forward.do">'
                                + '<input type="hidden" name="params" value="' + encodeURIComponent(jsons) + '" />'
                                + '<input type="hidden" name="url" value="/after/volunteer-flow3.jsp" />'
                                + '<input type="submit" class="btn" value="开始"></a>'
                                + '</form>'
                                + '</div>';
                            UI.schoolListinfo.append(schoolListHtml);
                        });
                    }
                    //在VIP用户下第一次提交考后报考
                    $.ajax({
                        url: '/exam/updateUserExam.do',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            "scores": aggregateScoreV,
                            "ranking": rankingV,
                            "isReported": 1
                        },
                        success: function (res) {
                            console.log("在VIP用户下第一次提交考后报考");
                            console.log(res);
                            if(res.rtnCode=="0000000"){
                                console.log('成功');
                            }


                        }
                    });

                }
            });

        });

        // 获取用户是否提交过考后报考
        function findUserExam(){
            $.ajax({
                url: '/exam/findUserExam.do',
                type: 'GET',
                dataType: 'JSON',
                data: {},
                success: function (res) {
                    console.log("用户是否提交过考后报考");
                    console.log(res);
                    //console.log(res.bizData.isReported)
                    var scores = res.bizData.scores;
                    var ranking = res.bizData.ranking;
                    $('#aggregateScore-input').val(scores);
                    $('#ranking-input').val(ranking);
                    if (res.bizData.isReported == 1) {
                        $('#aggregateScore-input').attr('readonly', 'readonly');
                        $('#ranking-input').attr('readonly', 'readonly');
                    } else {
                        $('#aggregateScore-input').removeAttr('readonly').val('');
                        $('#ranking-input').removeAttr('readonly').val('');
                    }

                }
            });
        }

        findUserExam();


        $('#prev-btn').on('click', function () {
            $('#volunteer-flow1').show();
            $('#volunteer-flow2').hide();
            UI.yzmDreamSchool.click();
            findUserExam();
        });

    })


});
