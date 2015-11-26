/**
 * Created by kepeng on 15/9/24.
 */

define(function (require) {
    var $ = require('$');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }


    var getQueryStr = function(_url, _param) {
        var rs = new RegExp("(^|)" + _param + "=([^\&]*)(\&|$)", "g").exec(_url),
            tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    };
    var method = getUrLinKey('method');
    var menuName = getUrLinKey('menuName');
    var codes = getUrLinKey('code');
    var menuName = decodeURIComponent(getQueryStr(window.location.href, 'menuName'));
    function getArticleDetile(id) {
        var url = '/volunteerSchool/article.do';
        if ('hot' === method) {
            url = '/gkinformation/getInformationContentById.do';
            $.get('/gkinformation/updateHotCount.do?id=' + id, function(ret) {
                //console.log(ret);
            });
        }
        $.get(url + '?id=' + id, function(data) {
            if ('0000000' === data.rtnCode) {
                if (data.bizData) {
                    var html = [];
                    html.push('<h1>' + (data.bizData.title || data.bizData.hotInformation) + '</h1>');
                    html.push('<article><iframe src="'+  (data.bizData.content || data.bizData.informationContent)  +'" frameborder="0" scrolling="no" style="border:0px;width:100%;"></iframe></article>');


                    $('#section_article').html(html.join(''));
                    $.each($('#section_article img'), function(i, value) {
                        var src = $(value).attr('src');
                        if (src.indexOf('http://') < 0 && src.indexOf('https://') < 0) {
                            src = 'http://www.gkzy114.com' + src;
                            $(value).attr('src', src);
                        }
                    })

                } else {
                    $('#section_article').html('<h6>暂无信息！</h6>');
                }
            } else {
                $('#section_article').html('<h6>暂无信息！</h6>');
            }
        })
    }

    function getRightInfo() {
        if ('hot' === method) {
            $.get('/gkinformation/getHotInformation.do?pageNo=0&pageSize=10', function(data) {
                console.log(data)
                if ('0000000' === data.rtnCode) {
                    var biz = data.bizData;
                    if (biz.length > 0) {
                        var html = [];
                        html.push('<h3>' + (menuName || '高考热点') + '</h3>');
                        html.push('<ul>');
                        for (var i = 0, len = biz.length; i < len; i++) {
                            html.push('<li><a target="_blank" href="/consult/gk_hot_detail.jsp?method=hot&id=' + biz[i].id + '">' + biz[i].hotInformation + '</a></li>');
                        }
                        html.push('</ul>');
                        $('#ask_list').html(html.join(''));
                    }
                }
            });
        } else {
            var code = getUrLinKey('code');
            if (code) {
                $.get('/volunteerSchool/ranks.do?cateId=' + code, function(data) {
                    if ('0000000' === data.rtnCode) {
                        var biz = data.bizData;
                        if (biz.length > 0) {
                            var html = [];
                            html.push('<h3>' + (menuName || '高考热点') + '</h3>');
                            html.push('<ul>');
                            for (var i = 0, len = biz.length; i < len; i++) {
                                html.push('<li><a target="_blank" href="/consult/gk_hot_detail.jsp?id=' + biz[i].id + '&menuName='+ menuName +'&code='+ codes +'">' + biz[i].title + '</a></li>');
                            }
                            html.push('</ul>');
                            $('#ask_list').html(html.join(''));
                        }
                    }
                });
            }
        }
    }


    $(document).ready(function() {
        var id = getUrLinKey('id');
        getArticleDetile(id);
        getRightInfo();

        $('#search').on('click', function(e) {
            var val = $('#keywords').val();
            window.location.href = '/consult/gk_hot.jsp?val=' + val;
        });
    });
    (function autoHeight(){
        var b_width = Math.max(document.body.scrollWidth,document.body.clientWidth);
        var b_height = Math.max(document.body.scrollHeight,document.body.clientHeight);
        var c_iframe = document.getElementById("c_iframe");
        c_iframe.src = c_iframe.src + "#" + b_width + "|" + b_height;  // 这里通过hash传递b.htm的宽高
    })();

});


