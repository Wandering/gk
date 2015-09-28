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
    $.get('/vip/getAccount.do', function (res) {
        $('.account-tel').text(res.bizData.account);
    });
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
            $('.name').attr('value', personListData.name);
            $('.school').attr('value', personListData.schoolName);
            $('.birthdayDate').attr('value', getTime(personListData.birthdayDate));
            //console.info(getTime(personListData.birthdayDate));
            $('.sex').attr('value', personListData.sex);
            $('.subject').attr('value', personListData.subjectType);
            $('.mail').attr('value', personListData.mail);
            $('.qq').attr('value', personListData.qq);
            if (personListData.sex == 1) {
                $('#sex_m').attr('checked', true)
            }
            if (personListData.subjectType == 1) {
                $('#subject_l').attr('checked', true)
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
    $('.content').fadeIn();
    //头像上传
    //setTimeout(function () {
    //    //初始化文件上传
    //    var errorCodes = ["-100", "-110", "-120", "-130"];
    //    var errorMsgs = ["文件数量不能超过(5)", "文件超过大小限制(10MB)", "零字节的文件", "无效的文件类型"];
    //    $("#uploadify").uploadify({
    //        'swf': "/static/bower_components/uploadify/uploadify.swf",
    //        'fileObjName': 'file',
    //        'uploader': "http://pre.file.xy189.cn/file/upload/savefile.shtml",
    //        'auto': true,
    //        'removeTimeout': 0,
    //        'multi': false,
    //        'uploadLimit': 0,
    //        'fileSizeLimit': "10MB",
    //        'fileTypeDesc': '图片文件(*.jpg;*.png;*.gif;*.jpeg)',
    //        'buttonText': '点击上传',
    //        'fileTypeExts': "*.jpg;*.png;*.gif;*.jpeg",
    //        'progressData': 'percentage',
    //        'speed': 'percentage',
    //        'queueSizeLimit': 5,
    //        'removeCompleted': true,
    //        'onSelect': function (file) {
    //            this.queueData.filesErrored = 0;
    //        },
    //        'onOpen': function (event, ID, fileObj) {
    //        },
    //        'onSelectError': function (file, errorCode, errorMsg) {
    //            for (var i = 0; i < errorCodes.length; i++) {
    //                if (errorCodes[i] == errorCode) {
    //                    this.queueData.errorMsg = errorMsgs[i];
    //                }
    //            }
    //        },
    //        'onCancel': function (file) {
    //            //alert(file.name);
    //        },
    //        'onFallback': function () {
    //            alert("浏览器不能兼容Flash,请下载最新版!");
    //        },
    //        'onClearQueue': function (queueItemCount) {
    //        },
    //        'onUploadStart': function (file) {
    //        },
    //        'onUploadSuccess': function (file, data, response) {
    //            //获取到data处理
    //            console.log(data);
    //            var obj = JSON.parse(data);
    //            var data = {'userIcon': obj.data.url};
    //            $.ajax({
    //                type: "post",
    //                url: "/expert/updateUser.do",
    //                dataType: "json",
    //                data: data,
    //                async: false,
    //                success: function (res) {
    //                    if (res.rtnCode !== '0000000') {
    //                        alert(res.msg);
    //                    } else {
    //                        //修改成功
    //                        console.log(res);
    //                        $('.expert-avatar').find('.tips').html('更新成功').fadeOut(5000);
    //                    }
    //                }
    //            });
    //
    //            $('.user-icon').attr('src', obj.data.url);
    //            var id = this.wrapper.selector;
    //            $(id).uploadify('settings', 'buttonText', '正在加载');
    //            $("img[data-icon]").each(function () {
    //                $(this).attr("src", obj.data.url + "!100?t=" + new Date().getTime());
    //            });
    //            $(id).uploadify('settings', 'buttonText', '重新上传');
    //        },
    //        'onUploadError': function (file, errorCode, errorMsg, errorString) {
    //            switch (errorMsg) {
    //                case '400':
    //                    $('#' + file.id).find('.data').html(" - 上传失败，文件超过大小限制(2MB)");
    //                    break;
    //                case '401':
    //                    $('#' + file.id).find('.data').html(" - 上传失败，零字节的文件");
    //                    break;
    //                case '402':
    //                    $('#' + file.id).find('.data').html(" - 上传失败，无效的文件类型");
    //                    break;
    //                case '500':
    //                    $('#' + file.id).find('.data').html(" - 上传失败，服务器问题");
    //                    break;
    //            }
    //        },
    //        'onDialogClose': function (queueDat) {
    //        }
    //    });
    //}, 10);
    var sex, subject;
    //1男，理科，0女，文科
    if ($('#sex_w').attr('checked')) {
        sex = 0;
    } else {
        sex = 1;
    }
    if ($('#subject_w').attr('checked')) {
        subject = 0;
    } else {
        subject = 1;
    }
    $('.btn-submit').click(function () {
        //修改信息
        var school = $('.school').val();
        var str = $('.birthdayDate').val();
        var birthdayDate = Date.parse(new Date(str)) / 1000;



        //var cmbProvince;
        //var sexM = $('#sex_m').attr('value');
        //var sexW = $('#sex_w').attr('value');
        //var subjectW = $('#subject_w').attr('value');
        //var subjectL = $('#subject_l').attr('value');


        var name = $('.name').val().trim();
        if (name.length > 10) {
            $('.error-tips').text('用户名不能大于10个字').fadeIn();
            return false;
        }
        var mail = $('.mail').val().trim();
        var mail_reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (!mail_reg.test(mail)) {
            $('.error-tips').text('邮箱填写有误').fadeIn();
            return false;
        }
        var qq_reg = /^\s*[.0-9]{5,11}\s*$/;
        var qq = $('.qq').val().trim();
        if (!qq_reg.test(qq)) {
            $('.error-tips').text('QQ号码输入有误').fadeIn();
            return false;
        }

        $.ajax({
            url: '/info/updateUserInfo.do',
            dataType: 'json',
            type: 'post',
            data: {
                name: name,
                countyId: '2',
                schoolName: school,
                sex: sex,
                birthdayDate: birthdayDate,
                subjectType: subject,
                mail: mail,
                icon: 'http://img1.2345.com/duoteimg/qqTxImg/2013/12/ka_3/04-054658_103.jpg',
                qq: qq
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    $('.error-tips').text('信息更新成功').fadeIn(1000).fadeOut(2000);
                } else {
                    $('.content').text(res.msg);
                }
            }
        })



    });

});