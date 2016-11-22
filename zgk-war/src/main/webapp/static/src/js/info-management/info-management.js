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

    },
    expertInfo: function (title) {
        var that = this;
        var expertInfoHtml = [];
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['70%', '80%'],
            content: '/expert-info',
            cancel: function () {

            }
        });
    }
};