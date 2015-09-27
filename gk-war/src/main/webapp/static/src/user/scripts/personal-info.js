/**
 * Created by pdeng on 15/9/24.
 */
define(function (require) {
    var $ = require('$');
    require('header-user');
    require('laydate');
    //require('uploadify');
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

    //获取用户信息
    $.get('/info/getUserInfo.do', function (res) {
        if (res.rtnCode == '0000000') {
            var personListData = res.bizData;
            console.log(personListData);
            var avatar;
            if (personListData.icon == '' || personListData.icon == null) {
                avatar = '/static/dist/common/images/icon_default.png';
            } else {
                avatar = personListData.icon
            }
            $('.avatar-img').attr('src', avatar);
            $('.avatar-box').show();
            $('.account-tel').text(personListData.account);
            console.log(personListData.account);//暂缺字段
            $('.name').attr('value', personListData.name);
            $('.school').attr('value', personListData.schoolName);
            $('.birthdayDate').attr('value', getTime(personListData.birthdayDate));
            $('.sex').attr('value', personListData.sex);
            $('.subject').attr('value', personListData.subjectType);
            $('.mail').attr('value', personListData.mail);
            $('.qq').attr('value', personListData.qq);
            if (personListData.sex == 1) {
                $('#sex_m').attr('checked', true)
            }
            if (personListData.subjectType == 1) {
                $('#subject_w').attr('checked', true)
            }
        } else {
            alert(res.msg);
        }
    });
    //省市地区
    $.ajax({
        url: '/region/getAllRegion.do',
        dataType: 'json',
        type: 'get',
        data: {},
        success: function (res) {
            console.log(res)
        }
    });
    //头像上传
    $(function () {
        setTimeout(function () {
            //初始化文件上传
            var errorCodes = ["-100", "-110", "-120", "-130"];
            var errorMsgs = ["文件数量不能超过(5)", "文件超过大小限制(10MB)", "零字节的文件", "无效的文件类型"];
            $("#uploadify").uploadify({
                'swf': "/static/bower_components/uploadify/uploadify.swf",
                'fileObjName': 'file',
                'uploader': "http://pre.file.xy189.cn/file/upload/savefile.shtml",
                'auto': true,
                'removeTimeout': 0,
                'multi': false,
                'uploadLimit': 0,
                'fileSizeLimit': "10MB",
                'fileTypeDesc': '图片文件(*.jpg;*.png;*.gif;*.jpeg)',
                'buttonText': '点击上传',
                'fileTypeExts': "*.jpg;*.png;*.gif;*.jpeg",
                'progressData': 'percentage',
                'speed': 'percentage',
                'queueSizeLimit': 5,
                'removeCompleted': true,
                'onSelect': function (file) {
                    this.queueData.filesErrored = 0;
                },
                'onOpen': function (event, ID, fileObj) {
                },
                'onSelectError': function (file, errorCode, errorMsg) {
                    for (var i = 0; i < errorCodes.length; i++) {
                        if (errorCodes[i] == errorCode) {
                            this.queueData.errorMsg = errorMsgs[i];
                        }
                    }
                },
                'onCancel': function (file) {
                    //alert(file.name);
                },
                'onFallback': function () {
                    alert("浏览器不能兼容Flash,请下载最新版!");
                },
                'onClearQueue': function (queueItemCount) {
                },
                'onUploadStart': function (file) {
                },
                'onUploadSuccess': function (file, data, response) {
                    //获取到data处理
                    console.log(data);
                    var obj = JSON.parse(data);
                    var data = {'userIcon': obj.data.url};
                    $.ajax({
                        type: "post",
                        url: "/expert/updateUser.do",
                        dataType: "json",
                        data: data,
                        async: false,
                        success: function (res) {
                            if (res.rtnCode !== '0000000') {
                                alert(res.msg);
                            } else {
                                //修改成功
                                console.log(res);
                                $('.expert-avatar').find('.tips').html('更新成功').fadeOut(5000);
                            }
                        }
                    });

                    $('.user-icon').attr('src', obj.data.url);
                    var id = this.wrapper.selector;
                    $(id).uploadify('settings', 'buttonText', '正在加载');
                    $("img[data-icon]").each(function () {
                        $(this).attr("src", obj.data.url + "!100?t=" + new Date().getTime());
                    });
                    $(id).uploadify('settings', 'buttonText', '重新上传');
                },
                'onUploadError': function (file, errorCode, errorMsg, errorString) {
                    switch (errorMsg) {
                        case '400':
                            $('#' + file.id).find('.data').html(" - 上传失败，文件超过大小限制(2MB)");
                            break;
                        case '401':
                            $('#' + file.id).find('.data').html(" - 上传失败，零字节的文件");
                            break;
                        case '402':
                            $('#' + file.id).find('.data').html(" - 上传失败，无效的文件类型");
                            break;
                        case '500':
                            $('#' + file.id).find('.data').html(" - 上传失败，服务器问题");
                            break;
                    }
                },
                'onDialogClose': function (queueDat) {
                }
            });
        }, 10);
    })

    //修改信息
    $('.btn-submit').click(function () {
        $.ajax({
            url: '/info/updateUserInfo.do',
            dataType: 'json',
            type: 'post',
            data: {
                name: 'zuo',
                countyId: '2',
                schoolName: '苏州中学',
                sex: '1',
                birthdayDate: '1443001519852',
                subjectType: '1',
                mail: '12345678@qq.com',
                icon: 'touxiang',
                qq: '123456'
            }
        })
    });

});