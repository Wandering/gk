/**
 * Created by kepeng on 15/9/24.
 */

define(function(require) {
    var $ = require('$');
    require('swiper');

    var getQueryStr = function(_url, _param) {
        var rs = new RegExp("(^|)" + _param + "=([^\&]*)(\&|$)", "g").exec(_url),
            tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    };

    var Question = {
        startSize: 0,
        endSize:10,
        renderAsk: function(data) {
            var html = [];
            var i = 0,
                len = data.length
            for (; i < len; i++) {
                var question = data[i].question;
                if (question) {
                    html.push('<a href="/question/question_detile.jsp?id=' + question.questionId + '"><section class="ask-answer mt20">');
                    html.push('<div class="ask mt20">');
                    html.push('<div class="head-img">');
                    var src = question.userIcon;
                    if (!'aa.jpg'.match(/(.jpg|.png|.gif)/g)) {
                        src = '/static/src/common/images/user_default.png';
                    }
                    html.push('<img src="' + (src || '') + '" />');
                    html.push('</div>');
                    html.push('<div class="head-info">');
                    var createTime = new Date(question.createTime).Format('yyyy-MM-dd hh:mm');
                    html.push('<h6>来自 ' + (question.userName || '匿名专家  ') + createTime + '</h6>');
                    var questions = question.questions;
                    var text = [];
                    for (var j = 0, jlen = questions.length; j < jlen; j++) {
                        text.push(questions[j].text);
                    }
                    html.push('<h3>' + text.join('') + '</h3>');
                    html.push('</div></div>');
                }

                var answer = data[i].answer;
                if (answer) {
                    html.push(this.renderAnswer(answer));
                }
                html.push('</section></a>');
            }

            if (this.startSize == 0) {
                $('#question_content').html(html.join(''));
            } else {
                $('#question_content').append(html.join(''));
            }

        },
        renderAnswer: function(answer) {
            var html = [];
            html.push('<ul class="answer-list">');
            html.push('<li>');
            html.push('<div class="left">');
            html.push('<div class="head-img">');
            html.push('<img src="' + (answer.userIcon || '') + '" />');
            html.push('<i class="star"></i>');
            html.push('</div>');
            html.push('<span>' + (answer.userName || '匿名') + '</span>');
            html.push('</div>');
            var answers = answer.answers;
            var text = [];
            for (var n = 0, nlen = answers.length; n < nlen; n++) {
                text.push('<p>' + answers[n].text + '</p>');
                if (answers[n].img) {
                    text.push('<p class="ta"><img src="' + answers[n].img + '" /></p>');
                }
            }
            html.push('<div class="right">' + text.join('') + '</div>');
            html.push('</li></ul>');
            return html.join('');
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
            if (this.startSize == 0) {
                $('.next-btn').removeClass('none');
                $('.next-btn').text('加载更多...');
            }
            var that = this;
            $.get(url + 'startSize=' + that.startSize + '&endSize=' + that.endSize, function(data) {
                if ('0000000' === data.rtnCode) {
                    if (data.bizData.length > 0) {
                        $('#more_loading').show();
                        that.renderAsk(data.bizData);
                    } else {
                        if (that.startSize == 0) {
                            $('#more_loading').hide();
                            $('#question_content').html('<p style="padding: 20px 0;text-align: center">暂无相关信息！</p>');
                        } else {
                            $('.next-btn').text('暂无更多信息！');
                            $('.next-btn').addClass('none');
                        }

                    }

                }
            });
        },
        getSearch: function() {
            var keyword = $('#keywords').val();
            //if (keyword) {
                $('#tabs_list').hide();
                this.startSize = 0;
                this.endSize = 10;
                var url = '/question/newQuestion.do?keyword=' + keyword + '&';
                this.getData(url);
            //}
        },
        addEventForMore: function() {
            this.startSize += 10;
            this.endSize += 10;
            var keyword = $('#keywords').val();
            if (keyword) {
                var url = '/question/newQuestion.do?keyword=' + keyword + '&';
                this.getData(url);
            } else {
                Question[$('.tabs-list li.active').attr('data-method')]();
            }

        }
    }

    var testData = [{
        question: {
            "questions": [{
                "text": "综合评价招生是统招生吗？",
                "img": ""
            }], //问题描述
            "createTime": 1443240600798, //创建时间
            "userId": 1, //专家ID
            "userName": "张三", //专家名称
            "userIcon": "http://himg.bdimg.com/sys/portrait/item/c68b4a737048696265726e6174655374d718.jpg", //专家头像
            "disableNum": 0, //禁用次数  3次以上不能再换人问
        },
        answer: {
            "answers": [{
                "text": "陕西省招办每年公布的《陕西省普通高等院校招生工作实施办法》，是陕西省高考招生的纲领性文件，家长及考生都应深入了解。2015年陕西省招生政策会陆续公布，请随时关注本平台各批次招生政策，在此我们会对此“办法”进行专业通俗的解读。以便于家长和考生正确把握。",
                "img": "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/super/pic/item/023b5bb5c9ea15ce5a2233bfb0003af33b87b2bc.jpg"
            }], //答案描述
            "answerTime": 1443240600798, //回答时间
            "userId": 1, //专家ID
            "userName": "张三", //专家名称
            "userIcon": "http://himg.bdimg.com/sys/portrait/item/c68b4a737048696265726e6174655374d718.jpg", //专家头像
        }
    }];

    $(document).ready(function() {

        var keywords = decodeURIComponent(getQueryStr(window.location.href, 'val'));
        if (keywords) {
            $('#tabs_list').hide();
            $('#keywords').val(keywords);
            Question.getSearch();
        } else {
            $('#tabs_list').show();
            //Question.renderAsk(testData);
            //return;
            Question.getNew();
            $('.tabs-list li').on('mouseover', function(e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    Question.startSize = 0;
                    Question.endSize = 10;
                    Question[$(this).attr('data-method')]();
                }
            });
        }

        $('#search').on('click', function(e) {
            Question.getSearch();
        });

        $('.next-btn').on('click', function(e) {
            if (!$(this).hasClass('none')) {
                Question.addEventForMore();
            }
        });
    });
});