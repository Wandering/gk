/**
 * Created by kepeng on 15/9/26.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    $(document).ready(function() {
        $('#search').on('click', function(e) {
            var val = $('#keywords').val();
            window.location.href = '/consult/profession_info.jsp?val=' + val;
        });

        $('.into-evalution a').on('click',function(e) {
            //window.location.href = '';
            $.ajax({
                type: 'post',
                url: '/appraisal/lstest.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {},
                dataType: 'json',
                success: function(data) {
                    console.log(data);
                    if ('0000000' === data.rtnCode) {
                        var url = data.bizData;
                    }
                },
                error: function(data) {
                }
            });
        });
    });
});
