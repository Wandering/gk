/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    var Info = {
        getBasicInfo: function() {
            $.get('', function(data) {
                if ('0000000' === data.rtnCode) {

                }
            });
        },
        renderInfo: function(obj) {
            $('#info_content').html('<img class="fl" src="' + obj.universityImage + '" />'
                                    + '<div class="info">'
                                        + '<ul>'
                                            + '<li class="school-name">' + obj.name + '</li>'
                                            + '<li>所在省份：' + obj.provinceName + '</li>'
                                            + '<li>院校隶属：' + obj.subjection + '</li>'
                                            + '<li>学历层次：' + obj.educatLevel + '</li>'
                                            + '<li>院校特征：' + obj.property + '</li>'
                                            + '<li>院校类型：' + obj.universityType + '</li>'
                                            + '<li>院校网址：<a href="' + + '">' + + '</a></li>'
                                            + '<li>院校地址：' + obj.address + '</li>'
                                            + '<li>联系电话：<span>' + obj.contactPhone + '</span></li>'
                                        + '</ul>'
                                    + '</div>');
        },
        getSchoolInfo: function() {
            $.get('', function(data) {
                if ('0000000' === data.rtnCode) {

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
                for (var j = 0, infolen = infos.length; i < infolen; i++) {
                    tabContent.push('<tr>'
                                    + '<td>' + infos[j].subjectName + '</td>'
                                    + '<td>' + infos[j].planNumber + '</td>'
                                    + '<td>' + infos[j].enrollNumber + '</td>'
                                    + '<td>' + infos[j].highestScore + '</td>'
                                    + '<td>' + infos[j].highestRank + '</td>'
                                    + '<td>' + infos[j].lowestScore + '</td>'
                                    + '<td>' + infos[j].lowestRank + '</td>'
                                    + '<td>' + infos[j].averageScore + '</td>'
                                    + '<td>' + infos[j].averageRank + '</td>'
                                + '</tr>');
                }
                tabContent.push('</tbody>'
                        + '</table>'
                    + '</div>');
            }

            $('.tabs-list-last').html(tab.join(''));
            $('#last_content').html(tabContent.join(''));
            $('#school_table_0').show();
            this.addSchoolEventHandle();
        },
        addSchoolEventHandle: function() {
            $('.tabs-list-last li').on('mouseover', function() {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    $('#school_table_' + ($(this).index() - 1)).show().siblings().hide();
                }
            });
        },
        getEnroll: function() {
            $.get('', function(data) {
                if ('0000000' === data.rtnCode) {

                }
            });
        },
        renderEnroll: function(ret) {
            var data = ret.enrollPlan;
            var tab = [];
            var tabContent = [];
            for (var i = 0, len = data.length; i < len; i++) {
                tab.push('<li>' + data[i].title + '</li>');
                var infos = data[i].planInfos;
                tabContent.push('<div style="display:none" class="school-table mt20" id="enroll_table_' + i + '">'
                    + '<table border="0" cellpadding="0" cellspacing="0">'
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
                for (var j = 0, infolen = infos.length; i < infolen; i++) {
                    tabContent.push('<tr>'
                        + '<td>' + infos[j].majoredName + '</td>'
                        + '<td>' + infos[j].batch + '</td>'
                        + '<td>' + infos[j].subject + '</td>'
                        + '<td>' + infos[j].planNumber + '</td>'
                        + '<td>' + infos[j].schoolLength + '</td>'
                        + '<td>' + infos[j].feeStandard + '</td>'
                        + '</tr>');
                }
                tabContent.push('</tbody>'
                    + '</table>'
                    + '</div>');
            }

            tab.push('<li>招生章程</li>');
            tabContent.push('<div class="school-table mt20" id="enroll_table_' + i + '">' + ret.entroIntro + '</div>');
            i++;
            tab.push('<li>院校简介</li>');
            tabContent.push('<div class="school-table mt20" id="enroll_table_' + i + '">' + ret.universityIntro + '</div>');
            tab.push('<li class="fr">'
                        + '<button class="active">文科</button>'
                        + '<button>理科</button>'
                    + '</li>');

            $('.tabs-list-enroll').html(tab.join(''));
            $('#enroll_content').html(tabContent.join(''));
            $('#enroll_table_0').show();
            this.addEnrollEventHandle();
        },
        addEnrollEventHandle: function() {
            $('.tabs-list-enroll li').on('mouseover', function() {
                if ($(this).hasClass('fr')) {
                    return;
                }
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    $('#enroll_table_' + ($(this).index() - 1)).show().siblings().hide();
                }
            });
        }
    }
});