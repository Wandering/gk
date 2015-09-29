/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    var getQueryStr = function(_url, _param) {
        var rs = new RegExp("(^|)" + _param + "=([^\&]*)(\&|$)", "g").exec(_url),
            tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    };

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
                    $('#classify a').hide();
                    $('#classify a.' + id).show();
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
            $.get('/majored/getInitInfo.do', function(data) {
                if ('0000000' === data.rtnCode) {
                    if (data.bizData.batchType) {
                        that.render(data.bizData.batchType);
                    }
                }
            });
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

            var that = this;
            $.ajax({
                type: 'post',
                url: '/majored/searchMajored.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    batchTypeId:batchTypeId,
                    batchTypeName:batchTypeName,
                    subjectTypeId:subjectTypeId,
                    subjectTypeName:subjectTypeName,
                    majoredTypeId:majoredTypeId,
                    majoredTypeName:majoredTypeName,
                    pageSize:10,
                    pageNo:pageNo,
                    searchName:search
                },
                dataType: 'json',
                success: function(data) {
                    if ('0000000' === data.rtnCode) {
                        var schoolList = data.bizData.list;
                        if (schoolList && schoolList.length > 0) {
                            that.renderProfessionList(schoolList);
                            if (pageNo == 1) {
                                that.renderPage(1, data.bizData.count);
                            }
                        } else {
                            $('#profession_list').html('<p style="text-align: center">暂无信息！</p>');
                            $('#page').html('');
                        }
                    }
                },
                error: function(data) {
                }
            });
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
                    + '<td class="name"><a href="/consult/profession_detile.jsp?id=' + data[i].code + '">' + (data[i].name || '') + '</a></td>'
                    + '<td>' + (data[i].planNumber || '') + '</td>'
                    + '<td>' + (data[i].schoolLength || '') + '</td>'
                    + '<td>' + (data[i].foreginLanguage || '') + '</td>'
                    + '<td>' + (data[i].feeStandard || '') + '</td>'
                    + '<td>'
                    + '<a target="_blank" href="/consult/school_detile.jsp?id=' + data[i].universityCode + '">' + (data[i].universityName || '') + '</a>'
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
            num = num < minNum ? minNum: num;
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
            $('#page a').on('click', function(e) {
                if ($(this).hasClass('previous-page')) {
                    that.curPage--;
                    if (that.curPage > 0) {
                        if (!$('#page a.' + that.curPage)[0] && this.totalPage > 10) {
                            that.refreshPage(that.curPage);
                        }
                        $('#page a.' + that.curPage).addClass('active').siblings().removeClass('active');
                        var startNum = (that.curPage - 1) * 10 + 1;
                        $('.startNum').text(startNum);
                        that.getProfession(that.curPage);
                    }
                } else if ($(this).hasClass('next-page')) {
                    that.curPage++;
                    if (that.curPage <= that.totalPage) {
                        if (!$('#page a.' + that.curPage)[0] && this.totalPage > 10) {
                            that.refreshPage(that.curPage);
                        }
                        $('#page a.' + that.curPage).addClass('active').siblings().removeClass('active');
                        var startNum = (that.curPage - 1) * 10 + 1;
                        $('.startNum').text(startNum);
                        that.getProfession(that.curPage);
                    }
                } else {
                    if (!$(this).hasClass('active')) {
                        that.curPage = parseInt($(this).text());
                        $(this).addClass('active').siblings().removeClass('active');
                        if (this.totalPage > 10) {
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
                            that.getProfession(that.curPage);
                        }
                    }
                }
            });
        },
        getSearch: function() {
            this.curPage = 1;
            this.getProfession(1);
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
        var keywords = decodeURIComponent(getQueryStr(window.location.href, 'val'));
        if (keywords) {
            $('#search').val(keywords);
        }
        $('#search_button').on('click', function() {
            var search = $('#search').val();
            if (search) {
                Profession.getSearch();
            }
        });
    });
});
