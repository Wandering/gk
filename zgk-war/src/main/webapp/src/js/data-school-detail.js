/*
 * 采用webpack插件html-loader实现html页面继承
 * 使用require导入commonCss公用样式
 * require引入依赖库
 * */
define(['commonjs', '../css/data/data-school-detail.css', 'handlebars','cookie'], function (util, dataSchoolEnrollmentCSS, handlebars,cookie) {
    var id = util.getLinkey('id'); //公用院校id
    //拉取院校基础信息
    util.ajaxFun(util.INTERFACE_URL.getSchoolDetail, 'get', {universityId: id}, function (res) {
        if (res.rtnCode == '0000000') {
            if (util.isLogin) {
                handlebars.registerHelper('collectionHelper', function (val) {
                    if (val == 1) {
                        return '已收藏'
                    } else {
                        return '未收藏'
                    }
                });
            }
            var template = handlebars.compile($('#school-info-tpl').html());
            $('#school-info').append(template(res.bizData));
        }
    });
    /*
     * 收藏模块
     * 拉取收藏数据|添加收藏|取消收藏
     * */
    //院校收藏拉取
    //添加或取消收藏
    if (util.isLogin()) {
        setTimeout(function () {
            util.ajaxFun(util.INTERFACE_URL.getIsUniversityCollect, 'get', {
                type: 1,//院校收藏
                projectId: id
            }, function (res) {
                if (res.rtnCode == '0000000') {
                    res.bizData == 1 ? $('.isCollect').text('已收藏').attr('cid', 1) : $('.isCollect').text('未收藏').attr('cid', 0);
                    res.bizData == 1 ? $('.icon-heart').removeClass('hide') : $('.icon-heart-no').removeClass('hide');
                }
            });
        }, 800);
        $(document).on('click', '.isCollect', function () {
            var collectId = $(this).attr('cid');
            addOrDelCollect(collectId);
        });
        function addOrDelCollect(type) {
            var url = '';
            if (type == 0) {
                url = util.INTERFACE_URL.saveUserCollect;  //添加
                $('.isCollect').text('已收藏').attr('cid', 1);
                $('.icon-heart').removeClass('hide');
                $('.icon-heart-no').addClass('hide');
            } else if (type == 1) {
                url = util.INTERFACE_URL.deleteUserCollect; //取消
                $('.isCollect').text('未收藏').attr('cid', 0);
                $('.icon-heart-no').removeClass('hide');
                $('.icon-heart').addClass('hide');
            }
            var data = {
                type: 1,//院校收藏
                projectId: id
            };
            util.ajaxFun(url, 'get', data, function (res, strMsg) {
                if (res.rtnCode == '0000000') {
                    alert('操作成功!');
                }
            });
        }
    } else {
        setTimeout(function () {
            $('.isCollect').html('+加收藏').addClass('no-login-add');
        }, 800);
    }
    //未登录点击收藏提示登录
    $(document).on('click', '.no-login-add', function () {
        var go = confirm('╮(╯▽╰)╭,收藏功能登陆后才能操作.');
        if (go == true) {
            window.location.href = '/static/login.html';
        }
    });


    /*
     *左侧切换
     * @typeid:0~5 一一对应
     * 院校简介|报考指南|开设专业|招生计划|录取情况
     * */
    $(document).on('click', '.info-nav li', function () {
        var _this = $(this);
        _this.addClass('active').siblings().removeClass('active');
        switch (parseInt(_this.attr('typeid'))) {
            case 0:
                $('#university-intro').show().siblings().hide();
                $('#open-professional,#enrollment-plan').hide();
                break;
            case 1:
                $('#entrance-intro').show().siblings().hide();
                $('#open-professional,#enrollment-plan').hide();
                break;
            case 2:
                $('#open-professional').show();
                $('#university-intro,#entrance-intro,#enrollment-plan,#admission-situation').hide();
                break;
            case 3:
                $('#enrollment-plan').show();
                majorEnrollingPlanList                //招生计划
                $('#university-intro,#open-professional,#admission-situation,#entrance-intro').hide();
                break;
            default:
                $('#admission-situation').show().siblings().hide();
                $('#open-professional,#enrollment-plan').hide();
                break;
        }
    });
    /*
     * 开设专业
     * universityId=3 //院校Id
     * rows=10 //查询条数（选填）
     * offset=0 //起始条数（选填）
     * */
    var offset = 0,
        rows = 10;
    var getOpenProfessData = {
        offset: offset,
        rows: rows,
        universityId: id
    };
    $(document).on('click', '.btn-next', function () {
        getOpenProfessData.offset = getOpenProfessData.offset + 1;
        getOpenProfess();
    });
    getOpenProfess();                //开设专业
    function getOpenProfess() {
        util.ajaxFun(util.INTERFACE_URL.getOpenProfessional, 'get', getOpenProfessData, function (res) {
            if (res.rtnCode == '0000000') {
                var template = handlebars.compile($('#open-professional-tpl').html());
                $('#open-professional .content ').append(template(res.bizData));
                if (res.bizData.featureMajorList.length < rows) {
                    $('.btn-next').hide();
                }
            }
        });
    }

    /*
     * 招生计划
     * (院校招生计划|专业招生计划)
     * */
    //获取招生年份
    var universityMajorEnrollingPlanListData = {
        universityId: id,
        year: 2015,
        batch: 1,
        universityMajorType: 1,
        offset: 0,
        rows: 10
    };
    util.ajaxFun(util.INTERFACE_URL.getAdmissionline, 'get', universityMajorEnrollingPlanListData, function (res) {
        if (res.rtnCode == '0000000') {
            var html = '<option value="00">请选择年份</option>';
            res.bizData.forEach(function (i) {
                html += '<option value="' + i + '">' + i + '年</option>';
            });
            $('#professional-plan-year').html(html);
        }
    });

    //获取科目
    var universitySubjectListData = {};
    util.ajaxFun(util.INTERFACE_URL.getRemoteDataDictList, 'get', {}, function (res) {
        if (res.rtnCode == '0000000') {
            var html = '<option value="00">请选择科目</option>';
            $.each(res.bizData, function (i, v) {
                html += '<option value="' + v.dictId + '">' + v.name + '</option>'
            });
            $('#professional-plan-subject').html(html);
        }
    });
    //获取批次
    var universityBatchListData = {
        type: "BATCHTYPE"
    };
    util.ajaxFun(util.INTERFACE_URL.getCollegeList, 'get', universityBatchListData, function (res) {
        if (res.rtnCode == '0000000') {
            var html = '<option value="00">请选择批次</option>';
            $.each(res.bizData, function (i, v) {
                html += '<option value="' + v.dictId + '">' + v.name + '</option>'
            });
            $('#professional-plan-batch').html(html);
        }
    });

    /*
     * 筛选查询
     * 年份,科目,批次筛选
     * universityMajorType:文1,理2
     * */
    var searchYear = 2015,
        searchUniversityMajorType = 1,
        searchBatch = 1;
    $(document).on('change', '#professional-plan-year', function () {
        searchYear = $(this).val();
        console.info(searchYear);
        majorEnrollingPlanList();
    });
    $(document).on('change', '#professional-plan-subject', function () {
        searchUniversityMajorType = $(this).val();
        majorEnrollingPlanList();
    });
    $(document).on('change', '#professional-plan-batch', function () {
        searchBatch = $(this).val();
        majorEnrollingPlanList();
    });
    var majorEnrollingPlanRows = 1,
        majorEnrollingPlanOffset = 0;
    var majorEnrollingPlanListData = {
        'universityId': id, //院校Id
        'year': searchYear, //年份
        'batch': searchBatch, //批次
        'universityMajorType': searchUniversityMajorType, //专业类别
        'offset': majorEnrollingPlanOffset, //起始条数（选填）
        'rows': majorEnrollingPlanRows //查询条数（选填）
    };
    majorEnrollingPlanList();
    function majorEnrollingPlanList() {
        util.ajaxFun(util.INTERFACE_URL.getUniversityMajorEnrollingPlanList, 'get', majorEnrollingPlanListData, function (res) {
            if (res.rtnCode == '0000000') {
                var template = handlebars.compile($('#select-table-tpl').html());
                $('#select-table').html(template(res));
                //if (res.bizData.majorList.length < rows) {
                //    $('.btn-next').hide();
                //}
            }
        });
    }


    // 设为目标
    $('#school-info').on('click','.set-target,.forecasting',function(){
        var thisSchool = $(this).attr('schoolName');
        util.cookie.setCookie("targetScore", "", 4, "");
        util.cookie.setCookie("targetSchool", thisSchool, 4, "");
    });


});