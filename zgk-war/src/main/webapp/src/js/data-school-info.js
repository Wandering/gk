/*
 * 采用webpack插件html-loader实现html页面继承
 * 使用require导入commonCss公用样式
 * require引入依赖库
 * */
define(['commonjs', '../css/data/data-school-info.css', 'handlebars', 'timeFormat', 'cookie', 'noDataTips'], function (util, dataSchoolInfoCss, handlebars, getTime, cookie, noDataTips) {

    // 获取省份
    util.ajaxFun(util.INTERFACE_URL.getProvinceList, 'get', {}, function (res) {
        if (res.rtnCode === "0000000") {
            var template = handlebars.compile($("#temp-province-list").html());
            $('#province-list').html(template(res));
        }
    });
    getCollegeList('PROPERTY', "#temp-college-list", "#college-list");    //院校分类
    getCollegeList('EDULEVEL', "#temp-degree-list", "#degree-list");    //学历层次
    getCollegeList('FEATURE', "#temp-characteristic-list", "#characteristic-list");    //院校特征
    /*
     * getgetCollegeList()
     * @hdsDom    handlebars模板class或id
     * @htmlDom   html模板class或id
     * */
    function getCollegeList(type, hdsDom, htmlDom) {
        util.ajaxFun(util.INTERFACE_URL.getCollegeList, 'get', {type: type}, function (res) {
            if (res.rtnCode == '0000000') {
                handlebars.registerHelper("collectHelper", function (val) {
                    if (val == 1) {
                        return '已收藏'
                    } else if (val == 0) {
                        return '未收藏'
                    }
                });
                var template = handlebars.compile($(hdsDom).html());
                $(htmlDom).html(template(res));
                //是否登陆判断
                setInterval(function () {
                    if (util.isLogin()) {
                        $('.collect').attr('href','javascript:void(0)').css('cursor','inherit');
                    }else{
                        $('.collect').attr({
                            'href':'/static/login.html',
                            'target':'_blank'
                        }).css('cursor','pointer');
                    }
                }, 100);
            }
        });
    }



    //院校列表(筛选)

    /*
     * getSchoolList()
     * 参数说明:
     * @universityName:院校名称
     * @areaid:省份
     * @educationLevel:学历层次
     * @type:院校分类
     * @property:院校特征
     * @offset:起始条数
     * @rows:每页条数 默认5条
     * */
    var universityName = '',
        areaid = '',
        sortId = '',
        educationLevel = '',
        property = '',
        offset = 0,
        rows = 4;
    var searchSchoolListData = {
        "universityName": universityName,
        "areaid": areaid,
        "type": sortId,
        "educationLevel": educationLevel,
        "property": property,
        "offset": offset,
        "rows": rows
    };

    function getSchoolList() {
        util.ajaxFun(util.INTERFACE_URL.getSearchList, 'get', searchSchoolListData, function (res) {
            if (res.rtnCode == '0000000') {

                var template = handlebars.compile($('#temp-search-list').html());
                $('#search-list-main').append(template(res));
                //判断是否有下一页
                if (res.bizData.universityList.length < rows) {
                    $('.btn-next').hide();
                } else {
                    $('.btn-next').show();
                }
                //加载更多按钮
                if (res.bizData.count > rows) {
                    $('.btn-next').show();
                } else {
                    $('.btn-next').hide();
                }
                //没有数据
                if (res.bizData.universityList.length == 0) {
                    $('.data-tips').html(noDataTips('真抱歉,没有查到相关院校'));
                    $('.btn-next').hide();
                } else {
                    $('.data-tips').html('');
                }
                $('.data-list').removeClass('hide');
                $('.btn-next').text('加载更多').removeAttr('disabled');
                //$('.line-loader-animate').addClass('hide');
            }
        });
    }


    getSchoolList();
    //加载更多
    $('.btn-next').on('click', function () {
        $('.btn-next').text('加载中...').attr('disabled', 'disabled');
        searchSchoolListData.offset += searchSchoolListData.rows;
        getSchoolList();
    });
    //省份筛选
    $(document).on('click', '#province-ul li', function () {
        $('.btn-next').hide();
        searchSchoolListData.areaid = $(this).attr('areaId');
        searchSchoolListData.universityName = '';
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#search-list-main').html('');
        getSchoolList();
    });
    //院校分类筛选
    $(document).on('click', '#sort-ul li', function () {
        $('.btn-next').hide();
        searchSchoolListData.type = $(this).attr('sortId');
        searchSchoolListData.universityName = '';
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#search-list-main').html('');
        getSchoolList();
    });
    //学历层次筛选
    $(document).on('click', '#educationLevel-ul li', function () {
        $('.btn-next').hide();
        searchSchoolListData.educationLevel = $(this).attr('levelId');
        searchSchoolListData.universityName = '';
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#search-list-main').html('');
        getSchoolList();
    });
    //院校特征
    $(document).on('click', '#property-ul li', function () {
        $('.btn-next').hide();
        searchSchoolListData.universityName = '';
        searchSchoolListData.property = $(this).text();
        //searchSchoolListData.property = $(this).attr('propertyId');
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#search-list-main').html('');
        getSchoolList();
        //if ($(this).attr('class') == 'active') {
        //    searchSchoolListData.property = '';
        //    $(this).removeClass('active');
        //}
    });
    //搜索
    $(document).on('click', '.go', function () {
        $('.btn-next').hide();
        searchSchoolListData.universityName = $.trim($('#query').val());
        $('#search-list-main').html('');
        getSchoolList();
    });


    //院校信息详情跳转
    $(document).on('click', '.school-logo,.school-name', function () {
        var id = $(this).attr('sid');
        window.location = 'http://' + window.location.host + '/static/data-school-detail.html?id=' + id + '';
    });

    // 设为目标
    $('#search-list-main').on('click', '.set-target,.forecasting', function () {
        var thisSchool = $(this).attr('schoolName');
        util.cookie.setCookie("targetScore", "", 4, "");
        util.cookie.setCookie("targetSchool", thisSchool, 4, "");
    });

    //静态图片
    var sideBanner1 = require('../img/side-banner1.png');
    $('#side-banner1').attr('src', sideBanner1);
    var sideBanner2 = require('../img/side-banner2.png');
    $('#side-banner2').attr('src', sideBanner2);
    var sideBanner3 = require('../img/side-banner3.png');
    $('#side-banner3').attr('src', sideBanner3);
    var sideBanner4 = require('../img/side-banner4.png');
    $('#side-banner4').attr('src', sideBanner4);
});



