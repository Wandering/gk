/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    var School = {
        render: function(ele, data) {
            var html = [];
            var i = 0,len = data.length;
            html.push('<a class="active" id="0">全部</a>');
            for (; i < len; i++) {
                html.push('<a id="' + data[i].id + '">' + data[i].name + '</a>');
            }
            ele.html(html.join(''));
        },
        show: function(eleId, data) {
            this.render($('#' + eleId), data);
        },
        getData: function() {
            $.get('', function(data) {
                $.each(['provinces','universityType', 'universityBatch', 'universityFeature'], function(i, value) {

                })
            });
        },
        renderSchool: function(data) {
            var html = [], i = 0, len = data.length;
            html.push('<table border="0" cellpadding="0" cellspacing="0">'
                        + '<thead>'
                            + '<tr>'
                                + '<th class="name">院校名称</th>'
                                + '<th>所在地区</th>'
                                + '<th>院校类型</th>'
                                + '<th>院校隶属</th>'
                                + '<th>院校特征</th>'
                                + '<th>院校信息</th>'
                            + '</tr>'
                        + '</thead>'
                        + '<tbody>');
            for (; i < len; i++) {
                var trClass = i % 2 == 0 ? 'active' : '';
                html.push('<tr class="' + trClass + '">'
                                + '<td class="name">' + data[i].name + '</td>'
                                + '<td>' + data[i].provinceName + '</td>'
                                + '<td>' + data[i].universityType + '</td>'
                                + '<td>' + data[i].subjection + '</td>'
                                + '<td>' + data[i].property+ '</td>'
                                + '<td>'
                                    + '<a href="/consult/school_detile.jsp?id=' + data[i].id + '">查看详情</a>'
                                + '</td>'
                            + '</tr>');
            }
            html.push('</tbody>'
                + '</table>');
            $('#school_list').html(html.join(''));
        },
        getSchoolList: function(pageNo) {
            var provinces = $('#provinces a.active').attr('id');
            var universityType = $('#universityType a.active').attr('id');
            var universityBatch = $('#universityBatch a.active').attr('id');
            var universityFeature = $('#universityFeature a.active').attr('id');
        }
    }

    $(document).ready(function() {

    });
});
