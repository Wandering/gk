/**
 * Created by kepeng on 15/9/25.
 */

define(function(require) {
    var $ = require('$');
    require('swiper');

    var School = {
        curPage: 1,
        totalPage: 0,
        render: function(ele, data) {
            var html = [];
            var i = 0,
                len = data.length;
            html.push('<a class="active" id="0">全部</a>');
            for (; i < len; i++) {
                html.push('<a id="' + data[i].id + '">' + data[i].name + '</a>');
            }
            ele.html(html.join(''));
        },
        show: function(eleId, data) {
            this.render($('#' + eleId), data);
            this.addEventForOption();
        },
        getData: function() {
            var that = this;
            //$.get('', function(data) {
            //    if ('0000000' === data.rtnCode) {
            //        $.each(['provinces', 'universityType', 'universityBatch', 'universityFeature'], function(i, value) {
            //            that.show(value, data.bizData[value]);
            //        })
            //    }
            //});

            $.each(['provinces', 'universityType', 'universityBatch', 'universityFeature'], function(i, value) {
                that.show(value, testData[value]);
            });
            this.getSchoolList(1);
        },
        addEventForOption: function() {
            var that = this;
            $('.options a').on('click', function(e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    that.getSchoolList(1);
                }
            });
        },
        renderSchool: function(data) {
            var html = [],
                i = 0,
                len = data.length;
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
                var trClass = i % 2 != 0 ? 'active' : '';
                html.push('<tr class="' + trClass + '">'
                                + '<td class="name">' + data[i].name + '</td>'
                                + '<td>' + data[i].provinceName + '</td>'
                                + '<td>' + data[i].universityType + '</td>'
                                + '<td>' + data[i].subjection + '</td>'
                                + '<td>' + data[i].property + '</td>'
                                + '<td>' + '<a href="/consult/school_detile.jsp?id=' + data[i].code + '">查看详情</a>' + '</td>'
                            + '</tr>');
            }
            html.push('</tbody>' + '</table>');
            $('#school_list').html(html.join(''));
        },
        getSchoolList: function(pageNo) {
            var provinces = $('#provinces a.active').attr('id');
            var provincesText = $('#provinces a.active').text();
            if ('全部' === provincesText) {
                provincesText = '';
            }
            var universityType = $('#universityType a.active').attr('id');
            var universityTypeText = $('#universityType a.active').text();
            if ('全部' === universityTypeText) {
                universityTypeText = '';
            }
            var universityBatch = $('#universityBatch a.active').attr('id');
            var universityBatchText = $('#universityBatch a.active').text();
            if ('全部' === universityBatchText) {
                universityBatchText = '';
            }
            var universityFeature = $('#universityFeature a.active').attr('id');
            var universityFeatureText = $('#universityFeature a.active').text();
            if ('全部' === universityFeatureText) {
                universityFeatureText = '';
            }
            var search = $('#school_serach').val();
            var URL= '';
            var url = URL + '?provinces=' + provinces + '&provincesText=' + provincesText + '&universityType=' + universityType + '&universityTypeText=' + universityTypeText + '&universityBatch=' + universityBatch + '&universityBatchText=' + universityBatchText + '&universityFeature=' + universityFeature + '&universityFeatureText=' + universityFeatureText + '&pageSize=10&pageNo=' + pageNo + '&searchName=' + search;
            var that = this;
            //$.get(url, function(data) {
            //    if ('0000000' === data.rtnCode) {
            //        var schoolList = data.bizData.schoolList;
            //        that.renderSchool(schoolList);
            //        if (pageNo == 1) {
            //            that.renderPage(1, data.bizData.schoolCount);
            //            var startNum = (pageNo - 1) * 10 + 1;
            //            $('.startNum').text(startNum);
            //
            //        }
            //    }
            //});

            var schoolList = schooldata.schoolList;
            that.renderSchool(schoolList);
            if (pageNo == 1) {
                that.renderPage(1, schooldata.schoolCount);
            }
        },
        renderPage: function(curPage, totals) {
            this.totalPage = Math.ceil(totals / 10);
            var pageHtml = [];
            var startNum = (curPage - 1) * 10 + 1;
            pageHtml.push('<span class="record">共' + totals + '条记录 <span class="startNum">' + startNum + '</span>/' + totals + '</span>');
            pageHtml.push('<a class="previous-page">上一页</a>');
            for (var i = 0; i < this.totalPage; i++) {
                var page = i + 1;
                if (curPage == page) {
                    pageHtml.push('<a class="active">' + page + '</a>');
                } else {
                    pageHtml.push('<a>' + page + '</a>');
                }
            }
            pageHtml.push('<a class="next-page">下一页</a>');
            $('#page').html(pageHtml.join(''));
            this.pageEventHandle();
        },
        pageEventHandle: function() {
            var that = this;
            $('#page a').on('click', function(e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    if ($(this).hasClass('previous-page')) {
                        that.curPage--;
                    } else if ($(this).hasClass('next-page')) {
                        that.curPage++;
                    } else {
                        that.curPage = parseInt($(this).text());
                    }

                    if (that.curPage > 0 && that.curPage <= that.totalPage) {
                        var startNum = (that.curPage - 1) * 10 + 1;
                        $('.startNum').text(startNum);
                        that.getSchoolList(that.curPage);
                    }
                }
            });
        },
        getSearch: function() {
            this.curPage = 1;
            this.getSchoolList(1);
        }
    }

    var testData = {
        "provinces": [{
            "id": 1,
            "name": "北京市"
        }, {
            "id": 2,
            "name": "天津市"
        }],
        //院校类型
        "universityType": [{
            "id": 1,
            "name": "综合"
        }, {
            "id": 2,
            "name": "工科"
        }],
        //院校批次
        "universityBatch": [{
            "id": 1,
            "name": "一批本科"
        }, {
            "id": 2,
            "name": "二批本科"
        }],
        //院校特征
        "universityFeature": [{
            "id": 1,
            "name": "985"
        }, {
            "id": 2,
            "name": "211"
        }, {
            "id": 3,
            "name": "研"
        }]
    };

    var schooldata = {
        "schoolCount":20,
        "currentPage":1,
        "schoolList": [
            {
                "id": 1,
                "code": 0012,
                "name": "北京大学1",
                "provinceName": "北京",
                "universityType": "综合",
                "subjection": "地方政府所属",
                "property": "985,211",
                "url":"http://www.baidu.com"
            },
            {
                "id": 2,
                "code": 0013,
                "name": "北京大学2",
                "provinceName": "北京",
                "universityType": "综合",
                "subjection": "地方政府所属",
                "property": "985,211",
                "url":"http://www.baidu.com"
            },
            {
                "id": 3,
                "code": 0014,
                "name": "北京大学3",
                "provinceName": "北京",
                "universityType": "综合",
                "subjection": "地方政府所属",
                "property": "985,211",
                "url":"http://www.baidu.com"
            }
    ]
}

    $(document).ready(function() {
        School.getData();
        $('#search_button').on('click', function(e) {
            var search = $('#school_serach').val();
            if (search) {
                School.getSearch();
            }
        })
    });
});