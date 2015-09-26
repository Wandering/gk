/**
 * Created by kepeng on 15/9/26.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    var SearchResult = {
        startSize:0,
        endSize:10,
        renderResultList: function(data) {
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
                    content.push('<p>' + answers[i].text + '</p>');
                    content.push('<p class="ta"><img src="' + answers[i].img + '" /></p>');
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
        getQuestionByKeywords: function() {
            var keywords = $('#keywords').val();
            $.get('', function(data) {
                if ('0000000' === data.rtnCode) {

                }
            });
        }
    }

    $(document).ready(function() {
        $('#search_button').click(function() {
            SearchResult.getQuestionByKeywords();
        });
    });
});
