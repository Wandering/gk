define(function (require) {
    var $ = require('$');

    var getQueryStr = function(_url, _param) {
        var rs = new RegExp("(^|)" + _param + "=([^\&]*)(\&|$)", "g").exec(_url),
            tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    };
    console.log(decodeURIComponent(getQueryStr(window.location.href, 'url')));

    var examPlayUrl = decodeURIComponent(getQueryStr(window.location.href, 'url'));
    var examPlaySwfHtml=''
        +'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1000" height="1024">'
        +'<param name="movie" value="'+ examPlayUrl +'" />'
        +'<param name="quality" value="high" />'
        +'<embed src="'+ examPlayUrl +'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="1000" height="1024"></embed>'
        +'</object>';
    $('#examPlaySwf').html(examPlaySwfHtml);
});
