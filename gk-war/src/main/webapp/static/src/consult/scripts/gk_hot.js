/**
 * Created by kepeng on 15/9/24.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');
    require('backToTop');

    var getQueryStr = function(_url, _param) {
        var rs = new RegExp("(^|)" + _param + "=([^\&]*)(\&|$)", "g").exec(_url),
            tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    };

    var Hot = {
        nextBtn:$('.next-btn'),
        render: function(data, pageNO) {
            var html = [];
            for (var i = 0, len = data.length; i < len; i++) {
                html.push('<a href="/consult/gk_hot_detile.jsp?method=hot&id=' + data[i].id + '"><div class="detile-content mt20">');
                html.push('<div class="detile-header">');
                var num = i + 1 + 4 * (pageNO - 1);
                html.push('<span class="order-number">' + num + '</span>');
                html.push('<span class="detile-title">' + data[i].hotInformation + '</span>');
                var date = new Date(data[i].lastModDate).Format('yyyy-MM-dd hh:mm');
                html.push('<span class="fr">' + date + '</span>');
                html.push('</div>');
                html.push('<div class="detile-info mt20">');
                html.push(data[i].informationSubContent);
                html.push('</div>');
                html.push('</div></a>');
            }
            if (pageNO == 1) {
                $('#wrapper').html(html.join(''));
            } else {
                $('#wrapper').append(html.join(''));
            }
        },
        getData: function(pageNO) {
            var that = this;
            $.get('/gkinformation/getAllInformation.do?pageNo=' + (pageNO - 1), function(data) {
                if ('0000000' === data.rtnCode)  {
                    if (data.bizData.length <= 0) {
                        if (pageNO == 1){
                            that.nextBtn.hide();
                            $('#wrapper').html('<p style="margin: 20px 0; text-align: center">暂无信息！</p>');
                            return;
                        } else {
                            that.nextBtn.addClass('none').text('没有更多可加载！');
                            return;
                        }
                    }
                    if (pageNO == 1) {
                        that.nextBtn.show();
                    }

                    that.nextBtn.attr('data-page', pageNO);
                    that.render(data.bizData, pageNO);
                } else {
                    that.nextBtn.addClass('none').text('没有更多可加载！');
                }
            });
        },
        nextPageHandle: function() {
            var that = this;
            this.nextBtn.on('click', function(e) {
                if ($(this).hasClass('none')) {
                    return;
                }
                var searchKeyWords = $('#key_words').val();
                if (searchKeyWords) {
                    that.getSearch(parseInt($(this).attr('data-page')) + 1, searchKeyWords);
                } else {
                    that.getData(parseInt($(this).attr('data-page')) + 1);
                }

            });
        },
        getSearch: function(pageNO, keyWords) {
            var that = this;
            $.get('/gkinformation/getInformationByKey.do?pageNo=' + pageNO + '&key=' + keyWords, function(data) {
                if ('0000000' === data.rtnCode)  {
                    if (data.bizData.length <= 0) {
                        if (pageNO == 1) {
                            that.nextBtn.hide();
                            $('#wrapper').html('<p style="margin: 20px 0; text-align: center">没有搜索到可用的信息！ </p>');
                            return;
                        } else {
                            that.nextBtn.addClass('none').text('没有更多可加载！');
                            return;
                        }
                    }
                    that.nextBtn.show();
                    that.nextBtn.attr('data-page', pageNO);
                    that.render(data.bizData, pageNO);
                } else {
                    that.nextBtn.addClass('none').text('没有更多可加载！');
                }
            });
        }
    }

    $(document).ready(function() {
        var keywords = decodeURIComponent(getQueryStr(window.location.href, 'val'));
        if (keywords) {
            $('#key_words').val(keywords);
            Hot.getSearch(1, keywords);
        } else {
            Hot.getData(1);
        }

        Hot.nextPageHandle();
        $('#search').on('click', function(e) {
            $('.next-btn').removeClass('none').text('加载更多...');
            var searchKeyWords = $('#key_words').val();
            if (searchKeyWords) {
                Hot.getSearch(1, searchKeyWords);
            } else {
                Hot.getData(1);
            }
        });
    });
});
