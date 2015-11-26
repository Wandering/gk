define(function (require) {
    var $ = require('$');

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    var Info = {
        getProfessionInfo: function (code) {
            var that = this;
            $.ajax({
                type: 'get',
                url: '/majored/getMajoredInfo.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    code: code
                },
                dataType: 'json',
                success: function (data) {
                    //console.log(data)
                    if ('0000000' === data.rtnCode) {
                        that.renderInfo(data.bizData);
                    }
                },
                error: function (data) {
                }
            });
        },
        renderInfo: function (obj) {
            $('#info_content').html('<img class="fl" src="' + (obj.universityImage || 'http://cdn.gaokao360.net/static/global/common/images/kqbk_banner_default.png') + '" />'
            + '<div class="info">'
            + '<ul>'
            + '<li class="school-name">' + obj.name + '</li>'
            + '<li>学历层次：' + obj.educationLevel + '</li>'
            + '<li>学科门类：' + obj.subjectType + '</li>'
            + '<li>专业分类：' + obj.majoredType + '</li>'
            + '<li>授予学位：' + (obj.degree || " - ") + '</li>'
            + '</ul>'
            + '</div>');
        },
        getReationInfo: function (code) {
            var that = this;
            $.ajax({
                type: 'get',
                url: '/majored/getMajoredDetail.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    code: code
                },
                dataType: 'json',
                success: function (data) {
                    //console.log(data)
                    if ('0000000' === data.rtnCode) {
                        that.renderReation(data.bizData);
                        $('#tab_0').show()
                        that.addEventHandleTab();
                    }
                },
                error: function (data) {
                }
            });
        },
        addEventHandleTab: function () {
            $('.tabs-list').on('click', 'li', function (e) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('active');
                    $('#tab_' + $(this).index()).show().siblings().hide();
                }
            });
        },
        renderList: function (list) {
            var html = [];
            html.push('<ul>');
            $.each(list, function (i, value) {
                html.push('<li>' + value + '</li>');
            });
            html.push('</ul>');
            return html.join('');
        },
        renderReation: function (data) {
            console.log(data)
            var similarMajor = data.similarMajor ? data.similarMajor.split('丨') : [];
            var defaultTipMsg = '<p class="article">暂无信息！</p>';
            var mainCourse =  data.mainCourse ? data.mainCourse.split('丨') : '';
            $('#tab_0').html(this.renderList(similarMajor) || defaultTipMsg);
            if(mainCourse.length > 0){
                $('#tab_1').html(this.renderList(mainCourse));
            }else{
                $('#tab_1').html(defaultTipMsg);
            }

            $('#tab_2').html('<p class="article">' + (data.workGuide || '暂无信息！') + '</p>');
            var openUniversity = data.openUniversity;
            //console.log(openUniversity)
            if(openUniversity.length == 0){
                $('#tab_3').html(defaultTipMsg);
                return false;
            }
            var openUniversityHtml = ''
                +'<div class="tipTxt"><strong>温馨提示：</strong> <i class="star"></i>号表示该专业在该院校招生</div>'
                + '<table class="table" id="openUniversity-table">'
                + '<thead>'
                + '<tr>'
                + '<th>开设院校</th>'
                + '<th>计划人数</th>'
                + '<th>科类</th>'
                + '<th>收费标准</th>'
                + '</tr>'
                + '</thead>'
                + '<tbody>';
            for (var i = 0, len = openUniversity.length; i < len; i++) {
                var isOpenN = openUniversity[i].isOpen;
                var isStar = '';
                if(isOpenN==0){
                    isStar = '';
                }else{
                    isStar = '<i class="star"></i>'
                }
                openUniversityHtml +=''
                +'<tr>'
                +'<td>'
                + isStar
                + '<a href="/consult/school_detail.jsp?id='+ openUniversity[i].universityId +'">'+ (openUniversity[i].universityName || "-" ) +'</a></td>'
                +'<td>'+ (openUniversity[i].enrollNumber || "-" )   +'</td>'
                +'<td>'+ (openUniversity[i].subject)  +'</td>'
                +'<td>'+ (openUniversity[i].feeStandard || "-" )  +'</td>'
                +'</tr>';
            }
            openUniversityHtml +='</tbody></table>';
            $('#tab_3').html(openUniversityHtml);
        }
    };

    var getInitInfo = {
        "name": "土木工程",
        "educationLevel": "本科",
        "subjectType": "工科",
        "majoredType": "轻工类",
        "degree": "工学学士",
        "universityImage": "http://himg.bdimg.com/sys/portrait/item/c68b4a737048696265726e6174655374d718.jpg"
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

    $(document).ready(function () {
        var code = getUrLinKey('id');
        Info.getProfessionInfo(code);
        Info.getReationInfo(code);
    });
});