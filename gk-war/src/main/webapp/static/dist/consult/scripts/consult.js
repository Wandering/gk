/**
 * Created by kepeng on 15/9/26.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');

    var api = flowplayer("player", "/static/src/guide/scripts/flowplayer-3.2.18.swf");

    $(document).ready(function() {
        $('#search').on('click', function(e) {
            var val = $('#keywords').val();
            window.location.href = '/consult/profession_info.jsp?val=' + val;
        });

        $('.into-evalution a').on('click',function(e) {
            var flag = false;
            if (!flag) {
                flag = true;
                $.get('/appraisal/lstest.do', function(data) {
                    flag = true;
                    if ('0000000' === data.rtnCode && data.bizData) {
                        window.location.href = data.bizData;
                    } else {
                        $('#error_tip').html(data.msg);
                    }
                });
            }
        });
    });
});
