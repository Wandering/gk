/**
 * Created by pdeng on 2016/11/22.
 */

/**
 *  上传
 *  @api http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44453900
 */
var upload = function () {
    var $ = jQuery,
        $list = $('#fileLi'),
    // Web Uploader实例
        uploader;
    // 初始化Web Uploader
    uploader = WebUploader.create({
        auto: true,
        // swf文件路径
        swf: BASE_URL + '/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://cs-pro.qtonecloud.cn/rest/v1/uploadFile?userId=gk360&dirId=0&productCode=gk360&bizSystem=gk360&spaceName=gk360',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#uploaderBtn1',
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });
    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        $('#fileLi').html('');
        var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
            '<img class="thumb-img">' +
            '<div class="info">' + file.name + '</div>' +
            '</div>'
        );
        $list.append($li);
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $list.replaceWith('<span>不能预览</span>');
                return;
            }
            $('.thumb-img').attr('src', src);
        }, 100, 100);
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress span');
        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo($li)
                .find('span');
        }
        $percent.css('width', percentage * 100 + '%');
    });
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file,response) {
        layer.closeAll();
        $('#avatar-img').attr('src', response.bizData.file.fileUrl);
    });
    // 文件上传失败，现实上传出错。
    uploader.on('uploadError', function (file) {
        var $li = $('#' + file.id),
            $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }
        $error.text('上传失败');
    });
    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').remove();
    });
};

upload();
/**
 * 用户信息
 * @type {{init: UserInfo.init, addListens: UserInfo.addListens, uploadAvatar: UserInfo.uploadAvatar}}
 */
var UserInfo = {
    init: function () {
        this.fetchExpert();
        this.bindEvents();
    },
    bindEvents: function () {
        var that = this;
        $('#submit-btn').on('click', function () {
            var dataParam = that.submitVerify();
            if (dataParam) {
                that.submitForm(dataParam);
            }
        });
        $('#modify-pwd').on('click', function () {
            that.modifyPwd();
        });
    },
    submitVerify: function () {
        var paramData = {
            'imageUrl': $('#avatar-img').attr('src'), //头像
            'expertName': $('#user-name').val(),//专家姓名
            'qq': $('#user-qq').val(),//QQ
            'weixin': $('#user-wx').val()// 微信
        };
        if (paramData.expertName.length == 0) {
            layer.msg('用户名称不能为空');
            return false;
        }
        if (paramData.expertName.length > 12) {
            layer.msg('用户名称不能大于12位');
            return false;
        }
        if (paramData.qq.length == 0) {
            layer.msg('QQ账号不能为空');
            return false;
        }
        if (paramData.qq.length > 15 || paramData.qq.length < 5 || isNaN(paramData.qq)) {
            layer.msg('QQ账号输入有误');
            return false;
        }
        if (paramData.weixin.length == 0) {
            layer.msg('微信账号不能为空');
            return false;
        }
        return paramData;
    },
    submitForm: function (data) {
        Common.ajaxFun('/expert/admin/user/updateInfo.do', 'POST', data, function (res) {
            if (res.rtnCode === '0000000') {
                window.location.reload();
            }
        });
    },
    modifyPwd: function () {
        var tpl = '' +
            '<form class="modify-pwd-form">' +
            '    <div class="form-group">' +
            '            <div class="account-number">' +
            '        <b>登录账号</b><span class="number" id="user-phone">' + $('#user-phone').html() + '</span>' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="sr-only" for="current-psd">当前密码</label>' +
            '            <input type="password" class="form-control" id="current-psd" placeholder="当前密码">' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="sr-only" for="new-psd">新密码</label>' +
            '            <input type="password" class="form-control" id="new-psd" placeholder="新密码">' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="sr-only" for="confirm-psd">确认密码</label>' +
            '            <input type="password" class="form-control" id="confirm-psd" placeholder="确认密码">' +
            '    </div>' +
            '    <div class="form-group modify-pwd-btn-box">' +
            '        <div class="btn btn-primary modify-pwd-btn" id="modify-pwd-btn">提交</div>' +
            '    </div>' +
            '    </form>';
        layer.open({
            type: 1,
            title: '修改密码',
            area: ['450px', 'auto'], //宽高
            content: tpl
        });
        //修改密码
        $('body').on('click', '#modify-pwd-btn', function () {
            var currentPsd = $('#current-psd');
            var newPsd = $('#new-psd');
            var confirmPsd = $('#confirm-psd');
            if (currentPsd.val() === '') {
                layer.tips('当前密码不能为空', '#modify-pwd-btn', {
                    tips: '3',
                    scrollbar: false
                });
                return false;
            }
            if (newPsd.val() === '') {
                layer.tips('新密码不能为空', '#modify-pwd-btn', {
                    tips: '3',
                    scrollbar: false
                });
                return false;
            }
            if ($.trim(newPsd.val()).length > 16 || $.trim(newPsd.val()).length < 6) {
                layer.tips('新密码输入有误，6-16位', '#modify-pwd-btn', {
                    tips: '3',
                    scrollbar: false
                });
                return false;
            }
            if (confirmPsd.val() === '') {
                layer.tips('确认密码不能为空', '#modify-pwd-btn', {
                    tips: '3',
                    scrollbar: false
                });
                return false;
            }
            if ($.trim(confirmPsd.val()).length > 16 || $.trim(confirmPsd.val()).length < 6) {
                layer.tips('确认密码输入有误，6-16位', '#modify-pwd-btn', {
                    tips: '3',
                    scrollbar: false
                });
                return false;
            }
            if ($.trim(confirmPsd.val()) != $.trim(newPsd.val())) {
                layer.tips('两次密码输入不一致', '#modify-pwd-btn', {
                    tips: '3',
                    scrollbar: false
                });
                return false;
            }
            Common.ajaxFun('/expert/admin/login/resetPassword.do', 'POST', {
                account: $.trim($('#user-phone').html()),
                password: $.md5(currentPsd.val()),//旧密码
                newPassword: $.md5(newPsd.val())//新密码
            }, function (res) {
                if (res.rtnCode == '0000000') {
                    layer.closeAll();
                } else {
                    layer.tips(res.msg, '#modify-pwd-btn', {
                        tips: '3',
                        scrollbar: false
                    });
                }
            });
        });
    },
    fetchExpert: function () {
        Common.ajaxFun('/expert/admin/user/queryUserInfo.do', 'get', {}, function (res) {
            if (res.rtnCode === '0000000') {
                var dataJson = res.bizData;
                $('#avatar-img').attr('src',dataJson.imageUrl);
                $('#user-phone').html(dataJson.account);
                $('#user-name').val(dataJson.expertName);
                $('#user-qq').val(dataJson.qq);
                $('#user-wx').val(dataJson.weixin);
            }
        });
    }
};
UserInfo.init();


