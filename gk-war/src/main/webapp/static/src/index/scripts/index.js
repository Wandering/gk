define(function (require) {
    var $ = require('$');
    require('swiper');

    var url = 'http://' + window.location.host;
    $(function () {
        //在线互动
        var more = $('.more');
        $('.ask-question').click(function () {
            window.location.assign(url + '/question/ask.jsp');
        });
        $('.go-search').click(function () {
            var searchText = $('.search-val').val();
            window.location.assign(url + '/question/question.jsp?val=' + searchText);
        });
        $('#tabs-online').find('li').click(function () {
            var n = $(this).index();
            $(this).addClass('active').siblings().removeClass('active');
            //$('.tab').fadeOut();
            $('.tab').hide();
            $('.tab').eq(n).fadeIn(500);
        });
        more.click(function () {
            window.location.assign(url + '/question/question.jsp')
        });

        //热门资讯
        $('#tabs-hosts').find('li').click(function () {
            var n = $(this).index();
            var m = $('.more');
            $(this).addClass('active').siblings().removeClass('active');
            //$('.tab-info').fadeOut();
            $('.tab-info').hide();
            $('.tab-info').eq(n).fadeIn(500);
            (n == 1) ? (m.fadeOut()) : (m.fadeIn());
        });
        $('#hot-info').click(function(){
            window.location.assign(url+'/consult/gk_hot.jsp')
        });
        $.get('/agent/getAgent.do', function (res) {
            if (res.rtnCode == '0000000') {
                var dataJson = res.bizData;
                $.each(dataJson, function (i, v) {
                    //console.log(v.address)
                    //console.log(v.name)
                    //console.log(v.telphone)
                });
            } else {
                alert(res.msg);
            }
        });
        $.ajax({
            url:' /gkinformation/getAllInformation.do',
            dataType:'json',
            type:'get',
            data:{
                "pageNo":0
            },
            success:function(res){
                var dataJson =res.bizData;
                //console.log(res);
                var template = '';
                $.each(dataJson,function(i,v){
                    template += '<li>' +
                        '<div class="icon ta"> ' +
                        '<span>4月25日</span> ' +
                        '</div> ' +
                        '<div class="title-info"> ' +
                        '<h3>'+v.hotInformation+'</h3> ' +
                        '<h6>'+v.informationContent+'</h6> ' +
                        '</div> ' +
                        '</li>'
                });
                $('.hot-list').html(template);
            }
        })


    });

    //在线互动获取数据
    (function() {
        var Question = {
            render: function() {
                var html = [];
                for (var i = 0, len = data.length; i < len; i++) {
                    var question = data[i].question;
                    var questions = question.questions;
                    var title = [];
                    for (var t = 0, tlen = questions.length; t < tlen; t++) {
                        title.push(questions[t].text);
                    }
                    var time = new Date(data[i].createTime).Format('yyyy-MM-dd hh:mm');
                    var answer = data[i].answer;
                    var answers = answer.answers;
                    var content = [];
                    for (var c = 0, clen = answers.length; c < clen; c++) {
                        var text = answers[i].text;
                        if (answers[i].text.length > 200) {
                            text = text.substring(0, 200);
                        }
                        content.push('<p>' + text + '</p>');
                        if (answers[i].img) {
                            content.push('<p class="ta"><img src="' + answers[i].img + '" /></p>');
                        }
                    }
                    html.push('<div class="detile-content mt20">'
                        + '<div class="detile-header">'
                        + '<span class="order-number">' + (i + 1) + '</span>'
                        + '<span class="detile-title">' + title.join('') + '</span>'
                        + '<span class="fr">' + time + '</span>'
                        + '</div>'
                        + '<div class="detile-info mt20">'
                        + content.join('')
                        + '</div>'
                        + '</div>');
                }
            },
            getNew: function() {
                var url = '/question/newQuestion.do?';
                this.getData(url);
            },
            getHot: function() {
                var url = '/question/hotQuestion.do?';
                this.getData(url);
            },
            getData: function(url) {
                var that = this;
                $.get(url + 'startSize=0&endSize=6', function(data) {
                    if ('0000000' === data.rtnCode) {
                        if (data.bizData.length > 0) {
                        } else {
                        }
                    }
                });
            }
        };

        Question.getNew();
        Question.getHot();
    })();

});
