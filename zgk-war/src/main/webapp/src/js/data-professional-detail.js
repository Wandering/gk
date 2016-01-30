define(['commonjs', 'handlebars'], function (common, handlebars) {
    require('../css/data/data-professional-info.css');
    $(function () {
        var majoredId = common.getLinkey('id');
        //专业详情
        $('#majored-category-detail').html('');
        common.ajaxFun(common.INTERFACE_URL.getMajoredInfoById, 'GET', {
            'majoredId':majoredId
        }, function (res) {
            console.log(res)
            if (res.rtnCode === "0000000") {
                var template = handlebars.compile($("#temp-majored-detail").html());
                $('#majored-category-detail').html(template(res.bizData));
            }
        });

        common.ajaxFun(common.INTERFACE_URL.getMajorOpenUniversityList, 'GET', {
            'majoredId':majoredId
        }, function (res) {
            console.log(res)
            if (res.rtnCode === "0000000") {
                $('#open-universities-detail').html();
            }
        });

        $('#majored-category-detail').on('click','b.detail-tab',function(){
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $('.sub-content').hide();
            $('.sub-content:eq('+ index +')').show();
        });



    });
});




