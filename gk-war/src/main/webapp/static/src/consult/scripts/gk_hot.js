/**
 * Created by kepeng on 15/9/24.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');
    require('backToTop');

    var Hot = {
        nextBtn:$('.next-btn'),
        render: function(data, pageNO) {
            var html = [];
            for (var i = 0, len = data.length; i < len; i++) {
                html.push('<a href="/consult/gk_hot_detile.jsp?id=' + data[i].id + '"><div class="detile-content mt20">');
                html.push('<div class="detile-header">');
                var num = i + 1 + 4 * (pageNO - 1);
                html.push('<span class="order-number">' + num + '</span>');
                html.push('<span class="detile-title">' + data[i].hotInformation + '</span>');
                var date = new Date(data[i].createDate).Format('yyyy-MM-dd hh:mm');
                html.push('<span class="fr">上传时间：' + date + '</span>');
                html.push('</div>');
                html.push('<div class="detile-info mt20">');
                html.push(data[i].informationContent);
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
            $.get('/gkinformation/getAllInformation.do?pageNO=' + pageNO, function(data) {
                if ('0000000' === data.rtnCode)  {
                    if (data.bizData.length <= 0 && pageNO == 1) {
                        that.nextBtn.hide();
                        $('#wrapper').html('<p style="margin: 20px 0; text-align: center">暂无信息！</p>');
                        return;
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
                that.getData(parseInt($(this).attr('data-page')) + 1);
            });
        },
        getSearch: function(pageNO, keyWords) {
            var that = this;
            $.get('/gkinformation/getInformationByKey.do?pageNO=' + pageNO + '&key=' + keyWords, function(data) {
                if ('0000000' === data.rtnCode)  {
                    if (data.bizData.length <= 0 && pageNO == 1) {
                        that.nextBtn.hide();
                        $('#wrapper').html('<p style="margin: 20px 0; text-align: center">没有搜索到可用的信息！ </p>');
                        return;
                    }
                    that.nextBtn.attr('data-page', pageNO);
                    that.render(data.bizData, pageNO);
                } else {
                    that.nextBtn.addClass('none').text('没有更多可加载！');
                }
            });
        }
    }

    $(document).ready(function() {
        Hot.getData(1);
        Hot.nextPageHandle();
        $('#search').on('click', function(e) {
            var searchKeyWords = $('#key_words').val();
            if (searchKeyWords) {
                Hot.getSearch(1, searchKeyWords);
            } else {
                Hot.getData(1);
            }
        });
    });
});
