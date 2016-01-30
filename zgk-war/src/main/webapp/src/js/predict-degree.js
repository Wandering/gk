define(['commonjs','tips','handlebars','cookie','noDataTips'], function (util,tips,handlebars,cookie,noDataTips) {
    require('../css/volunteer/volunteer-prediction.css');

    $(function () {

        var  targetScoreV = util.cookie.getCookieValue('targetScore');
        var  targetSchoolV = util.cookie.getCookieValue('targetSchool');
        var  subjectType = util.cookie.getCookieValue('subjectType');

        console.log(targetSchoolV)


        $('#score').val(targetScoreV);
        $('#universityName').val(targetSchoolV);

        $('.radio-subject[value="'+ subjectType +'"]').attr('checked','checked');


        $('#predict-degree-btn').on('click', function () {
            var subjectV = $('.radio-subject[name="subject"]:checked').val(),
                scoreV = $.trim($('#score').val()),
                universityNameV = $.trim($('#universityName').val());
            if (!cookie.getCookieValue('isLogin')) {
                tips('#tips', '请先登录后再操作!');
                return false;
            }

            if (subjectV == "" || subjectV == undefined) {
                tips('#tips', '请选择科目');
                return false;
            }

            if (scoreV == "") {
                tips('#tips', '请输入分数');
                return false;
            }
            if (universityNameV == "") {
                tips('#tips', '请输入目标院校');
                return false;
            }
            util.ajaxFun(util.INTERFACE_URL.getPredictProbability, 'POST', {
                'type': subjectV,
                'score': scoreV,
                'universityName': universityNameV
            }, function (res) {
                console.log(res)
                if (res.rtnCode === "0000000") {
                    $('#content-a').hide();
                    $('#content-b').show();
                    var source = $("#temp-content").html();
                    var template = handlebars.compile(source);
                    $('#content-b').html(template(res.bizData));
                    var startNum = res.bizData.probability;
                    if(startNum  > 2){
                        $('#recommend').show();
                    }else{
                        $('#recommend').hide();
                    }
                    if(res.bizData.probability==0){
                        $('#star-list').html('暂无');
                    }
                    var strArr = '';
                    for (var i = 0; i < res.bizData.probability; i++) {
                        var star = '<span class="star icon-star"></span>';
                        strArr += star;
                    }
                    $('#star-list').html(strArr);
                    res.bizData.type == '1' ? $('#type-subject').text('文史') : $('#type-subject').text('理工');

                    if(res.bizData.historyList.length==0){
                        $('.data-tips').html(noDataTips('真抱歉,暂无数据'));
                    } else {
                        $('.data-tips').html('');
                    }



                } else {
                    tips('#tips', res.msg);
                }
            });
        })
        $('#content-b').on('click', '#prev-btn', function () {
            $('#content-a').show();
            $('#content-b').hide();
        })


        // 设为我的目标
        $('#content-b').on('click','#set-my-target-btn',function(){
            setCookie();
            window.location.assign('http://' + window.location.host + '/static/user-target.html');
        });



        function setCookie(){
            var subjectType = $('.radio-subject[name="subject"]:checked').val(),
                scoreV = $.trim($('#score').val()),
                universityNameV = $.trim($('#universityName').val());
            util.cookie.setCookie("subjectType", subjectType, 4, "");
            util.cookie.setCookie("targetScore", scoreV, 4, "");
            util.cookie.setCookie("targetSchool", universityNameV, 4, "");
        }


        $('#content-b').on('click','#system-school-btn',function(){
            setCookie();
            window.location.assign('http://' + window.location.host + '/static/predict-school.html');
        })


    });
});