define(function (require) {
    var $ = require('$');
    require('header-user');
    require('laydate');
    require('swiper');
    //编辑框
    var editor;
    KindEditor.ready(function (K) {
        var location = window.location;
        var redirectPath = location.protocol + '//' + location.host + '/question/proxy.html';
        editor = K.create('textarea[name="content"]', {
            resizeType: 1,
            height:380,
            allowImageRemote: false,
            formatUploadUrl: false,
            uploadJson: "http://10.21.67.8:8080/file/upload/saveiframefile.shtml?redirectPath=" + redirectPath,
            filePostName: "file",
            allowPreviewEmoticons: false,
            allowFileManager: false,
            items: ['plainpaste', 'insertorderedlist', 'insertunorderedlist', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', '|', 'image', 'fullscreen'],
            afterBlur: function () {
                this.sync();
            },
            afterChange: function () {
                //var limitNum = 6000;  //设定限制字数
                //var pattern = '还可以输入<var class="num" id="num">' + limitNum + '</var>字';
                //$('.KindEditor .word_count').html(pattern); //输入显示
                //if (this.count('text') > limitNum) {
                //    pattern = ('已经超过<var class="num c-c00" id="num">' + (limitNum - this.count('text')) + '</var>字');
                //} else {
                //    //计算剩余字数
                //    var result = limitNum - this.count('text');
                //    pattern = '还可以输入<var class="num" id="num">' + result + '</var>字';
                //}
                //if (this.count('text') > 0) {
                //    $('#description').val(this.count('text'));
                //    $('.ke-container').removeClass('n-invalid');
                //} else {
                //    $('#description').val("")
                //}
                //$('.KindEditor .word_count').html(pattern); //输入显示
                //console.log(editor.count('text') )
            },
            //上传文件后执行的回调函数,获取上传图片的路径
            afterUpload: function (res) {
                console.log(res)
            }
        });
    });
    //开始日期，结束日期
    //var start = {
    //    elem: '.data-start',
    //    format: 'YYYY/MM/DD hh:mm:ss',
    //    min: laydate.now(), //设定最小日期为当前日期
    //    max: '2099-06-16 23:59:59', //最大日期
    //    istime: true,
    //    istoday: false,
    //    choose: function(datas){
    //        end.min = datas; //开始日选好后，重置结束日的最小日期
    //        end.start = datas //将结束日的初始值设定为开始日
    //    }
    //};
    //var end = {
    //    elem: '.data-end',
    //    format: 'YYYY/MM/DD hh:mm:ss',
    //    min: laydate.now(),
    //    max: '2099-06-16 23:59:59',
    //    istime: true,
    //    istoday: false,
    //    choose: function(datas){
    //        start.max = datas; //结束日选好后，重置开始日的最大日期
    //    }
    //};

    $('.btn-submit').click(function () {
        var s = $('.data-start').val();
        var e = $('.data-end').val();
        var start = Date.parse(new Date(s)) / 1000;
        var end = Date.parse(new Date(e)) / 1000;
        //提交内容信息
        console.log($('#content').val());
        $.ajax({
            url: '/appointment/addAppointment.do',
            dataType: 'json',
            type: 'post',
            data: {
                "title": $('.order-theme').val(),
                "content": $('#content').val(),
                "startDate": start,
                "endDate": end,
                "name": $('.name').val(),
                "mobile": $('.mobile').val(),
                "qq": $('.qq').val()
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    console.log(res);
                }
            }
        })
    });
});
