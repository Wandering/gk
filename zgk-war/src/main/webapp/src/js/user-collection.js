define(['commonjs', '../css/user/user-account-info.css', 'handlebars','noDataTips'], function (util, collectionCss, handlebars,noDataTips) {
    $('#banner-info').prepend(require('html!../user-banner.html'));
    // tab切换
    $('#tab-title').on('click', 'span', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var index = $(this).index();
        $('.content-list').removeClass('active');
        $('.content-list:eq(' + index + ')').addClass('active');
    });

    var cookie = require('cookie');
    var icon = cookie.getCookieValue('icon');
    var imgIco = require('../img/icon_default.png');
    if(icon=='undefined'){
        icon = imgIco;
    }
    var userName = cookie.getCookieValue('userName');
    var vipStatus = cookie.getCookieValue('vipStatus');
    $('.user-avatar').attr('src',icon);
    $('.user-name').text(userName);
    if(vipStatus==0){
        $('#btn-vip,#user-type').show();
        $('#vip-box').hide();
    }else{
        $('#btn-vip,#user-type').hide();
        $('#vip-box').show();
    }

    /*
     * 收藏(院校收藏|课程收藏)
     * type:1（院校）或2（课程）
     * offset:起始条数（选填）
     * rows:查询条数（选填）
     * */
    var rows = 10,
        offset = 0;
    var schoolCollectionData = {
        type: 1,
        rows: rows,
        offset: offset
    };
    getCollectList();
    function getCollectList() {
        util.ajaxFun(util.INTERFACE_URL.getUserCollectList, 'GET', schoolCollectionData, function (res) {
            if (res.rtnCode == '0000000') {
                if(res.bizData.sum == 0){
                    $('.data-tips').html(noDataTips('您还未收藏任何院校，快点查看院校信息，确立目标吧!'));
                    return
                }
                var template = handlebars.compile($('#school-collection-tpl').html());
                $('#school-collection').append(template(res.bizData));
                if (res.bizData.sum>rows) {
                    $('#more-school').show();
                }else{
                    $('#more-school').hide();
                }
                if (schoolCollectionData.offset<rows) {
                    $('#more-school').removeClass('hide');
                }else{
                    $('#more-school').addClass('hide');
                }
            }
        });
    }

    $(document).on('click', '#more-school', function () {
        schoolCollectionData.offset += schoolCollectionData.rows;
        getCollectList();
    });


    //取院校收藏
    $(document).on('click', '.cancel-collection', function () {
        var _this = $(this);
        var projectIdClassId = _this.attr('cancel-id');
        util.ajaxFun(util.INTERFACE_URL.deleteUserCollect, 'GET', {
            type: '1',
            projectId: projectIdClassId
        }, function (res) {
            if (res.rtnCode == '0000000') {
                //删除动画
                _this.parent().css({
                    left: '1000px'
                });
                setTimeout(function () {
                    _this.parent().remove();
                }, 2000);
            }
        });
    });

});



