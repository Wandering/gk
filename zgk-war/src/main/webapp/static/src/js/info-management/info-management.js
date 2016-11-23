/**
 * Created by machengcheng on 16/11/22.
 */

$(function () {

    var booking = new Booking();
    $(document).on('click', '.expert-btn', function () {
        booking.expertInfo('专家信息');
    });

});

function Booking () {

}
Booking.prototype = {
    constructor: Booking,
    init: function () {
        this.getOrderList();
    },
    getOrderList: function () {
        var that = this;
        Common.ajaxFun('/expert/admin/order/queryExpertOrder.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    expertInfo: function (title) {
        var that = this;
        var expertInfoHtml = [];
        layer.open({
            type: 2,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['900px', '550px'],
            content: '/expert/admin/expert-info.do',
            cancel: function () {

            }
        });
    }
};