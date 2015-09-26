/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    var Profession = {
        curPage:1,
        totalPage:0,
        render: function(data) {
            var batch = [], classify = [], profession = [];
            var i = 0, len = data.length;

            batch.push('<a class="both active">全部</a>');
            classify.push('<a class="both active">全部</a>');
            profession.push('<a class="both active">全部</a>');

            for (; i < len; i++) {
                batch.push('<a data-id="' + data[i].id + '">' + data[i].name + '</a>');
                var subjectType = data[i].subjectType;

                for (var j = 0, jlen = subjectType.length; j < jlen; j++) {
                    classify.push('<a class="' + data[i].id + '" data-id="' + subjectType[j].id + '">' + subjectType[j].name + '</a>');

                    var majoredType = subjectType[j].majoredType;

                    for (var n = 0, nlen = majoredType.length; n < nlen; n++) {
                        profession.push('<a class="' + subjectType[j].id + '" data-id="' + majoredType[n].id + '">' + majoredType[n].name + '</a>');
                    }
                }
            }
            $('#batch').html(batch.join(''));
            $('#classify').html(classify.join(''));
            $('#profession').html(profession.join(''));
            this.addEventHandle();
            this.getProfession(1);
        },
        addEventHandle: function() {
            var that = this;
            $('#batch a').on('click', function(e) {
                if ($(this).hasClass('active')) {
                    return;
                }
                $(this).addClass('active').siblings().removeClass('active');
                var id = $(this).attr('data-id');
                if (id) {
                    $('#classify a.' + id).show().siblings().hide();
                } else {
                    $('#classify a').show();
                }
                $('#classify a.both').show().addClass('active').siblings().removeClass('active');
                $('#profession_content').hide();
                $('#profession a').removeClass('active');
                that.getProfession(1);
            });

            $('#classify a').on('click', function(e) {

                if ($(this).hasClass('active')) {
                    return;
                }
                $(this).addClass('active').siblings().removeClass('active');
                var id = $(this).attr('data-id');
                $('#profession a').removeClass('active');
                if (id) {
                    $('#profession_content').show();
                    $('#profession a').hide();
                    $('#profession a.' + id).show();
                    $('#profession a.both').show().addClass('active');
                } else {
                    $('#profession_content').hide();
                }
                that.getProfession(1);
            });

            $('#profession a').on('click', function(e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    that.getProfession(1);
                    return;
                }
            });
        },
        getOptions: function() {
            var that = this;
            //$.get('', function(data) {
            //    if ('0000000' === data.rtnCode) {
            //        that.render(data.bizData.batchType);
            //    }
            //});
            that.render(testData.batchType);
        },
        getProfession: function(pageNo) {
            var batchTypeId = $('#batch a.active').attr('data-id');
            var batchTypeName = $('#batch a.active').text();
            if ('全部' === batchTypeName) {
                batchTypeName = '';
            }

            var subjectTypeId = $('#classify a.active').attr('data-id');
            var subjectTypeName = $('#classify a.active').text();

            if ('全部' === subjectTypeName) {
                subjectTypeName = '';
            }

            var professionDOM = $('#profession a.active');
            var majoredTypeId = '',
                majoredTypeName = '';
            if (professionDOM[0]) {
                majoredTypeId = professionDOM.attr('data-id');
                majoredTypeName = professionDOM.text();

                if ('全部' === majoredTypeName) {
                    majoredTypeName = '';
                }
            }

            var search = $('#search').val();

            var Url = '';

            var url = Url + '?batchTypeId=' + batchTypeId + '&batchTypeName=' + batchTypeName
                + '&subjectTypeId=' + subjectTypeId + '&subjectTypeName=' + subjectTypeName
                + '&majoredTypeId=' + majoredTypeId + '&majoredTypeName=' + majoredTypeName
                + '&pageSize=10&pageNo=' + pageNo + '&search=' + search;
            var that = this;
            //$.get(url, function(data) {
            //    if ('0000000' === data.rtnCode) {
            //        var schoolList = data.bizData.subjectList;
            //        that.renderProfessionList(schoolList);
            //        if (pageNo == 1) {
            //            that.renderPage(1, data.bizData.pageCount);
            //        }
            //    }
            //});

            var schoolList = profession.subjectList;
            that.renderProfessionList(schoolList);
            if (pageNo == 1) {
                that.renderPage(1, profession.pageCount);
            }
        },
        renderProfessionList: function(data) {
            var html = [], i = 0, len = data.length;
            html.push('<table border="0" cellpadding="0" cellspacing="0">'
                + '<thead>'
                + '<tr>'
                + '<th class="name">专业名称</th>'
                + '<th>计划人数</th>'
                + '<th>学制</th>'
                + '<th>外语语种</th>'
                + '<th>收费标准</th>'
                + '<th>开设院校</th>'
                + '</tr>'
                + '</thead>'
                + '<tbody>');
            for (; i < len; i++) {
                var trClass = i % 2 != 0 ? 'active' : '';
                html.push('<tr class="' + trClass + '">'
                    + '<td class="name"><a href="/consult/profession_detile.jsp?id=' + data[i].code + '">' + data[i].name + '</a></td>'
                    + '<td>' + data[i].planNumber + '</td>'
                    + '<td>' + data[i].schoolLength + '</td>'
                    + '<td>' + data[i].foreginLanguage + '</td>'
                    + '<td>' + data[i].feeStandard+ '</td>'
                    + '<td>'
                    + '<a href="/consult/school_detile.jsp?id=' + data[i].universityCode + '">' + data[i].universityName + '</a>'
                    + '</td>'
                    + '</tr>');
            }
            html.push('</tbody>'
                + '</table>');
            $('#profession_list').html(html.join(''));
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
                        that.getProfession(that.curPage);
                    }
                }
            });
        }
    };

    var testData = {
        "batchType": [
            {
                "id": 1,
                "name": "一批本科",
                "subjectType": [
                    {
                        "id": 1,
                        "name": "试验班1",
                        "majoredType": [
                            {
                                "id": 1,
                                "name": "科学技术类"
                            }, {
                                "id": 2,
                                "name": "科学技术类2"
                            }
                        ]
                    }
                ]
            },
            {
                "id": 2,
                "name": "二批本科",
                "subjectType": [
                    {
                        "id": 2,
                        "name": "试验班2",
                        "majoredType": [
                            {
                                "id": 3,
                                "name": "医学技术类"
                            }, {
                                "id": 4,
                                "name": "医学技术类2"
                            }
                        ]
                    }
                ]
            }
        ]
    };

    var profession = {
        "pageCount": 20,
        "currentPage": 2,
        "subjectList": [
            {
                "code": 1,
                "name": "土木工程",
                "planNumber": 12,
                "schoolLength": "四年制",
                "foreginLanguage": "英语",
                "feeStandard": "4500/年",
                "universityCode": 1022,
                "universityName": "解放军理工大学"
            },
            {
                "code": 2,
                "name": "软件工程",
                "planNumber": 10,
                "schoolLength": "四年制",
                "foreginLanguage": "英语",
                "feeStandard": "4500/年",
                "universityCode": 1022,
                "universityName": "解放军理工大学"
            }
        ]
    }

    $(document).ready(function() {
        Profession.getOptions();
    });
});
