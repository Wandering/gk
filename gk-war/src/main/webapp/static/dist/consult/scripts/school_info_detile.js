/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    var Info = {
        getBasicInfo: function(code) {
            var that = this;
            $.ajax({
                type: 'post',
                url: '/university/getUniversityDetail.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    code:code
                },
                dataType: 'json',
                success: function(data) {
                    if ('0000000' === data.rtnCode) {
                        that.renderInfo(data.bizData);
                    }
                },
                error: function(data) {
                }
            });
        },
        renderInfo: function(obj) {
            $('#info_content').html('<img class="fl" src="' + (obj.universityImage || '/static/src/common/images/kqbk_banner_default.png') + '" />'
                                    + '<div class="info">'
                                        + '<ul>'
                                            + '<li class="school-name">' + (obj.name || '') + '</li>'
                                            + '<li>所在省份：' + (obj.provinceName || '') + '</li>'
                                            + '<li>院校隶属：' + (obj.subjection || '') + '</li>'
                                            + '<li>学历层次：' + (obj.educatLevel || '') + '</li>'
                                            + '<li>院校特征：' + (obj.property || '') + '</li>'
                                            + '<li>院校类型：' + (obj.universityType || '') + '</li>'
                                            + '<li>院校网址：<a href="' + obj.url + '">' + (obj.url || '') + '</a></li>'
                                            + '<li>院校地址：' + (obj.address || '') + '</li>'
                                            + '<li>联系电话：<span>' + (obj.contactPhone || '') + '</span></li>'
                                        + '</ul>'
                                    + '</div>');
        },
        getSchoolInfo: function(code) {
            var that = this;
            $.ajax({
                type: 'post',
                url: '/university/getEnrollInfo.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    code:code
                },
                dataType: 'json',
                success: function(data) {
                    if ('0000000' === data.rtnCode) {
                        that.renderSchool(data.bizData.enrollInfo);
                    }
                },
                error: function(data) {
                }
            });
        },
        renderSchool: function(data) {
            var tab = [];
            var tabContent = [];
            for (var i = 0, len = data.length; i < len; i++) {
                tab.push('<li>' + data[i].title + '</li>');
                var infos = data[i].infos;
                tabContent.push('<div style="display:none" class="school-table mt20" id="school_table_' + i + '">'
                                + '<table border="0" cellpadding="0" cellspacing="0">'
                                    + '<thead>'
                                        + '<tr>'
                                            + '<th></th>'
                                            + '<th>计划数</th>'
                                            + '<th>录取数</th>'
                                            + '<th>最高分</th>'
                                            + '<th>最高位次</th>'
                                            + '<th>最低分</th>'
                                            + '<th>最低位次</th>'
                                            + '<th>平均分</th>'
                                            + '<th>平均分位次</th>'
                                        + '</tr>'
                                    + '</thead>'
                                    + '<tbody>');
                for (var j = 0, infolen = infos.length; j < infolen; j++) {
                    tabContent.push('<tr>'
                                    + '<td>' + (infos[j].subjectName || '') + '</td>'
                                    + '<td>' + (infos[j].planNumber || '') + '</td>'
                                    + '<td>' + (infos[j].enrollNumber || '') + '</td>'
                                    + '<td>' + (infos[j].highestScore || '') + '</td>'
                                    + '<td>' + (infos[j].highestRank || '') + '</td>'
                                    + '<td>' + (infos[j].lowestScore || '') + '</td>'
                                    + '<td>' + (infos[j].lowestRank || '') + '</td>'
                                    + '<td>' + (infos[j].averageScore || '') + '</td>'
                                    + '<td>' + (infos[j].averageRank || '') + '</td>'
                                + '</tr>');
                }
                tabContent.push('</tbody>'
                        + '</table>'
                    + '</div>');
            }

            $('#tabs_list_last').html(tab.join(''));
            $('#last_content').html(tabContent.join(''));
            $('#tabs_list_last li').first().addClass('active');
            $('#school_table_0').show();
            this.addSchoolEventHandle();
        },
        addSchoolEventHandle: function() {
            $('#tabs_list_last li').on('mouseover', function() {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    $('#school_table_' + $(this).index()).show().siblings().hide();
                }
            });
        },
        getEnroll: function(code) {
            var that = this;
            $.ajax({
                type: 'post',
                url: '/university/getEnrollPlan.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    code:code
                },
                dataType: 'json',
                success: function(data) {
                    if ('0000000' === data.rtnCode) {
                        that.renderEnroll(data.bizData);
                    }
                },
                error: function(data) {
                }
            });
        },
        renderEnrollTable: function(infos) {
            var tabContent = [];
            tabContent.push('<table border="0" cellpadding="0" cellspacing="0">'
                + '<thead>'
                + '<tr>'
                + '<th>专业名称</th>'
                + '<th>批次</th>'
                + '<th>科类</th>'
                + '<th>计划人数</th>'
                + '<th>学制</th>'
                + '<th>收费标准</th>'
                + '</tr>'
                + '</thead>'
                + '<tbody>');
            for (var j = 0, infolen = infos.length; j < infolen; j++) {
                tabContent.push('<tr>'
                    + '<td>' + (infos[j].majoredName || '') + '</td>'
                    + '<td>' + (infos[j].batch || '') + '</td>'
                    + '<td>' + (infos[j].subject || '') + '</td>'
                    + '<td>' + (infos[j].planNumber || '') + '</td>'
                    + '<td>' + (infos[j].schoolLength || '') + '</td>'
                    + '<td>' + (infos[j].feeStandard || '') + '</td>'
                    + '</tr>');
            }
            tabContent.push('</tbody>'
                + '</table>');
            return tabContent.join('');
        },
        renderEnroll: function(ret) {
            var data = ret.enrollPlan;
            var tab = [];
            var tabContent = [];
            for (var i = 0, len = data.length; i < len; i++) {
                var paramName = 'enrollData' + i;
                tab.push('<li data-saveData="' + paramName + '">' + data[i].title + '</li>');
                var infos = data[i].planInfos;
                this[paramName] = infos;
                tabContent.push('<div style="display:none" class="school-table mt20" id="enroll_table_' + i + '">');
                tabContent.push(this.renderEnrollTable(infos));
                tabContent.push('</div>');
            }

            tab.push('<li>招生章程</li>');
            tabContent.push('<div style="display:none" class="school-table mt20" id="enroll_table_' + i + '">' + (ret.entroIntro || '') + '</div>');
            i++;
            tab.push('<li>院校简介</li>');
            tabContent.push('<div style="display:none" class="school-table mt20" id="enroll_table_' + i + '">' + (ret.universityIntro || '') + '</div>');

            $('#tabs_list_enroll').html(tab.join(''));
            $('#enroll_content').html(tabContent.join(''));
            $('#tabs_list_enroll li').first().addClass('active');
            var text = $('#tabs_list_enroll li.active').text();
            this.setCategory(text);
            $('#enroll_table_0').show();
            this.addEnrollEventHandle();
            this.categoryHandle();
        },
        addEnrollEventHandle: function() {
            var that = this;
            $('#tabs_list_enroll li').on('mouseover', function() {
                if ($(this).hasClass('fr')) {
                    return;
                }
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    var text = $(this).text();
                    var index = $(this).index();
                    that.setCategory(text, $(this));
                    $('#enroll_table_' + index).show().siblings().hide();
                }
            });
        },
        setCategory: function(text, activeDom) {
            if ('招生章程' === text || '院校简介' === text) {
                $('#category').hide();
            } else {
                $('#category button').removeClass('active');
                if (activeDom) {
                    var categoryId = activeDom.attr('data-categoryId');
                    if (categoryId) {
                        $('#category button[data-id="' + categoryId + '"]').addClass('active');
                    }
                }
                $('#category').show();
            }
        },
        categoryHandle: function() {
            var that = this;
            $('#category button').on('click', function(e) {
                $(this).addClass('active').siblings().removeClass('active');
                $('#tabs_list_enroll li.active').attr('data-categoryId', $(this).attr('data-id'));
                var categoryText = $(this).text();
                that.filterEnroll(categoryText, $('#tabs_list_enroll li.active').index());
            });
        },
        filterEnroll: function(categoryText, menuId) {
            var data = this['enrollData' + menuId];
            var NewData = [];
            for (var i = 0; i < data.length; i++) {
                if (categoryText === data[i].subject) {
                    NewData.push(data[i]);
                }
            }
            if (NewData.length > 0) {
                $('#enroll_table_' + menuId).html(this.renderEnrollTable(NewData));
            } else {
                $('#enroll_table_' + menuId).html('<p style="padding: 20px 0; text-align: center">没有符合相关的信息！</p>');
            }
        }
    };

    var BasicInfoForTest = {
        "code": 0012,
        "name": "北京大学",
        "subjection": "教育部直属",
        "educatLevel": "本科",
        "universityType": "综合",
        "property": "985,211,研",
        "address": "北京市海淀区颐和园路5号",
        "contactPhone": "010-62751407",
        "universityImage": "http://himg.bdimg.com/sys/portrait/item/c68b4a737048696265726e6174655374d718.jpg",
        "provinceName": "北京",
        "url": "http://www.baidu.com"
    };

    var getEnrollInfo = {
        "enrollInfo": [
            {
                "title": "2014年招生情况",
                "infos": [
                    {
                        "subjectName": "文科",
                        "planNumber": 20,//计划人数
                        "enrollNumber": 10,//录取数
                        "highestScore": 666,//最高分
                        "highestRank": 1,//最高位次
                        "lowestScore": 500,//最低分
                        "lowestRank": 26,///最低位次
                        "averageScore": 580,//平均分
                        "averageRank": 12//平均分位次
                    },
                    {
                        "subjectName": "理科",
                        "planNumber": 15,//计划人数
                        "enrollNumber": 8,//录取数
                        "highestScore": 616,//最高分
                        "highestRank": 1,//最高位次
                        "lowestScore": 520,//最低分
                        "lowestRank": 16,///最低位次
                        "averageScore": 560,//平均分
                        "averageRank": 10//平均分位次
                    }
                ]
            }, {
                "title": "2013年招生情况",
                "infos": [
                    {
                        "subjectName": "文科",
                        "planNumber": 20,//计划人数
                        "enrollNumber": 20,//录取数
                        "highestScore": 566,//最高分
                        "highestRank": 1,//最高位次
                        "lowestScore": 600,//最低分
                        "lowestRank": 26,///最低位次
                        "averageScore": 680,//平均分
                        "averageRank": 14//平均分位次
                    },
                    {
                        "subjectName": "理科",
                        "planNumber": 15,//计划人数
                        "enrollNumber": 10,//录取数
                        "highestScore": 616,//最高分
                        "highestRank": 1,//最高位次
                        "lowestScore": 520,//最低分
                        "lowestRank": 16,///最低位次
                        "averageScore": 560,//平均分
                        "averageRank": 10//平均分位次
                    }
                ]
            }
        ]
    };

    var getEnrollPlan = {
        "enrollPlan": [
            {
                "title": "2015招生计划",
                "planInfos": [
                    {
                        "majoredName": "材料类",
                        "batch": "一批本科",
                        "subject": "理科",
                        "planNumber": 20,//计划人数
                        "schoolLength": "四年",//学制
                        "feeStandard": "12000/年"//收费标准
                    }
                ]
            },
            {
                "title": "2014招生计划",
                "planInfos": [
                    {
                        "majoredName": "材料类",
                        "batch": "一批本科",
                        "subject": "理科",
                        "planNumber": 10,//计划人数
                        "schoolLength": "四年",//学制
                        "feeStandard": "10000/年"//收费标准
                    }
                ]
            },

        ],
        "entroIntro": "<p> 北京大学招生简介</p>",//招生简介，一段html代码
        "universityIntro": "<p> 北京大学介绍</p>"//院校简介，一段html代码
    };

    $(document).ready(function() {
        var code = getUrLinKey('id');
        Info.getBasicInfo(code);
        Info.getSchoolInfo(code);
        Info.getEnroll(code);
    });
});