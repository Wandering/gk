/**
 * Created by pdeng on 15/9/24.
 */

$.ajax({
    url: '/banner/getUrlByType.do',
    type: 'get',
    dataType: 'json',
    data: {
        type:1
    },
    success: function (res) {
        console.log(res);
        if (res.rtnCode == '0000000') {
            //window.location.href
        }
    }
});
