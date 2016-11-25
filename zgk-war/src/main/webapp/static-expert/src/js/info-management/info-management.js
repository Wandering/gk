/**
 * Created by machengcheng on 16/11/22.
 */

$(function () {

    var booking = new Booking();
    booking.setIntervalDate(2016,11,25);
    $(document).on('click', '.user-btn', function () {
        booking.userInfo('用户信息');
    });

});

function Booking () {
    this.init();
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
                var data = res.bizData;
                if (data.rows.length != 0) {
                    var parentItem = [];
                    $.each(data.rows, function (i, k) {
                        parentItem.splice(0, parentItem.length);
                        parentItem.push('<tr>');
                        switch (parseInt(k.serviceState)) {
                            case 0:
                                parentItem.push('<td><a href="javascript: void(0);" class="red-link">' + k.serviceName + '</a></td>');
                                break;
                            case 1:
                                parentItem.push('<td><a href="javascript: void(0);" class="gray-link">' + k.serviceName + '</a></td>');
                                break;
                            case 2:
                                parentItem.push('<td>' + k.serviceName + '</td>');
                                break;
                            case 3:
                                parentItem.push('<td><a href="javascript: void(0);" class="gray-link">' + k.serviceName + '</a></td>');
                                break;
                            case 4:
                                parentItem.push('<td><a href="javascript: void(0);" class="gray-link">' + k.serviceName + '</a></td>');
                                break;
                            default:
                                parentItem.push('<td><a href="javascript: void(0);" class="blue-link">' + k.serviceName + '</a></td>');
                                break;
                        }
                        switch (parseInt(k.serviceState)) {
                            case 0:
                                parentItem.push('<td><a href="/consult-specialist.html" class="blue-link">还未预约，快去预约吧！</a></td>');
                                break;
                            case 1:
                                parentItem.push('<td><span style="color: #F6A623;">预约成功</span>—服务中—结束</td>');
                                break;
                            case 2:
                                parentItem.push('<td>预约成功—<span style="color: #F6A623;">服务中</span>—结束</td>');
                                break;
                            case 3:
                                parentItem.push('<td>预约成功—服务中—<span style="color: #F6A623;">结束</span></td>');
                                break;
                            case 4:
                                parentItem.push('<td>预约成功—服务中—<span style="color: #F6A623;">结束</span></td>');
                                break;
                            default:
                                parentItem.push('<td><a href="javascript: void(0);" class="blue-link">还未预约，快去预约吧！</a></td>');
                                break;
                        }
                        if (k.customer) {
                            parentItem.push('<td><a href="javascript: void(0);" oid="' + k.id + '" class="user-btn">' + k.customer + '</a></td>');
                        } else {
                            parentItem.push('<td>-</td>');
                        }
                        if (k.serviceTime) {
                            parentItem.push('<td>' + k.serviceTime + '</td>');
                        } else {
                            parentItem.push('<td>-</td>');
                        }
                        if (k.channel) {
                            parentItem.push('<td>' + k.channel + '</td>');
                        } else {
                            parentItem.push('<td>-</td>');
                        }
                        if (k.isInto == 2) {
                            parentItem.push('<td><a href="/expert/admin/experts-play.do?stuId='+ k.customerId  +'" class="video-btn">进入视频</a></td>');
                        } else {
                            parentItem.push('<td><a href="javascript: void(0);" class="no-video-btn">进入视频</a></td>');
                        }
                        parentItem.push('</tr>');
                        $("#booking-list").append(parentItem.join(''));
                    });
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    userInfo: function (title) {
        var that = this;
        var userInfoHtml = [];
        layer.open({
            type: 2,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['900px', '550px'],
            content: '/expert/admin/user-info.do',
            cancel: function () {

            }
        });
    },
    setIntervalDate:function(year,month,day){


    }
};



