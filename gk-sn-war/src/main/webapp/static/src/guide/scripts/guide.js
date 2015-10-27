/**
 * Created by kepeng on 15/9/24.
 */

define(function (require) {
    var $ = require('$');
    require('backToTop');

    var Tab = require('http://cdn.gaokao360.net/static/global/guide/scripts/tab.min');

    var timer = null;

    function getTab() {
        $.get('/policyInterpretation/allCategories.do?provinceId=1', function(data) {
            if ('0000000' === data.rtnCode) {
                Tab.init({
                    data:data.bizData,
                    contentId:'tab_title_content',
                    getArticleHandle:function(id) {
                        clearTimeout(timer);
                        timer = setTimeout(function() {
                            getArticle(id);
                        }, 300)
                    }
                });
            }
        });
    }

    function getArticle(id) {
        $.get('/policyInterpretation/detail.do?id=' + id, function(data) {
            if ('0000000' === data.rtnCode) {
                if (data.bizData) {
                    $('#tab_content').html(data.bizData.content);
                    $.each($('#tab_content img'), function(i, value) {
                        var src = $(value).attr('src');
                        if (src.indexOf('http://') < 0 && src.indexOf('https://') < 0) {
                            $(value).attr('src', 'http://www.gkzy114.com/' + src);
                        }
                    });
                }
            }
        });
    }

    $(document).ready(function() {
        getTab();
    });
});

