define(function (require) {
    var $ = require('$');
    require('backToTop');

    // 获取URL
    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    // 获取cookie
    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }


    var Info = {
        // 院校基本信息
        getBasicInfo: function (parameterId, code) {
            var _this = this;
            $.ajax({
                type: 'GET',
                url: '/university/getUniversityDetail.do?' + parameterId + '=' + code,
                dataType: 'json',
                success: function (data) {
                    var schoolId = data.bizData.id;
                    if (GetCookie("snuser")) {
                        Info.getIsCollect(schoolId);
                    }
                    if ('0000000' === data.rtnCode) {
                        _this.renderInfo(data.bizData);
                        Info.schoolIntro(schoolId);
                        // tab切换
                        var flag = false;
                        $('#tabs-list').on('click', 'li', function () {
                            var _this = $(this);
                            var thisV = _this.attr('rel');
                            var index = _this.index();
                            _this.addClass('active').siblings().removeClass('active');
                            $('.tabs-content:eq('+ index +')').show().siblings('.tabs-content').hide();
                            if (thisV == "院校简介") {
                                $('#tabs-content1').show();
                                if(!$('#tabs-content1').attr('flag'))Info.schoolIntro(schoolId);
                            } else if (thisV == "招生简章") {
                                if(!$('#tabs-content2').attr('flag'))Info.brochures(schoolId);
                            } else if (thisV == "往年招生情况") {
                                if(!$('#tabs-content3').attr('flag'))Info.getSchoolInfo(schoolId);
                            } else if (thisV == "往年招生计划") {
                                if(!$('#tabs-content4').attr('flag'))Info.getEnroll(schoolId);
                            } else if (thisV == "专业录取分数") {
                                if(!$('#tabs-content5').attr('flag'))Info.admitAMark(schoolId);
                            } else if (thisV == "开设专业") {
                                if(!$('#tabs-content6').attr('flag'))Info.openSpecialty(schoolId);
                            }
                        })
                        $('#tabs-list li:eq(0)').click();
                    } else {
                        var pageErrorTip = require('pageErrorTip');
                        $('#info_content').html(pageErrorTip('数据维护中'));
                    }
                },
                error: function (data) {
                    var pageErrorTip = require('pageErrorTip');
                    $('#info_content').html(pageErrorTip('数据维护中'));
                }
            });
        },
        //基本信息
        renderInfo: function (obj) {
            var address = '院校地址：' + (obj.address || '');
            var addressClassName = '';
            if (address.length > 17) {
                addressClassName = 'integet-line';
            }
            var contactPhone = '联系电话：' + (obj.contactPhone || '');
            var phoneClassName = '';
            if (contactPhone.length > 16) {
                phoneClassName = 'integet-line';
            }
            var url = '院校网址：' + (obj.url || '');
            var urlClassName = '';
            if (url.length > 20) {
                urlClassName = 'integet-line';
            }
            var collectHref = '';
            if (!GetCookie("snuser") || GetCookie("snuser") == '""') {
                console.log('没有登录111');
                collectHref = '/login/login.jsp';
            } else {
                collectHref = 'javascript:;';
            }
            $('#info_content').html(
                '<a target="_blank" href="' + collectHref + '" id="collect" class="collect"><span>收藏</span><i></i></a>'
                + '<img class="fl" src="' + (obj.universityImage || 'http://cdn.gaokao360.net/static/global/common/images/kqbk_banner_default.png') + '" />'
                + '<div class="info">'
                + '<ul>'
                + '<li class="school-name">' + (obj.name || '') + '</li>'
                + '<li>所在省份：' + (obj.provinceName || '') + '</li>'
                + '<li>院校隶属：' + (obj.subjection || '') + '</li>'
                + '<li>学历层次：' + (obj.educatLevel || '') + '</li>'
                + '<li>院校特征：' + (obj.property || '') + '</li>'
                + '<li>院校类型：' + (obj.universityType || '') + '</li>'
                + '<li class="' + urlClassName + '">院校网址：<a class="website" target="_blank" href="' + obj.url + '">' + (obj.url || '') + '</a></li>'
                + '<li class="' + addressClassName + '">' + address + '</li>'
                + '<li class="' + phoneClassName + '">联系电话：<span>' + (obj.contactPhone || '') + '</span></li>'
                + '</ul>'
                + '</div>');
        },
        // 收藏学校
        getIsCollect: function (schoolId) {
            $.get('/userCollection/isUniversityCollect.do?universityId=' + schoolId,
                function (data) {
                    if (data.rtnCode = "0000000") {
                        var isCollect = data.bizData;
                        if (isCollect == 0) {
                            $('#collect').removeClass('active').find('span').text('收藏');
                        } else {
                            $('#collect').addClass('active').find('span').text('取消收藏');
                        }
                        $('#info_content').on('click', '#collect', function () {
                            if ($('#collect').find('span').text() == '收藏') {
                                Info.saveUserCollect(schoolId);
                            } else {
                                Info.deleteUserCollect(schoolId);
                            }
                        });
                    }
                });
        },
        // 保存收藏
        saveUserCollect: function (schoolId) {
            $.get('/userCollection/saveUserCollect.do?universityId=' + schoolId, function (data) {
                if (data.rtnCode == "0000000") {
                    $('#collect').addClass('active').find('span').text('取消收藏');
                }
            });
        },
        // 取消收藏
        deleteUserCollect: function (schoolId) {
            $.get('/userCollection/deleteUserCollect.do?universityId=' + schoolId, function (data) {
                if (data.rtnCode == '0000000') {
                    $('#collect').removeClass('active').find('span').text('收藏');
                }
            })
        },
        // 院校简介
        schoolIntro: function (schoolId) {
            $.get('/university/getUniversityIntro.do?universityId=' + schoolId, function (data) {
                if (data.rtnCode == '0000000') {
                    $('#tabs-content1').html(data.bizData.universityIntro).attr('flag',true);
                }
            });
        },
        // 招生简章
        brochures: function (schoolId) {
            $.get('/university/getEntroIntro.do?universityId=' + schoolId, function (data) {
                if (data.rtnCode == '0000000') {
                    $('#tabs-content2').html(data.bizData.entroIntro).attr('flag',true);
                }
            })

        },
        // 往年招生情况
        getSchoolInfo: function (schoolId) {
            var _this = this;
            $.get(' /university/getEnrollInfo.do?id=' + schoolId, function (data) {
                if (data.rtnCode == '0000000') {
                    _this.renderSchool(data.bizData.enrollInfo)
                }
            })
        },
        renderSchool: function (data) {
            //招生情况年份
            var tabHtml = '<div id="tabs_list_last3" class="tabs-ui"><ul>';
            var tabContentHtml = '';
            for (var i = 0; i < data.length; i++) {
                var title = data[i].title;
                tabHtml += '<li>' + title + '</li>';
                tabContentHtml += ''
                + '<div class="school-table mt20">'
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
                + '<tbody>';
                for (var j = 0; j < data[i].infos.length; j++) {
                    var infosData = data[i].infos[j];
                    tabContentHtml += '<tr>'
                    + '<td>' + (infosData.subjectName || '') + '</td>'
                    + '<td>' + (infosData.planNumber || '') + '</td>'
                    + '<td>' + (infosData.enrollNumber || '') + '</td>'
                    + '<td>' + (infosData.highestScore || '') + '</td>'
                    + '<td>' + (infosData.highestRank || '') + '</td>'
                    + '<td>' + (infosData.lowestScore || '') + '</td>'
                    + '<td>' + (infosData.lowestRank || '') + '</td>'
                    + '<td>' + (infosData.averageScore || '') + '</td>'
                    + '<td>' + (infosData.averageRank || '') + '</td>'
                    + '</tr>';
                }
                tabContentHtml += '</tbody></table></div>';
            }
            tabHtml += '</ul></div>';
            $('#tabs-content3')
                .attr('flag',true)
                .append(tabHtml)
                .append(tabContentHtml)
                .on('click', 'li', function () {
                    var _this = $(this);
                    var index = _this.index();
                    var thisParent = _this.parents('#tabs-content3');
                    _this.addClass('active').siblings().removeClass('active');
                    thisParent.find('.school-table').hide();
                    thisParent.find('.school-table:eq(' + index + ')').show();
                })
            $('#tabs_list_last3').find('li:eq(0)').click();
        },
        // 往年招生计划
        getEnroll: function (schoolId) {
            var _this = this;
            var batch = getUrLinKey('batch');
            $.get('/university/getEnrollPlan.do?id=' + schoolId + '&batch=' + batch, function (data) {
                console.log(data)
                if (data.rtnCode == '0000000') {
                    _this.renderEnroll(data.bizData.enrollPlan);
                }
            })
        },
        renderEnroll: function (data) {

            var tabHtml = '<div id="tabs_list_last4" class="tabs-ui"><ul>';
            var tabContentHtml = '';
            for (var i = 0; i < data.length; i++) {
                var title = data[i].title;
                tabHtml += '<li>' + title + '</li>';
                tabContentHtml += '<div class="school-table mt20">'
                + '<div class="subjectType">'
                + '<label><input type="radio" name="subject1" id=""/> <span>文史</span></label><label><input type="radio" name="subject1" id=""/> <span>理工</span></label>'
                + '</div>'
                + '<table border="0" cellpadding="0" cellspacing="0">'
                + '<thead>'
                + '<tr>'
                + '<th  class="name">专业名称</th>'
                + '<th>批次</th>'
                + '<th>科类</th>'
                + '<th>计划人数</th>'
                + '<th>学制</th>'
                + '<th>收费标准</th>'
                + '</tr>'
                + '</thead>'
                + '<tbody>';
                for (var j = 0; j < data[i].planInfos.length; j++) {
                    var planInfosData = data[i].planInfos[j];
                    tabContentHtml += '<tr batch="'+ planInfosData.subject +'">'
                    + '<td width="50%" class="name"><a target="_blank" href="/consult/profession_detail.jsp?id='+ planInfosData.majoredId +'">' + (planInfosData.majoredName || '') + '</a></td>'
                    + '<td width="10%">' + (planInfosData.batch || '') + '</td>'
                    + '<td width="10%">' + (planInfosData.subject || '') + '</td>'
                    + '<td width="10%">' + (planInfosData.planNumber || '') + '</td>'
                    + '<td width="10%">' + (planInfosData.schoolLength || '') + '</td>'
                    + '<td width="10%">' + (planInfosData.feeStandard || '') + '</td>'
                    + '</tr>';
                }
                tabContentHtml += '</tbody></table></div>';
            }
            tabHtml += '</ul></div>';
            $('#tabs-content4')
                .attr('flag',true)
                .append(tabHtml)
                .append(tabContentHtml)
                .on('click', 'li', function () {
                    var _this = $(this);
                    var index = _this.index();
                    var thisParents = _this.parents('#tabs-content4');
                    thisParents.find('tr').show();
                    thisParents.find('label').removeClass('active').find('input').attr('checked',false);
                    _this.addClass('active').siblings().removeClass('active');
                    thisParents.find('.school-table').hide();
                    thisParents.find('.school-table:eq(' + index + ')').show()
                        .find('label')
                        .on('click',function(){
                            $(this).addClass('active').siblings().removeClass('active');
                            var batch = $(this).find('span').text();
                            var batchW = $(this).parents('.school-table').find('tr[batch="文史"]');
                            var batchL = $(this).parents('.school-table').find('tr[batch="理工"]');
                            if(batch=="文史"){
                                batchL.hide();
                                batchW.show();
                            }else{
                                batchW.hide();
                                batchL.show();
                            }
                        })
                });
            $('#tabs_list_last4').find('li:eq(0)').click();
        },
        // 专业录取分数
        admitAMark: function (schoolId) {
            var _this = this;
            $.get('/university/getMajoredScoreLineList.do?universityId=' + schoolId, function (data) {
                console.log(data)
                if (data.rtnCode == '0000000') {
                    _this.renderAdmitAMark(data.bizData);
                }
            })
        },
        renderAdmitAMark: function (data) {
            var tabHtml = '<div id="tabs_list_last5" class="tabs-ui"><ul>';
            var tabContentHtml = '';
            for (var v in data) {
                var title = v;
                tabHtml += '<li>' + title + '</li>';
                tabContentHtml += '<div class="school-table mt20">'
                + '<div class="subjectType">'
                + '<label><input type="radio" name="subject1" id=""/> <span>文史</span></label><label><input type="radio" name="subject1" id=""/> <span>理工</span></label>'
                + '</div>'
                + '<table border="0" cellpadding="0" cellspacing="0">'
                + '<thead>'
                + '<tr>'
                + '<th>专业名称</th>'
                    //+ '<th>批次</th>'
                + '<th>科类</th>'
                + '<th>录取最高分</th>'
                + '<th>录取平均分</th>'
                + '</tr>'
                + '</thead>'
                + '<tbody>';
                for (var j = 0; j < data[v].length; j++) {
                    var admitAMarkData = data[v][j];
                    tabContentHtml += '<tr batch="'+ admitAMarkData.subject +'">'
                    + '<td>' + (admitAMarkData.majoredName || '-') + '</td>'
                        //+ '<td>' + (admitAMarkData.enrollBatch || '-') + '</td>'
                    + '<td>' + (admitAMarkData.subject || '-') + '</td>'
                    + '<td>' + (admitAMarkData.highestScore) + '</td>'
                    + '<td>' + (admitAMarkData.averageScore) + '</td>'
                    + '</tr>';
                }
                tabContentHtml += '</tbody></table></div>';
            }
            tabHtml += '</ul></div>';
            $('#tabs-content5')
                .attr('flag',true)
                .append(tabHtml)
                .append(tabContentHtml)
                .on('click', 'li', function () {
                    var _this = $(this);
                    var index = _this.index();
                    var thisParents = _this.parents('#tabs-content5');
                    thisParents.find('tr').show();
                    thisParents.find('label').removeClass('active').find('input').attr('checked',false);
                    _this.addClass('active').siblings().removeClass('active');
                    thisParents.find('.school-table').hide();
                    thisParents.find('.school-table:eq(' + index + ')').show()
                        .find('label')
                        .on('click',function(){
                            $(this).addClass('active').siblings().removeClass('active');
                            var batch = $(this).find('span').text();
                            var batchW = $(this).parents('.school-table').find('tr[batch="文史"]');
                            var batchL = $(this).parents('.school-table').find('tr[batch="理工"]');
                            console.log($(this).parents('.school-table').find('tr').html())
                            if(batch=="文史"){
                                batchL.hide();
                                batchW.show();
                            }else{
                                batchW.hide();
                                batchL.show();
                            }
                        })
                });
            $('#tabs_list_last5').find('li:eq(0)').click();
        },
        // 开设专业
        openSpecialty: function (schoolId) {
            var _this = this;
            $.get('/university/getOpenMajoredPojoList.do?universityId=' + schoolId, function (data) {
                console.log(data)
                var dataJson = data.bizData;
                if (data.rtnCode == '0000000') {
                    var tabContentHtml = '<div class="school-table mt20">'
                        +'<div class="tipTxt"><strong>温馨提示：</strong> <i class="star"></i>号表示该专业在该院校招生</div>'
                        + '<table border="0" cellpadding="0" cellspacing="0">'
                        + '<thead>'
                        + '<tr>'
                        + '<th>专业名称</th>'
                        + '<th>科类</th>'
                        + '<th>薪资排名</th>'
                        + '<th>就业率排名</th>'
                        + '</tr>'
                        + '</thead>'
                        + '<tbody>';
                    for (var i = 0; i < dataJson.length; i++) {
                        var employmentRateRank = dataJson[i].employmentRateRank
                            , isEnrol = dataJson[i].isEnrol
                            , majoredName = dataJson[i].majoredName
                            , salaryRank = dataJson[i].salaryRank
                            , subject = dataJson[i].subject;
                        var isEnrolTxt = '';
                        if (isEnrol == 1) {
                            isEnrolTxt = '<i class="star"></i>';
                        } else {
                            isEnrolTxt = '';
                        }
                        tabContentHtml += '<tr>'
                        + '<td>'
                        + isEnrolTxt
                        + majoredName + '</td>'
                        + '<td>' + (subject || "-") + '</td>'
                        + '<td>' + salaryRank + '</td>'
                        + '<td>' + employmentRateRank + '</td>'
                        + '</tr>';
                    }
                    tabContentHtml += '</tbody></table></div>';
                    $('#tabs-content6')
                        .attr('flag',true)
                        .append(tabContentHtml);
                }
            })
        }
    };
    $(document).ready(function () {
        var code = getUrLinKey('code');
        var id = getUrLinKey('id');
        if (code) {
            Info.getBasicInfo('code', code);
        } else if (id) {
            Info.getBasicInfo('id', id);
        }
    });
});
