/**
 * Created by pdeng on 15/9/24.
 */
define(function (require) {
    require('laydate');
    Date.prototype.Format = function (fmt) {
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
            var avatar;
            if (personListData.icon == '' || personListData.icon == null) {
                avatar = '/static/dist/common/images/icon_default.png';
            } else {
                avatar = personListData.icon
            }
            console.log(res);
            $('.avatar-img').attr('src', avatar);
            $('.avatar-box').show();
            $('.name').attr('value', personListData.name);
            $('.school').attr('value', personListData.schoolName);
            $('.birthdayDate').attr('value', getTime(personListData.birthdayDate));
            $('.sex').attr('value', personListData.sex);
            $('.subject').attr('value', personListData.subjectType);
            $('.mail').attr('value', personListData.mail);
            $('.qq').attr('value', personListData.qq);
            //$('#cmbProvince option').attr('value', personListData.provinceId);
            //$('#cmbCity option').attr('value', personListData.cityId);
            //$('#cmbArea option').attr('value', personListData.countyId);
            if (personListData.sex == 1) {
                $('#sex_m').attr('checked', true)
            } else {
                $('#sex_w').attr('checked', true)
            }
            if (personListData.subjectType == 1) {
                $('#subject_l').attr('checked', true)
            } else {
                $('#subject_w').attr('checked', true)
            }

            Area.init(personListData);
            Area.addEventForArea();
        } else {
            alert(res.msg);
        }
    });
    $('.content').fadeIn();
    //省市地区
    var cmbProvince = '';
    var cmbCity = '';
    var cmbArea = '';

    var Area = {
        data:[],
        init: function(personListData) {
            var that = this;
            $.get('/region/getAllRegion.do', function(ret) {
                if ('0000000' === ret.rtnCode) {
                    that.data = ret.bizData;
                    $('#cmbProvince').html(that.render(that.data, true));
                    if (personListData.provinceId) {
                        $('#cmbProvince').val(personListData.provinceId);
                        that.changeProvince(personListData.provinceId);
                        $('#cmbCity').val(personListData.cityId);
                        that.changeCity(personListData.cityId)
                        $('#cmbArea').val(personListData.countyId);
                    }
                }
            });
        },
        render: function(data, flag) {
            var html = [];
            if (flag) {
                html.push('<option>请选择...</option>');
            }
            $.each(data, function(i, value) {
                html.push('<option value="' + value.id + '">' + value.name + '</option>');
            });
            return html.join('');
        },
        changeProvince: function(value) {
            if (value) {
                var city = this.getCity(value);
                if (city && city.length > 0) {
                    $('#cmbCity').html(this.render(city));
                    this.changeCity(city[0].id);
                } else {
                    $('#cmbCity').html('<option>请选择...</option>');
                }
            }
        },
        changeCity: function(value) {
            var provinceId = $('#cmbProvince').val();
            if (value && provinceId) {
                var countyList = this.getCounty(provinceId, value);
                if (countyList && countyList.length > 0) {
                    $('#cmbArea').html(this.render(countyList));
                } else {
                    $('#cmbArea').html('<option>请选择...</option>');
                }
            }
        },
        addEventForArea: function() {
            var that = this;
            $('#cmbProvince').change(function(e) {
                var value = this.value;
                that.changeProvince(value);
            });

            $('#cmbCity').change(function(e) {
                var value = this.value;
                that.changeCity(value);
            });
        },
        getCity: function(id) {
            for (var i = 0, len = this.data.length; i < len; i++) {
                if (this.data[i].id == id) {
                    return this.data[i].cityList;
                }
            }
        },
        getCounty: function(provinceId, cityId) {
            for (var i = 0, len = this.data.length; i < len; i++) {
                if (this.data[i].id == provinceId) {
                    var cityList = this.data[i].cityList;
                    if (cityList.length <= 0) {
                      return null;
                    }
                    var j = 0, jlen = cityList.length;
                    for (; j < jlen; j++) {
                       if (cityList[j].id == cityId) {
                           return cityList[j].countyList;
                       }
                    }
                }
            }
        }
    };


    //$.ajax({
    //    url: '/region/getAllRegion.do',
    //    dataType: 'json',
    //    type: 'get',
    //    data: {},
    //    success: function (res) {
    //        if (res.rtnCode == '0000000') {
    //            var provinceList = res.bizData;
    //            console.info(provinceList[0].cityList[0].name);
    //            console.info(provinceList[0].cityList[0].id);
    //            console.info(provinceList[0].cityList[0].provinceId);
    //
    //            $.each(provinceList, function (i, v) {
    //                cmbProvince += '<option value="' + i + '">' + v.name + '</option>';
    //                console.log(v);
    //            });
    //            console.log(cmbProvince);
    //
    //            $('#cmbProvince').html(cmbProvince);
    //
    //        }
    //    }
    //});
    //$('#cmbProvince').onchange(function(){
    //    alert(1);
    //});
    //头像上传
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
                //$.ajax({
                //    type: "post",
                //    url: "/expert/updateUser.do",
                //    dataType: "json",
                //    data: data,
                //    async: false,
                //    success: function (res) {
                //        if (res.rtnCode !== '0000000') {
                //            alert(res.msg);
                //        } else {
                //            //修改成功
                //            console.info(res);
                //            $('.expert-avatar').find('.tips').html('更新成功').fadeOut(5000);
                //        }
                //    }
                //});

                $('.avatar-img').attr('src', obj.data.url);
                var id = this.wrapper.selector;
                $(id).uploadify('settings', 'buttonText', '正在加载');
                $("img[data-icon]").each(function () {
                    $(this).attr("src", obj.data.url + "!100?t=" + new Date().getTime());
                });
                $(id).uploadify('settings', 'buttonText', '换一个');
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

    $('.btn-submit').click(function () {
        //修改信息
        var sex = $('input[name="sex"]:checked').val();
        var subject = $('input[name="subject"]:checked').val();
        var school = $('.school').val();
        var str = $('.birthdayDate').val();
        var birthdayDate = Date.parse(new Date(str)) / 1000;
        var name = $('.name').val().trim();

        if (name.length ==0) {
            $('.error-tips').text('用户名不能为空').fadeIn();
            return false;
        }
        if (name.length > 10) {
            $('.error-tips').text('用户名不能大于10个字').fadeIn();
            return false;
        }
        var mail = $('.mail').val().trim();
        var mail_reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
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
        var img_url = $('.avatar-img ').attr('src');
        $.ajax({
            url: '/info/updateUserInfo.do',
            dataType: 'json',
            type: 'post',
            data: {
                name: name,
                provinceId: $('#cmbProvince').val(),
                cityId: $('#cmbCity').val(),
                countyId: $('#cmbArea').val(),
                schoolName: school,
                sex: sex,
                birthdayDate: birthdayDate,
                subjectType: subject,
                mail: mail,
                icon: img_url,
                qq: qq
            },
            success: function (res) {
                if (res.rtnCode == '0000000') {
                    $('.user-avatar').attr('src', img_url);
                    $('.error-tips').text('信息更新成功').fadeIn(1000).fadeOut(2000);
                } else {
                    $('.content').text(res.msg);
                }
            }
        })
    });

});