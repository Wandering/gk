/**
 * Created by kepeng on 15/10/10.
 */

define(function (require, exports, module) {
    module.exports = function(msg) {
        return '<div class="page-error-tip">' +
                    '<img src="http://cdn.gaokao360.net/static/global/common/images/no-data-logo.png" />' +
                    '<p class="ta">' + msg + '</p>' +
                '</div>';
    }
});
