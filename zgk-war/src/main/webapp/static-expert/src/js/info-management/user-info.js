/**
 * Created by machengcheng on 16/11/23.
 */

$(function(){

    var orderId = parseInt(parent.$('.user-btn').attr('oid'));
    if (orderId) {
        var userInfo = new UserInfo(orderId);
    }

});

function UserInfo (orderId) {
    this.orderId = orderId;
    this.init();
}
UserInfo.prototype = {
    constructor: UserInfo,
    init: function () {
        this.getUserInfo();
        this.getReportList();
    },
    getUserInfo: function () {
        var that = this;
        Common.ajaxFun('/expert/admin/customer/queryCustomerInfo.do', 'GET', {
            'orderId': that.orderId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                $('.user-name').html(data.customerName);
                $('#user-img').attr('src', data.imageUrl);
                $('#user-sex').html(data.sex);
                $('#user-type').html(data.majorTypeName);
                $('#user-grade').html(data.grade);
                $('#user-school').html(data.schoolName);
                $('#questionDesc').html(data.questionDesc);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    getReportList: function () {
        var that = this;
        Common.ajaxFun('/expert/admin/customer/queryCustomerApesk.do', 'GET', {
            'orderId': that.orderId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.length != 0) {
                    var reportHtml = [];
                    $.each(data, function (i, k) {
                        reportHtml.push('<tr>');
                        if (parseInt(k.acId) != 5) {
                            reportHtml.push('<td><a href="' + k.reportUrl + '" class="report-btn">' + k.apeskName + '</a></td>');
                        } else {
                            reportHtml.push('<td><a href="/expert/admin/report-info.do?uid=216" class="report-btn new-report">' + k.apeskName + '</a></td>');
                        }
                        reportHtml.push('<td>' + k.reportDate + '</td>');
                        reportHtml.push('</tr>');
                    });
                    $('#report-list').html(reportHtml.join(''));
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    }
};