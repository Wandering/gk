/**
 * Created by kepeng on 15/9/24.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    var Question = {
        startSize:0,
        endSize:0,
        renderAsk: function(data) {
            var html = [];
            for (var i = 0, len = data.length; i < len; i++) {
                var question = data[i].question;
                if (question) {
                    html.push('<section class="ask-answer mt20">');
                    html.push('<a target="_blank" href="/question/question_detile.jsp?id=' + question.userId + '"><div class="ask mt20">');
                    html.push('<div class="head-img">');
                    html.push('<img src="' + question.userIcon || '' + '" />');
                    html.push('</div>');
                    html.push('<div class="head-info">');
                    html.push('<h6>来自 ' + question.userName || '匿名专家' + new Date(question.answerTime).Format('yyyy-MM-dd hh-mm') + '</h6>');
                    var questions = question.questions;
                    var text = [];
                    for (var i = 0, len = questions.length; i < len; i++) {
                        text.push(questions[i].text);
                    }
                    html.push('<h3>' + text.join('') + '</h3>');
                    html.push('</div></div></a>');
                }

                var answer = data[i].answer;

                if (answer) {
                    html.push(this.renderAnswer(answer));
                }
                html.push('</section>');
            }

            $('#question_content').html(html.join(''));
        },
        renderAnswer: function(answer) {
            var html = [];
            html.push('<ul class="answer-list">');
            html.push('<li>');
            html.push('<div class="left">');
            html.push('<div class="head-img">');
            html.push('<img src="' + answer.userIcon + '" />');
            html.push('<i class="star"></i>');
            html.push('</div>');
            html.push('<span>' + answer.userName + '</span>');
            html.push('</div>');
            var answers = answer.answers;
            var text = [];
            for (var i = 0, len = answers.length; i < len; i++) {
                text.push('<p>' + answers[i].text + '</p>');
                text.push('<p class="ta"><img src="' + answers[i].img + '" /></p>');
            }
            html.push('<div class="right">' + text.join('') + '</div>');
            html.push('</li></ul>');
        },
        getNew: function(startSize, endSize) {
            var that = this;
            $.get('/question/newQuestion.do?startSize=' + startSize + '&endSize=' + endSize, function(data) {
                if ('0000000' === data.rtnCode) {
                    that.renderAsk(data.bizData);
                }
            });
        },
        getHot: function(startSize, endSize) {
            var that = this;
            $.get('/question/hotQuestion.do?startSize=' + startSize + '&endSize=' + endSize, function(data) {
                if ('0000000' === data.rtnCode) {
                    that.renderAsk(data.bizData);
                }
            });
        },
        getSearch: function() {
            var that = this;
            $.get('', function(data) {

            })
        }
    }

    $(document).ready(function() {

        var keywords = getUrLinKey('keywords');
        if (keywords) {
            $('#tabs_list').hide();
        } else {
            $('#tabs_list').show();
            Question.getNew(0, 10);
            $('.tabs-list li').on('mouseover', function(e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    Question[$(this).attr('data-method')](0, 10);
                }
            });
        }
    });
});


