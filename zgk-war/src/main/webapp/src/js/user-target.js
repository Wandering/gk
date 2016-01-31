define(['commonjs','handlebars','tips'], function (util,handlebars,tips) {
    require('../css/user/user-account-info.css');

    $(function () {
        $('#banner-info').prepend(require('html!../user-banner.html'));

        var cookie = require('cookie');
        var icon = cookie.getCookieValue('icon');
        var imgIco = require('../img/icon_default.png');
        if(icon=='undefined'){
            icon = imgIco;
        }
        var userName = cookie.getCookieValue('userName');
        var vipStatus = cookie.getCookieValue('vipStatus');
        $('.user-avatar').attr('src',icon);
        $('.user-name').text(userName);
        if(vipStatus==0){
            $('#btn-vip,#user-type').show();
            $('#vip-box').hide();
        }else{
            $('#btn-vip,#user-type').hide();
            $('#vip-box').show();
        }




        var targetTypeImg = require('../img/user-target-jbtm.png');
        var targetJbtmTypeImg = require('../img/icon-jbtm.png');
        var  targetScoreV = util.cookie.getCookieValue('targetScore');
        var  targetSchoolV = util.cookie.getCookieValue('targetSchool');
        var  subjectType = util.cookie.getCookieValue('subjectType');

        $('#target-score').val(targetScoreV);
        $('#target-school').val(targetSchoolV);

        $('.radio-subject[value="'+ subjectType +'"]').attr('checked','checked');


        $('#predict-btn').on('click', function () {
            targetPos();
        });
        $('#section2').on('click', '#target-score-btn',function () {
            var scoreV = $('#target-score-input').val();
            targetPos(scoreV);
        });




        function targetPos(val){
            var subjectV = $('.radio-subject[name="subject"]:checked').val(),
                scoreV = $.trim($('#target-score').val()),
                universityNameV = $.trim($('#target-school').val());
            if (subjectV == "" || subjectV == undefined) {
                tips('#tips', '请选择科目');
                return false;
            }
            if (scoreV == "") {
                tips('#tips', '请输入分数');
                return false;
            }
            if (scoreV.length !=3) {
                tips('#tips', '请输入正确分数');
                return false;
            }
            if (universityNameV == "") {
                tips('#tips', '请输入目标院校');
                return false;
            }

            if(val){
                scoreV = val;
            }
            util.ajaxFun(util.INTERFACE_URL.getPredictProbability, 'POST', {
                'type': subjectV,
                'score': scoreV,
                'universityName': universityNameV
            }, function (res) {
                console.log(res)
                if (res.rtnCode === "0000000") {
                    var template = handlebars.compile($("#temp-content-section2").html());
                    $('#section2').html(template(res.bizData));
                    $('#section1,#again-target,#update-target-btn,#time-out-title,.ago-situation,#results-details,.gt-target').hide();
                    $('#section2,.ago-situation-title').show();
                    var startNum = res.bizData.probability;
                    if(startNum  > 2){
                        $('#recommend').show();
                    }else{
                        $('#recommend').hide();
                    }
                    var strArr = '';
                    for (var i = 0; i < res.bizData.probability; i++) {
                        var star = '<span class="star icon-star"></span>';
                        strArr += star;
                    }
                    $('#star-list').html(strArr);
                    res.bizData.type == '1' ? $('#type-subject').text('理工') : $('#type-subject').text('文史');
                    var lowestNumLen = $('.lowest').length;
                    var lowestNum = 0;
                    for(var j=0;j<lowestNumLen;j++){
                        lowestNum += parseInt($('.lowest:eq('+ j +')').text());
                    }
                    var averageLowest = lowestNum/3;
                    var resultNum = averageLowest-scoreV;
                    if(resultNum > 0){
                        $('#average-lowest').text(resultNum)
                        $('#user-target-img').attr('src',targetTypeImg);
                    }else{
                        $('#user-target-img').attr('src',targetJbtmTypeImg);
                        $('#tips-target').text('恭喜您顺利的达到目标:')
                        $('.tips-target-p,.txt-link').hide();
                        $('.gt-target').show();
                    }
                } else {
                    tips('#tips', res.msg);
                }
            });
        }


        $('#section2').on('click','#save-btn',function(){
            $('#easy-title,#recommend,#save-btn,.txt-link').hide();
            $('#again-target,#update-target-btn,#time-out-title,.ago-situation,#results-details').show();
            var subjectV = $('.radio-subject[name="subject"]:checked').val(),
                scoreV = $.trim($('#target-score').val()),
                universityNameV = $.trim($('#target-school').val());

            var lowestNumLen = $('.lowest').length;
            var lowestNum = 0;
            for(var j=0;j<lowestNumLen;j++){
                lowestNum += parseInt($('.lowest:eq('+ j +')').text());
            }
            var averageLowest = lowestNum/3;



            var lowestScoreNumLen = $('.lowest-score').length;
            var lowestScoreNum = 0;
            for(var j=0;j<lowestScoreNumLen;j++){
                lowestScoreNum += parseInt($('.lowest-score:eq('+ j +')').text());
            }
            var averageScoreLowest = lowestScoreNum/3;


            util.ajaxFun(util.INTERFACE_URL.postAddFrecast, 'POST', {
                'typeId': subjectV,
                'achievement': scoreV,
                'universityName': universityNameV,
                'lowestScore': averageScoreLowest,
                'averageScore': averageLowest
            }, function (res) {
                console.log(res)
                if (res.rtnCode === "0000000") {


                } else {
                    tips('#tips', res.msg);
                }
            });
        });



        util.ajaxFun(util.INTERFACE_URL.getPerformanceDetail, 'GET', {}, function (res) {
            console.log(res)

        });


    });


});



