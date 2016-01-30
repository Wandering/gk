define(['commonjs', 'tips', 'handlebars','cookie'], function (util, tips, handlebars,cookie) {

    require('../css/volunteer/volunteer-prediction.css');

    var  targetScoreV = util.cookie.getCookieValue('targetScore');
    var  subjectType = util.cookie.getCookieValue('subjectType');

    $(function () {
        $('#score').val(targetScoreV);
        $('.radio-subject[value="'+ subjectType +'"]').attr('checked','checked');
        $('#predict-school-btn').on('click', function () {
            if (!cookie.getCookieValue('isLogin')) {
                tips('#tips', '请先登录后再操作!');
                return false;
            }
            //if (cookie.getCookieValue('vipStatus')=='0') {
            //    tips('#tips', '请先升级VIP再操作!');
            //    return false;
            //}
            var subjectV = $('.radio-subject[name="subject"]:checked').val(),
                scoreV = $.trim($('#score').val())
            if (subjectV == "" || subjectV == undefined) {
                tips('#tips', '请选择科目');
                return false;
            }

            if (scoreV == "") {
                tips('#tips', '请输入分数');
                return false;
            }
            util.ajaxFun(util.INTERFACE_URL.getPredictSchoolList, 'POST', {
                'type': subjectV,
                'score': scoreV
            }, function (res) {
                console.log(res)
                if (res.rtnCode === "0000000") {
                    $('#content-a').hide();
                    var template = handlebars.compile($("#temp-content").html());
                    handlebars.registerHelper('stars',function(val){
                        var star = '';
                        for(var i =0;i<val;i++){
                            star += '<i class="icon-star"></i>'
                        }
                        return star;
                    });
                    $('#content-b').html(template(res.bizData));
                    $('#score-data').text(scoreV);
                    subjectV=='1'?$('#type-subject').text('文史'):$('#type-subject').text('理工');
                    var num = 0;
                    for(var k in res.bizData){
                        num += res.bizData[k].count;
                    }
                    $('#total-num').text(num);

                } else {
                    tips('#tips', res.msg);
                }
            });
        });
        $('#content-b').on('click', '#prev-btn', function () {
            $('#content-a').show();
            $('#content-b').hide();
        })


        // 设为目标
        $('#content-b').on('click','.objective-btn',function(){
            var schoolname = $(this).attr('schoolname');
            util.cookie.setCookie("subjectType", subjectType, 4, "");
            util.cookie.setCookie("targetScore", targetScoreV, 4, "");
            util.cookie.setCookie("targetSchool", schoolname, 4, "");
            window.location.assign('http://' + window.location.host + '/static/user-target.html');
        });









    });
})