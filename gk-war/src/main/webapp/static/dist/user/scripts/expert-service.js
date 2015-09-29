define(function (require) {
    var $ = require('$');
    require('header-user');
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    function getTime(timestamp) {
        var newDate = new Date();
        newDate.setTime(timestamp);
        return newDate.Format("yyyy-MM-dd");
    }

    //拉取数据列表
    var size = 4;
    function getList(no, size, key_search) {
        $.ajax({
            url: '/appointment/getAppointment.do',
            dataType: 'json',
            type: 'get',
            data: {
                pageNo: no,
                pageSize: size,
                titleKey: key_search  //关键字搜索
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    var template = '';
                    $.each(res.bizData, function (i, v) {
                        template += '<a class="row go-detail" href="javascript:void(0);"> ' +
                        '<div class="col-3 title" data-id="'+ v.id+'">' + v.title + '</div> ' +
                        '<div class="col-1 createTime">' + getTime(v.createDate) + '</div> ' +
                        '</a>';
                    });
                    if(res.bizData.length <size){
                        $('.more').hide();
                    }else{
                        $('.more').show();
                    }
                    $('.data-list').html(template);
                }
            }
        })
    }
    getList(1, size);
    //搜索
    var search = $('#search');
    search.keydown(function () {
        var key_search = search.val();
        if (event.keyCode == 13) {
            getList(1, 5, key_search);
        }
    });
    $('#btn-search').click(function () {
        var key_search = $('#search').val();
        getList(1, 5, key_search);
    });
    //预定详情
    $(document).on('click','.go-detail',function(e){
        e.stopPropagation();
        var id = $(this).find('.title').attr('data-id');
        window.location.href = 'http://' + window.location.host + '/user/expert-service-detail.jsp?id=' + id;
    });
});
