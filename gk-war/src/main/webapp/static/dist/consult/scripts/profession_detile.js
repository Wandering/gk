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
            $.get('', function(data) {
                if ('0000000' === data.rtnCode) {

                }
            });
        },
        addEventHandleTab: function() {
            $('#tabs-list li').on('mouseover', function(e) {
                $('#tab_' + $(this).index()).show().siblings().hide();
            });
        },
        renderReation: function(data) {
            $('#tab_0').html(data.similarMajor);
            $('#tab_1').html(data.mainCourse);
            $('#tab_2').html(data.workGuide);
            var openUniversity = data.openUniversity;
            var html = [];

            for (var i = 0, len = openUniversity.length; i < len; i++) {
                html.push('<h5>' + openUniversity[i].universityType + '</h5>');

                var universityLs = openUniversity[i].universityLs;
                if (universityLs.length > 0) {
                    html.push('<ul>');

                    for (var j = 0, jlen = universityLs.length; j < jlen; j++) {
                        html.push('<li><a href="/consult/school_detile.jsp?id=' + universityLs[i].code + '">' + universityLs[i].name + '</a></li>');
                    }

                    html.push('</ul>');
                }
            }
            $('#tab_3').html(html.join(''));
        }
    }
});