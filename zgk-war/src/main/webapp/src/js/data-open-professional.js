/*
 * 学校信息详情切换
 * */

alert(99)
define(['../css/data/data-open-professional.css', 'jquery', 'handlebars', 'urlConfig', 'cookie'], function (openProfessionalCss, jq, handlebars, url, cookie) {
    //拉取开设专业
    var getLinkey = function getLinkey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    };

    var offset = 0,
        rows = 5;
    var data = {
        offset: offset,
        rows: rows,
        universityId: getLinkey('id')
    };
    getOpenProfess();
    $('.btn-next').on('click', function () {
        data.offset = data.offset + 1;
    });
    function getOpenProfess() {
        ajaxFun(url.getOpenProfessional, 'get', data, function (res) {
            if (res.rtnCode == '0000000') {
                var template = handlebars.compile($('#open-professional-tpl').html());
                $('#open-professional').append(template(res.bizData));
                if (res.bizData.featureMajorList.length < rows) {
                    $('.btn-next').hide();
                }
            }
        });
    }

    function ajaxFun(url, method, data, callback, callbackError) {
        if (cookie.getCookieValue('token')) {
            data.token = cookie.getCookieValue('token');
        }
        if (cookie.getCookieValue('token')) {
            data.userKey = 'zj';
        }
        var strParameter = '';
        for (var i in data) {
            strParameter += "&" + i + "=" + data[i];
        }
        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        var s;
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 0;
        var vesion = parseInt(Sys.ie);
        if (Sys.ie && vesion >= 8 && vesion < 10) {
            alert("ie:" + vesion)
            xdr = new XDomainRequest();
            xdr.open(method, url + "?browserType=IE" + strParameter);
            xdr.send();
            xdr.onload = function () {
                var reaultData = xdr.responseText;
                callback(reaultData);
            };
            xdr.onerror = function () {
                var reaultData = xdr.responseText;
                if (callbackError && typeof(callbackError) === "function") {
                    callbackError(reaultData);
                }
            }
        } else {
            $.ajax({
                url: url,
                type: method,
                data: data || {},
                success: function (res) {
                    var result = res;
                    callback(res);
                },
                error: function (res) {
                    var result = res;
                    if (callbackError && typeof(callbackError) === "function") {
                        callbackError(res);
                    }
                }
            });
        }
    };

});


