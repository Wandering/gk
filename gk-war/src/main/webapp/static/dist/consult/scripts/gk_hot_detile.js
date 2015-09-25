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

    function getArticleDetile(id) {
        $.get('/volunteerSchool/article.do?id=' + id, function(data) {
            if ('0000000' === data.rtnCode) {
                if (data.bizData) {
                    var html = [];
                    html.push('<h1>' + data.bizData.title + '</h1>');
                    html.push('<h6>' + new Date(data.bizData.lastModDate).Format('yyyy-MM-dd hh:mm:ss') + '</h6>');
                    html.push('<article>' + data.bizData.content + '</article>');
                    $('#section_article').html(html.join(''));
                } else {
                    $('#section_article').html('<h6>暂无信息！</h6>');
                }
            } else {
                $('#section_article').html('<h6>暂无信息！</h6>');
            }
        })
    }


    $(document).ready(function() {
        var id = getUrLinKey('id');
        getArticleDetile(id);
    });
});


