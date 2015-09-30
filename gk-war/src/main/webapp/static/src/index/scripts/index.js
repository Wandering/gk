define(function (require) {
    var $ = require('$');
    require('swiper');
    require('getTime');


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
        $('#hot-info').click(function () {
            window.location.assign(url + '/consult/gk_hot.jsp')
        });
        $.get('/agent/getAgent.do', function (res) {
            if (res.rtnCode == '0000000') {
                var dataJson = res.bizData;
                var addressHtml = ''
                $.each(dataJson, function (i, v) {
                    var address = v.address;
                    var name = v.name;
                    var telphone = v.telphone;
                    addressHtml+= '<div class="col-3">'
                        + '<p class="area-name">' + address + '</p>'
                        + '<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">' + telphone + '</span>'+ name + '</p>'
                        + '</div>';
                    console.log(addressHtml)
                    $('#address-box').html(addressHtml);
                });
            } else {
                alert(res.msg);
            }
        });
        $.ajax({
            url: ' /gkinformation/getAllInformation.do',
            dataType: 'json',
            type: 'get',
            data: {
                "pageNo": 0
            },
            success: function (res) {
                var dataJson = res.bizData;
                //console.log(res);
                var template = '';
                $.each(dataJson, function (i, v) {
                    template += '<li>' +
                    '<div class="icon ta"> ' +
                    '<span>' + getTime1(v.lastModDate) + '</span> ' +
                    '</div> ' +
                    '<div class="title-info"> ' +
                    '<h3>' + v.hotInformation + '</h3> ' +
                    '<h6>' + v.informationSubContent + '</h6> ' +
                    '</div> ' +
                    '</li>'
                });
                $('.hot-list').html(template);
            }
        })


    });

    //在线互动获取数据
    (function () {
        var Question = {
            render: function (data) {
                var html = [];
                for (var i = 0, len = data.length; i < len; i++) {
                    var question = data[i].question;
                    var questions = question.questions;
                    var title = [];
                    for (var t = 0, tlen = questions.length; t < tlen; t++) {
                        title.push(questions[t].text);
                    }
                    var time = new Date(question.createTime).Format('yyyy-MM-dd hh:mm');
                    var answer = data[i].answer;
                    var answers = answer.answers;
                    var content = [];
                    for (var c = 0, clen = answers.length; c < clen; c++) {
                        var text = answers[c].text;
                        if (answers[c].text.length > 300) {
                            text = text.substring(0, 300);
                        }
                        content.push('<p>' + text + '</p>');
                        if (answers[c].img) {
                            content.push('<p class="ta"><img src="' + answers[c].img + '" /></p>');
                        }
                    }
                    html.push('<a href="/question/question_detile.jsp?id=' + question.userId + '"><div class="detile-content mt20">'
                    + '<div class="detile-header">'
                    + '<span class="order-number">' + (i + 1) + '</span>'
                    + '<span class="detile-title">' + title.join('') + '</span>'
                    + '<span class="fr">' + time + '</span>'
                    + '</div>'
                    + '<div class="detile-info mt20">'
                    + content.join('')
                    + '</div>'
                    + '</div></a>');
                }
                return html.join('');
            },
            getNew: function (contentId) {
                var url = '/question/newQuestion.do?';
                this.getData(url, contentId);
            },
            getHot: function (contentId) {
                var url = '/question/hotQuestion.do?';
                this.getData(url, contentId);
            },
            getData: function (url, contentId) {
                var that = this;
                $.get(url + 'startSize=0&endSize=6', function (data) {
                    if ('0000000' === data.rtnCode) {
                        if (data.bizData.length > 0) {
                            $('#' + contentId).html(that.render(data.bizData));
                        } else {
                            $('#' + contentId).html('<p class="mt20 ta">暂无信息！</p>');
                        }
                    }
                });
            }
        };

        Question.getNew('tab_0');
        Question.getHot('tab_1');
    })();


    // 各地招办联系方式
    //$.getJSON('/agent/getAgent.do', function (res) {
    //    console.log(res)
    //    var dataJson = res.bizData;
    //    if(res.rtnCode=="0000000"){
    //        for (var i = 0; i < dataJson.length; i++) {
    //            var address = dataJson[i].address;
    //            var name = dataJson[i].name;
    //            var telphone = dataJson[i].telphone;
    //            console.log(address)
    //            console.log(name)
    //            console.log(telphone)
    //            var addressHtml = ''
    //                + '<div class="col-3">'
    //                + '<p class="area-name">' + address + '</p>'
    //                + '<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">' + telphone + '</span>'+ name + '</p>'
    //                + '</div>';
    //            $('#address-box').html(addressHtml);
    //        }
    //    }
    //
    //
    //})

});
