/**
 * Created by kepeng on 15/9/25.
 */

define(function (require) {
    var $ = require('$');
    require('swiper');
    //编辑框
    var editor;
    KindEditor.ready(function(K) {
        var location = window.location;
        var redirectPath = location.protocol + '//' + location.host + '/question/proxy.html';
        editor = K.create('textarea[name="content"]', {
            width : '100%',
            resizeType : 1,
            allowImageRemote : false,
            formatUploadUrl:false,
            uploadJson : "http://10.21.67.8:8080/file/upload/saveiframefile.shtml?redirectPath=" + redirectPath,
            filePostName : "file",
            allowPreviewEmoticons : false,
            allowFileManager: false,
            items : ['plainpaste','insertorderedlist', 'insertunorderedlist','forecolor', 'hilitecolor', 'bold',
                'italic', 'underline',  '|', 'image','fullscreen'],
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
            afterUpload: function (url) {
                console.log(url)
            }
        });
    });

});
