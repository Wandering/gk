define(function (require) {

    function GetCookie(sMainName, sSubName) {
        var re = new RegExp((sSubName ? sMainName + "=(?:.*?&)*?" + sSubName + "=([^&;$]*)" : sMainName + "=([^;$]*)"), "i");
        return re.test(unescape(document.cookie)) ? RegExp["$1"] : "";
    }
    if (!GetCookie("gkuser") || GetCookie("gkuser") == '""') {
        window.location.href = '/login/login.jsp';
    }

    function getUrLinKey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    }

    var $ = require('$');
    require('header-user');
    //编辑框
    var editor;
    KindEditor.ready(function(K) {
        var location = window.location;
        var redirectPath = location.protocol + '//' + location.host + '/question/proxy.html';
        editor = K.create('textarea[name="content"]', {
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

    function submitQuestion() {
        //var title = $('#title').val();
        var content = $('#content').val();
        //if (!title) {
        //    $('#error').show().html('标题不能为空！');
        //    setTimeout(function() {
        //        $('#error').hide();
        //    }, 2000)
        //    return;
        //}

        if (!content) {
            $('#error').show().html('内容不能为空！');
            setTimeout(function() {
                $('#error').hide();
            }, 2000)
            return;
        }
        var text = editor.text().replace(/<[^>]+>/ig, '');
        if (!text) {
            $('#error').show().html('请输入问题描述！');
            setTimeout(function() {
                $('#error').hide();
            }, 2000)
            return;
        }
        var flag = false;
        if (!flag) {
            flag = true;
            $.ajax({
                type: 'post',
                url: '/question/insert.do',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {
                    question:content
                    //expertId:questionId,
                    //questionId:questionId,
                    //disableExpertId:disableExpertId
                },
                dataType: 'json',
                success: function(data) {
                    flag = false;
                    if ('0000000' === data.rtnCode) {
                        editor.html('');
                        $('#custom_body').html('<p class="error-contnet">提交成功！</p>');
                        $('#custom_model').fadeIn(500);
                        $('#custom_model').off('click');
                        $('#custom_model').on('model_buttonclick', function(e) {
                            e.stopPropagation();
                        });
                        $('#model_button').off('click');
                        $('#model_button').on('click', function(e) {
                            $('#custom_model').fadeOut(500);
                            //window.location.href='/question/ask.jsp?path=online'
                        });
                        var path = getUrLinKey('path');
                        console.log(path)
                        if ('online' === path) {
                            window.location.href = '/user/online-answer.jsp?method=ask';
                        }
                    } else {
                        $('#error').show().html(data.msg);
                    }

                    if ('1000004' === data.rtnCode) {
                        $('#error_tip').html(data.msg + ",请<a target='_blank' href='/login/login.jsp'>登录</a>");
                        $('#error_tip').show();
                    }
                },
                error: function(data) {
                    flag = false;
                    $('#error').show().html('服务器错误。');
                }
            });
        }

    }

    $(document).ready(function() {
        $('#submit_question').on('click', function(e) {
            submitQuestion();
        });
    })

});
