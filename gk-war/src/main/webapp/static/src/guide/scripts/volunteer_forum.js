/**
 * Created by kepeng on 15/9/24.
 */

define(function (require) {
    var $ = require('$');
    require('backToTop');

    var Tab = require('/static/src/guide/scripts/tab');

    var timer = null;

    var nextBtn = $('.next-btn');
    function getTab() {
        $.get('/volunteerSchool/categories.do', function(data) {
            if ('0000000' === data.rtnCode) {
                Tab.init({
                    data:data.bizData,
                    contentId:'tab_title_content',
                    parentHandle:function(curObj) {
                        clearTimeout(timer);
                        timer = setTimeout(function() {
                            if ('填报指南' === curObj.name) {
                                nextBtn.hide();
                                getArticleDetile(curObj.id);
                            } else {
                                nextBtn.removeClass('none').text('加载更多...');
                                nextBtn.show();
                                getArticleList(curObj, 1);
                            }
                        }, 300)
                    }
                });
            }
        });
    }

    function renderList(list, page) {
        var activeLi = $('.tabs-list li.active');
        var code = activeLi.attr('data-id');
        var menuName = activeLi.text();
        var html = [];
        for (var i = 0, len = list.length; i < len; i++) {
            html.push('<a target="_blank" href="/consult/gk_hot_detile.jsp?menuName=' + menuName + '&id=' + list[i].id + '&code=' + code + '"><div class="detile-content mt20">');
            html.push('<div class="detile-header">');
            html.push('<span class="order-number">' + (i + (page - 1) * 8 + 1) + '</span>');
            html.push('<span class="detile-title">' + list[i].title + '</span>');
            html.push(' <span class="fr">' + new Date(list[i].lastModDate).Format('yyyy-MM-dd hh:mm') + '</span>');
            html.push('</div>');
            if(list[i].summary==null||list[i].summary.trim()==""){
                html.push('<div class="detile-info mt20 hide">');
            }else{
                html.push('<div class="detile-info mt20">');
            }
            html.push('<img class="triangle" src="/static/dist/common/images/triangle.png" />');
            html.push(list[i].summary);
            html.push('</div>');
            html.push('</div></a>');
        }
        return html.join('');
    }

    function getArticleList(curObj, curPage) {
        $.get('/volunteerSchool/articles.do?cateId=' + curObj.id + '&pn=' + curPage + '&ps=8', function(data) {
            if ('0000000' === data.rtnCode) {
                var list = data.bizData.rows;
                if (list.length <= 0) {
                    nextBtn.addClass('none').text('没有更多可加载！');
                    return;
                }
                nextBtn.attr('data-page', data.bizData.page);
                var html = [];
                if (curPage == 1) {
                    html.push('<h6>' + curObj.name + '</h6>');
                }
                html.push(renderList(list, data.bizData.page));
                if (curPage == 1) {
                    $('.section-article').html(html.join(''));
                } else {
                    $('.section-article').append(html.join(''));
                }
            } else {
                nextBtn.addClass('none').text('没有更多可加载！');
            }
        })
    }

    function getArticleDetile(id) {
        $.get('/volunteerSchool/article.do?id=' + id, function(data) {
            if ('0000000' === data.rtnCode) {
                $('.section-article').html(data.bizData.content);
            }
        })
    }

    $(document).ready(function() {
        getTab();
        nextBtn.on('click', function(e) {
            if ($(this).hasClass('none')) {
                return;
            }
            var activeLi = $('.tabs-list li.active');
            var curObj = {
                id: activeLi.attr('data-id'),
                name: activeLi.html()
            }
            getArticleList(curObj, parseInt($(this).attr('data-page')) + 1);
        });

        var api = flowplayer("player", "/static/src/guide/scripts/flowplayer-3.2.18.swf", {
            clip: {
                autoPlay: false,       //是否自动播放，默认true
                autoBuffering: false     //是否自动缓冲视频，默认true
            }
        });
    });
});


