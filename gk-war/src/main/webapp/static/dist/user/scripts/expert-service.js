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
    var pointList = {
        num: 1,
        size: 10,
        next: $('.more'),
        search: $('#search'),
        btnSearch:$('#btn-search'),
        renderContainer: $('.data-list'),
        getList: function (num, size, key_search) {
            $.ajax({
                url: '/appointment/getAppointment.do',
                dataType: 'json',
                type: 'get',
                data: {
                    pageNo: num,
                    pageSize: size,
                    titleKey: key_search  //关键字搜索
                },
                success: function (res) {
                    if (res.rtnCode == '0000000') {
                        if(res.bizData.length == '0'){
                            $('.content-list').addClass('no-msg').html('对不起，暂无数据 ').fadeIn();
                            return false;
                        }
                        var template = '';
                        $.each(res.bizData, function (i, v) {
                            template += '<a class="row go-detail" href="javascript:void(0);"> ' +
                            '<div class="col-3 title" data-id="' + v.id + '">' + v.title + '</div> ' +
                            '<div class="col-1 createTime">' + getTime(v.createDate) + '</div> ' +
                            '</a>';
                        });
                        if (res.bizData.length < size) {
                            pointList.next.hide();
                        } else {
                            pointList.next.show();
                        }
                        //if (num == 1) {
                        //    pointList.renderContainer.html(template);
                        //} else {
                        //    pointList.renderContainer.append(template);
                        //}

                        pointList.renderContainer.append(template);
                    }
                }
            })
        }
    };
    pointList.getList(pointList.num, pointList.size);
    pointList.next.on('click', function () {
        pointList.num ++;
        pointList.getList(pointList.num, pointList.size);
    });
    pointList.btnSearch.click(function () {
        pointList.getList(pointList.num, pointList.size, pointList.search.val());
    });
    //搜索
    //pointList.search.keydown(function () {
    //    if (event.keyCode == 13) {
    //        pointList.getList(pointList.num, pointList.size, pointList.search.val());
    //    }
    //});
    //预定详情
    $(document).on('click', '.go-detail', function (e) {
        e.stopPropagation();
        var id = $(this).find('.title').attr('data-id');
        window.location.href = 'http://' + window.location.host + '/user/expert-service-detail.jsp?id=' + id;
    });
});
