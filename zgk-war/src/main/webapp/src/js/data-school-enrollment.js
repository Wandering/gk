/*
 * 采用webpack插件html-loader实现html页面继承
 * 使用require导入commonCss公用样式
 * require引入依赖库
 * */
define(['commonjs', '../css/data/data-school-enrollment.css', 'handlebars', 'noDataTips'], function (util, dataSchoolEnrollmentCSS, handlebars, noDataTips) {

    // 院校所属
    util.ajaxFun(util.INTERFACE_URL.getProvinceList, 'get', {}, function (res) {
        if (res.rtnCode === "0000000") {
            var template = handlebars.compile($("#province-list-tpl").html());
            $('#province-list').html(template(res));
        }
    });
    getCollegeList('BATCHTYPE', "#college-list-tpl", "#college-list");    //院校分类
    getCollegeList('FEATURE', "#characteristic-list-tpl", "#characteristic-list");    //院校特征
    /*
     * getgetCollegeList()
     * @hdsDom    handlebars模板class或id
     * @htmlDom   html模板class或id
     * */
    function getCollegeList(type, hdsDom, htmlDom) {
        util.ajaxFun(util.INTERFACE_URL.getCollegeList, 'get', {type: type}, function (res) {
            if (res.rtnCode == '0000000') {
                var template = handlebars.compile($(hdsDom).html());
                $(htmlDom).html(template(res));
            }
        });
    }

    //招生年份
    util.ajaxFun(util.INTERFACE_URL.getAdmissionline, 'get', {}, function (res) {
        if (res.rtnCode === "0000000") {
            var template = handlebars.compile($("#enrol-years-tpl").html());
            $('#enrol-years').html(template(res));
        }
    });

    /*
     * getLineList()
     * 参数说明:
     * @queryparam:搜索框参数（院校名称）
     * @year:年份(选填)
     * @areaId:省份(选填)
     * @property:985/211（选填，无默认）
     * @batch:批次(选填)
     * @type:类型(选填，默认1文史2理工)
     * @page:页
     * @rows:每页条数 默认5条
     * */
    var queryparam = '',
        year = '',
        areaId = '',
        property = '',
        batch = '',
        type = 2,
        page = 1,
        rows = 6;
    var getLineListData = {
        "queryparam": queryparam,
        "year": year,
        "areaId": areaId,
        "property": property,
        "batch": batch,
        "type": type,
        "page": page,
        "rows": rows
    };

    //tab切换
    $('.title b').click(function () {
        $(this).addClass('active').siblings().removeClass('active');
        getLineListData.type = $(this).attr('tid');
        $('#line-list').html('');
        getLineListData.page = page;
        getLineListData.rows = rows;
        getLineList();
    });


    function getLineList() {
        util.ajaxFun(util.INTERFACE_URL.getLineList, 'get', getLineListData, function (res) {
            if (res.rtnCode == '0000000') {
                var template = handlebars.compile($('#line-list-tpl').html());
                $('#line-list').append(template(res.bizData));

                //判断是否有下一页
                if (res.bizData.rows.length < rows) {
                    $('.btn-next').hide();
                } else {
                    $('.btn-next').show();
                }
                //加载更多按钮
                if (res.bizData.records > rows) {
                    $('.btn-next').show();
                } else {
                    $('.btn-next').hide();
                }
                //没有数据
                if (res.bizData.rows.length == 0) {
                    $('.data-tips').html(noDataTips('真抱歉,没有查到相关院校'));
                    $('.btn-next').hide();
                } else {
                    $('.data-tips').html('');
                }
                $('.data-list').removeClass('hide');
                $('.btn-next').text('加载更多').removeAttr('disabled');

            }
        });
    }
    //待优化,临时写
    getLineListData.areaId = 330000;//浙江
    getLineListData.batch = 1;//一批本科
    getLineList();
    $('.btn-next').on('click', function () {
        $('.btn-next').text('加载中...').attr('disabled', 'disabled');
        getLineListData.page = getLineListData.page + 1;
        getLineList();
    });
//省份筛选
    $(document).on('click', '#province-list dd span', function () {
        $('.btn-next').hide();
        getLineListData.queryparam = '';
        getLineListData.areaId = $(this).attr('areaId');
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#line-list').html('');
        getLineList();
    });
//院校分类筛选
    $(document).on('click', '#college-list dd span', function () {
        $('.btn-next').hide();
        getLineListData.queryparam = '';
        getLineListData.batch = $(this).attr('sortid');
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#line-list').html('');
        getLineList();
    });
//院校特征
    $(document).on('click', '#characteristic-list dd span', function () {
        $('.btn-next').hide();
        getLineListData.queryparam = '';
        getLineListData.property = $(this).attr('propertyid');
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#line-list').html('');
        getLineList();
    });
//院校特征
    $(document).on('click', '#enrol-years dd span', function () {
        $('.btn-next').hide();
        getLineListData.queryparam = '';
        getLineListData.year = $(this).attr('yearid');
        $(this).addClass('active').siblings().removeClass('active').parent().siblings().find('.all').removeClass('active');
        $('#line-list').html('');
        getLineList();
    });
//搜索
    $(document).on('click', '.go', function () {
        $('.btn-next').hide();
        getLineListData.queryparam = $.trim($('#query').val());
        $('#line-list').html('');
        getLineList();
    });


//院校信息详情跳转
    $(document).on('click', '.school-info .name', function () {
        var id = $(this).attr('did');
        window.location = 'http://' + window.location.host + '/static/data-school-detail.html?id=' + id + '';
    });
});