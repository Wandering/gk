/**
 * Created by kepeng on 15/9/25.
 */

define(function(require) {
    var $ = require('$');
    require('backToTop');

    var School = {
        curPage: 1,
        totalPage: 0,
        render: function(eleId, data) {
            var html = [];
            var i = 0,
                len = data.length;
            if ('universityFeature' !== eleId) {
                html.push('<a class="active" id="0">全部</a>');
            }

            for (; i < len; i++) {
                html.push('<a id="' + data[i].id + '">' + data[i].name + '</a>');
            }
            $('#' + eleId).html(html.join(''));
        },
        show: function(eleId, data) {
            this.render(eleId, data);
            this.addEventForOption();
        },
        getData: function() {
            var that = this;
            $.get('/university/getInitInfo.do', function(data) {
                if ('0000000' === data.rtnCode) {
                    $.each(['provinces', 'universityType', 'universityBatch', 'universityFeature'], function(i, value) {
                        that.show(value, data.bizData[value]);
                    });
                    that.getSchoolList(1);
                }
            });
        },
        addEventForOption: function() {
            var that = this;
            $('.options a').on('click', function(e) {
                var parentId = $(this).parent().attr('id');
                that.curPage = 1;
                if ('universityFeature' === parentId) {
                    $(this).toggleClass('active');
                    that.getSchoolList(1);
                } else {
                    if (!$(this).hasClass('active')) {
                        $(this).addClass('active').siblings().removeClass('active');
                        //that.getSchoolList(1);
                    }
                    that.getSchoolList(1);
                }

            });
        },
        renderSchool: function(data) {
            console.log(data[0].id)
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
                                + '<td class="name">' + (data[i].name || '') + '</td>'
                                + '<td>' + (data[i].provinceName || '') + '</td>'
                                + '<td>' + (data[i].universityType || '') + '</td>'
                                + '<td>' + (data[i].subjection || '') + '</td>'
                                + '<td>' + (data[i].property || '') + '</td>'
                                + '<td>' + '<a target="_blank" href="/consult/school_detile.jsp?id=' + data[i].id + '">查看详情</a>' + '</td>'
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

            var universityFeatureId = [];
            $.each($('#universityFeature a.active'), function(i, value) {
                universityFeatureId.push($(value).attr('id'));
            });
            var universityFeature = universityFeatureId.join(',') || 0;
            var universityFeatureValue = [];
            $.each($('#universityFeature a.active'), function(i, value) {
                universityFeatureValue.push($(value).text());
            });
            var universityFeatureText = universityFeatureValue.join(',');
            var search = $('#school_serach').val();
            var that = this;

            $.ajax({
                type: 'get',
                url: '/university/getUniversityList.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    provinceId:provinces,
                    provinceName:provincesText,
                    universityTypeId:universityType,
                    universityTypeName:universityTypeText,
                    universityBatchId:universityBatch,
                    universityBatchName:universityBatchText,
                    universityFeatureId:universityFeature,
                    universityFeatureName:universityFeatureText,
                    pageSize:10,
                    pageNo:pageNo,
                    searchName:search
                },
                dataType: 'json',
                success: function(data) {
                    if ('0000000' === data.rtnCode) {
                        var schoolList = data.bizData.list;
                        if (schoolList && schoolList.length) {
                            that.renderSchool(schoolList);
                            if (pageNo == 1) {
                                that.renderPage(1, data.bizData.count);
                            }
                        } else {
                            //$('#school_list').html('<p style="text-align: center">暂无信息！</p>');
                            var pageErrorTip = require('pageErrorTip');
                            $('#school_list').html(pageErrorTip('暂无相关数据'));
                            $('#page').html('');
                            $('.record').html('');
                        }
                    }
                },
                error: function(data) {
                }
            });
        },
        renderPage: function(curPage, totals) {
            this.totalPage = Math.ceil(totals / 10);
            var pageHtml = [];
            var startNum = (curPage - 1) * 10 + 1;
            $('.record').html('共' + totals + '条记录&nbsp;&nbsp;共' + this.totalPage + '页&nbsp;&nbsp;<span class="startNum">' + startNum + '</span>/' + totals);
            //pageHtml.push('<span class="record">共' + totals + '条记录 <span class="startNum">' + startNum + '</span>/' + totals + '</span>');
            pageHtml.push('<a class="previous-page">上一页</a>');
            var num = this.totalPage > 10 ? 10: this.totalPage;
            for (var i = 0; i < num; i++) {
                var page = i + 1;
                if (curPage == page) {
                    pageHtml.push('<a class="active ' + page + '">' + page + '</a>');
                } else {
                    pageHtml.push('<a class="' + page + '">' + page + '</a>');
                }
            }
            if (num != this.totalPage) {
                pageHtml.push('<span>...</span>');
            }
            pageHtml.push('<a class="next-page">下一页</a>');
            $('#page').html(pageHtml.join(''));
            this.pageEventHandle();
        },
        refreshPageShow: function(curPage) {
            var num = curPage + 2;
            num = num > this.totalPage ? this.totalPage : num;
            var minNum = this.totalPage > 10 ? 10 : this.totalPage;
            num = num < minNum ? minNum : num;
            var oldNum = num;
            var arryPage = [];
            for (var i = 0; i < 10; i++) {
                arryPage.push(num--);
            }
            arryPage.reverse();

            if (oldNum > 10) {
                arryPage[0] = 1;
                arryPage[1] = 2;
                arryPage[2] = '...';
            }
            if (oldNum <= this.totalPage - 2) {
                arryPage.push('...');
            }
            return arryPage;
        },
        refreshPage: function(curPage) {
            var arryPage = this.refreshPageShow(curPage);
            var pageHtml = [];
            pageHtml.push('<a class="previous-page">上一页</a>');
            for (var i = 0; i < arryPage.length; i++) {
                if (typeof arryPage[i] === 'string') {
                    pageHtml.push('<span>...</span>');
                } else {
                    pageHtml.push('<a class="' + arryPage[i] + '">' + arryPage[i] + '</a>');
                }
            }
            pageHtml.push('<a class="next-page">下一页</a>');
            $('#page').html(pageHtml.join(''));
            this.pageEventHandle();
        },
        pageEventHandle: function() {
            var that = this;
            $('#page a').off('click');
            $('#page a').on('click', function(e) {
                if ($(this).hasClass('previous-page')) {
                    if (that.curPage > 1) {
                        that.curPage--;
                    }
                    if (that.curPage > 0) {
                        if (!$('#page a.' + that.curPage)[0] && that.totalPage > 10) {
                            that.refreshPage(that.curPage);
                        }
                        $('#page a.' + that.curPage).addClass('active').siblings().removeClass('active');
                        var startNum = (that.curPage - 1) * 10 + 1;
                        $('.startNum').text(startNum);
                        that.getSchoolList(that.curPage);
                    }
                } else if ($(this).hasClass('next-page')) {
                    if (that.curPage < that.totalPage) {
                        that.curPage++;
                    }
                    if (that.curPage <= that.totalPage) {
                        if (!$('#page a.' + that.curPage)[0] && that.totalPage > 10) {
                            that.refreshPage(that.curPage);
                        }
                        $('#page a.' + that.curPage).addClass('active').siblings().removeClass('active');
                        var startNum = (that.curPage - 1) * 10 + 1;
                        $('.startNum').text(startNum);
                        that.getSchoolList(that.curPage);
                    }
                } else {
                    if (!$(this).hasClass('active')) {
                        that.curPage = parseInt($(this).text());
                        $(this).addClass('active').siblings().removeClass('active');
                        if (that.totalPage > 10) {
                            var nextPage = that.curPage + 1;
                            var prePage = that.curPage - 1;
                            if (nextPage <= that.totalPage && prePage > 0) {
                                if (!$('#page a.' + nextPage)[0] || !$('#page a.' + prePage)[0]) {
                                    that.refreshPage(that.curPage);
                                    $('#page a.' + that.curPage).addClass('active')
                                }
                            }
                        }

                        if (that.curPage > 0 && that.curPage <= that.totalPage) {
                            var startNum = (that.curPage - 1) * 10 + 1;
                            $('.startNum').text(startNum);
                            that.getSchoolList(that.curPage);
                        }
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
        });

        $('#school_serach').keydown(function(e) {
            if (e.keyCode == 13) {
                var search = $('#school_serach').val();
                if (search) {
                    School.getSearch();
                }
            }
        })

    });
});