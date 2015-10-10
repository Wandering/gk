define(function (require) {
    var $ = require('$');
    require('header-user');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    var Question = {
        startSize: 0,
        endSize: 5,
        next:$('.next-btn'),
        render: function(data) {
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
                if (answers) {
                    for (var c = 0, clen = answers.length; c < clen; c++) {
                        var text = answers[c].text;
                        if (answers[c].text.length > 200) {
                            text = text.substring(0, 200);
                        }
                        content.push('<p>' + text + '</p>');
                        if (answers[c].img) {
                            content.push('<p class="ta"><img src="' + answers[c].img + '" /></p>');
                        }
                    }
                }

                var ask = title.join('')

                if (ask.length > 50) {
                    ask = ask.substring(0, 50);
                }

                html.push('<a href="/question/question_detile.jsp?id=' + question.questionId + '"><div class="detail-content">'
                    + '<div class="detail-header">'
                    + '<span class="order-number">' + (i + 1 + this.startSize) + '、</span>'
                    + '<span class="detail-title">' + ask + '</span>'
                    + '<span class="upload-time">' + time + '</span>'
                    + '</div>');
                if (content.length > 0) {
                   html.push('<div class="detail-info">'
                       + '<img class="triangle" src="/static/dist/common/images/triangle.png" />'
                       + content.join('')
                       + '</div>');
                }

                html.push('</div></a>');
            }
            return html.join('');
        },
        getMyQuestion: function(contentId, isAnswer) {
            var url = '/answer/findMyQuestion.do?';
            this.getData(url, contentId, isAnswer);
        },
        getData: function(url, contentId, isAnswer) {
            var that = this;
            var keywords = $('#keywords').val();
            var xhr = $.get(url + 'startSize=' + this.startSize + '&endSize=' + this.endSize + '&isAnswer=' + isAnswer + '&keyword=' + keywords, function(data) {
                if ('0000000' === data.rtnCode) {
                    if (data.bizData.length > 0) {
                        that.next.show();
                        if (that.startSize == 0) {
                            $('#' + contentId).html(that.render(data.bizData));
                        } else {
                            $('#' + contentId).append(that.render(data.bizData));
                        }

                    } else {
                        if (that.startSize == 0) {
                            $('#' + contentId).html(that.getTipTmpl('暂时没有数据，请耐心等待哦'));
                            that.next.hide();
                        } else {
                            that.next.text('暂无更多信息！');
                            that.next.off('click');
                        }
                    }
                }
            }).error(function(e) {
                $('#' + contentId).html(that.getTipTmpl('服务器开小差啦~ ~ ~'));
            });
        },
        getTipTmpl: function(msg) {
            return '<div class="error-tip-new"><img src="/static/dist/common/images/no-data-logo.png" /><p class="ta">' + msg + '</p></div>'
        },
        addNextPageHandle: function() {
            this.startSize += 5;
            this.endSize += 5;
            var isAnswer = $('.toggle-nav div.btn-selected').attr('data-isAnswer');
            this.getMyQuestion('detail_content_question', isAnswer);
        }
    };

    $(document).ready(function() {
        var method = getUrLinKey('method');
        if ('ask' === method) {
            $('.ask').addClass('btn-selected').siblings().removeClass('btn-selected');
            Question.getMyQuestion('detail_content_question', 0);
        } else {
            Question.getMyQuestion('detail_content_question', 1);
        }

        Question.next.on('click', function(e) {
            Question.addNextPageHandle();
        });

        $('#search').on('click', function(e) {
            //var val = $('#keywords').val();
            //window.location.href = '/question/question.jsp?val=' + val;
            var isAnswer =  $('.toggle-nav div.btn-selected').attr('data-isAnswer');
            Question.startSize = 0;
            Question.endSize = 5;
            Question.getMyQuestion('detail_content_question', isAnswer);
        });

        $('#keywords').keydown(function(e) {
            if (e.keyCode == 13) {
                var search = $('#keywords').val();
                if (search) {
                    var isAnswer =  $('.toggle-nav div.btn-selected').attr('data-isAnswer');
                    Question.startSize = 0;
                    Question.endSize = 5;
                    Question.getMyQuestion('detail_content_question', isAnswer);
                }
            }
        });

        $('.toggle-nav div.btn').on('click', function() {
            if (!$(this).hasClass('btn-selected')) {
                $(this).addClass('btn-selected').siblings().removeClass('btn-selected');
                var isAnswer = $(this).attr('data-isAnswer');
                Question.startSize = 0;
                Question.endSize = 5;
                Question.getMyQuestion('detail_content_question', isAnswer);
            }
        });
    });

});
