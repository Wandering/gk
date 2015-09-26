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
});
