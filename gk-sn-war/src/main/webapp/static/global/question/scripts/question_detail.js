define(function (require) {
    var $ = require('$');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    console.log(22)
    var Question = {
        renderAsk: function(data) {
            var html = [];
            var question = data.question;
            if (question) {
                html.push('<section class="ask-answer mt20">');
                html.push('<div class="ask mt20">');
                html.push('<div class="head-img">');
                html.push('<img src="' + (question.userIcon || 'http://cdn.gaokao360.net/static/global/common/images/user_default.png') + '" />');
                html.push('</div>');
                html.push('<div class="head-info">');
                html.push('<h6>来自 ' + (question.userName || '') + new Date(question.createTime).Format('yyyy-MM-dd hh:mm') + '</h6>');
                var questions = question.questions;
                var text = [];
                var textImg = [];
                for (var i = 0, len = questions.length; i < len; i++) {
                    text.push(questions[i].text);
                    if (questions[i].img) {
                        textImg.push('<img src="' + questions[i].img + '" />');
                    }
                }
                html.push('<h3>' + text.join('') + '</h3>');
                html.push('<p>' + textImg.join('') + '</hp>');
                html.push('</div></div>');
            }

            var answer = data.answer;
            if (answer && answer.answers && answer.answers.length > 0) {
                html.push(this.renderAnswer(answer));
            }
            html.push('</section>');
            $('#section_article').html(html.join(''));
        },
        renderList: function(id, data) {
            var html = [];
            for (var i = 0, len = data.length; i < len; i++) {
                var question = data[i].question;
                var text = [];
                if (question) {
                    var questions = question.questions;
                    for (var j = 0, jlen = questions.length; j < jlen; j++) {
                        if (questions[j].text) {
                            text.push(questions[j].text);
                        }
                    }
                }
                if (text.length > 0) {
                    html.push('<li><a target="_blank" href="/question/question_detail.jsp?id=' + question.questionId + '">' + text.join('') + '</a></li>');
                }
            }

            $('#' + id).html(html.join(''));
        },
        renderAnswer: function(answer) {
            var html = [];
            html.push('<ul class="answer-list">');
            html.push('<li>');
            html.push('<div class="left">');
            html.push('<div class="head-img">');
            html.push('<img src="' + (answer.userIcon || 'http://cdn.gaokao360.net/static/global/common/images/user_default.png') + '" />');
            html.push('<i class="star"></i>');
            html.push('</div>');
            html.push('<span>' + (answer.userName || '') + '</span>');
            html.push('</div>');
            var answers = answer.answers;
            var text = [];
            for (var i = 0, len = answers.length; i < len; i++) {
                if (answers[i].text) {
                    text.push('<p>' + answers[i].text + '</p>');
                }

                if (answers[i].img) {
                    text.push('<p><img src="' + answers[i].img + '" /></p>');
                }
            }
            html.push('<div class="right">' + text.join('') + '</div>');
            html.push('</li></ul>');
            return html.join('');
        },
        get: function(id) {
            var that = this;
            $.get('/question/questionDetail.do?id=' + id, function(data) {
                if ('0000000' === data.rtnCode) {
                    that.renderAsk(data.bizData);
                }
            });
        },
        getNew: function() {
            var that = this;
            $.get('/question/newQuestion.do?startSize=0&endSize=5', function(data) {
                if ('0000000' === data.rtnCode) {
                    that.renderList('new', data.bizData);
                }
            });
        },
        getHot: function() {
            var that = this;
            $.get('/question/hotQuestion.do?startSize=0&endSize=5', function(data) {
                if ('0000000' === data.rtnCode) {
                    that.renderList('hot', data.bizData);
                }
            });
        },
        updateReadNum: function(id) {
            //$.get('/question/updateBrowseNum.do?id=' + id, function(data) {});
            $.ajax({
                type: 'post',
                url: '/question/updateBrowseNum.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    id:id
                },
                dataType: 'json',
                success: function(data) {
                },
                error: function(data) {
                }
            });
        }
    }

    $(document).ready(function() {
        var id = getUrLinKey('id');
        Question.get(id);
        Question.updateReadNum(id);
        Question.getNew();
        Question.getHot();
    });
});
