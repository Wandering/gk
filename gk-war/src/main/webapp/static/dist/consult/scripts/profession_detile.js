/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    var Info = {
        getProfessionInfo: function() {
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
                                            + '<li>学历层次：' + obj.educationLevel + '</li>'
                                            + '<li>学科门类：' + obj.subjectType + '</li>'
                                            + '<li>专业分类：' + obj.majoredType + '</li>'
                                            + '<li>授予学位：' + obj.degree + '</li>'
                                        + '</ul>'
                                    + '</div>');
        },
        getReationInfo: function() {
            //$.get('', function(data) {
            //    if ('0000000' === data.rtnCode) {
            //
            //    }
            //});
            this.renderReation(testData);
            $('#tab_0').show()
            this.addEventHandleTab();
        },
        addEventHandleTab: function() {
            $('.tabs-list li').on('mouseover', function(e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    $('#tab_' + $(this).index()).show().siblings().hide();
                }
            });
        },
        renderReation: function(data) {
            $('#tab_0').html('<p class="article">' + data.similarMajor + '</p>');
            $('#tab_1').html('<p class="article">' + data.mainCourse + '</p>');
            $('#tab_2').html('<p class="article">' + data.workGuide + '</p>');
            var openUniversity = data.openUniversity;
            var html = [];

            for (var i = 0, len = openUniversity.length; i < len; i++) {
                html.push('<h5>' + openUniversity[i].universityType + '</h5>');

                var universityLs = openUniversity[i].universityLs;
                if (universityLs.length > 0) {
                    html.push('<ul>');

                    for (var j = 0, jlen = universityLs.length; j < jlen; j++) {
                        html.push('<li><a href="/consult/school_detile.jsp?id=' + universityLs[j].code + '">' + universityLs[j].name + '</a></li>');
                    }

                    html.push('</ul>');
                }
            }
            $('#tab_3').html(html.join(''));
        }
    };

    var getInitInfo = {
        "name": "土木工程",
        "educationLevel": "本科",
        "subjectType": "工科",
        "majoredType": "轻工类",
        "degree": "工学学士",
        "universityImage": "http://himg.bdimg.com/sys/portrait/item/c68b4a737048696265726e6174655374d718.jpg",
    }

    var testData = {
        "similarMajor": "轻化工程，包装工程，印刷工程，粉刷工程",
        "mainCourse": "印刷工程光学，印刷色度学，电子出版技术，高分离学",
        "workGuide": "凹版印刷,印刷技术，印务，数码印刷",
        "openUniversity": [
            {
                "universityType": "本科一批",
                "universityLs": [
                    {
                        "code": 1021,
                        "name": "清华大学"
                    }
                ]
            },
            {
                "universityType": "本科二批",
                "universityLs": [
                    {
                        "code": 2021,
                        "name": "太原理工大学"
                    }
                ]
            }
        ]
    }

    $(document).ready(function() {
        Info.renderInfo(getInitInfo);
        Info.getReationInfo();
    });
});