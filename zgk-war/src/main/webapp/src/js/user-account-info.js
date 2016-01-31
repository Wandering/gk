define(['commonjs', 'tips', 'timeFormat', 'uploadFun', 'cookie'], function (util, tips, timeFormat, uploadFun, cookie) {
    require('../css/user/user-account-info.css');
    require('webuploaderCss');
    require('../lib/laydate-master/need/laydate.css');
    require('../lib/laydate-master/skins/default/laydate.css');
    require('laydateJs');
    var dialog = require('dialog');
    $(function () {

        var phoneNum = cookie.getCookieValue('phone');

        $('#modify-pwd').on('click', function () {
            var formHtml = ''
                + '        <form class="form-horizontal modify-pwd-form">'
                + '    <div class="form-group">'
                + '        <div class="col-sm-12">'
                + '            <div class="account-number">'
                + '        <b>登录账号</b><span class="number" id="user-phone">'+ phoneNum +'</span>'
                + '    </div>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label class="sr-only" for="current-psd">当前密码</label>'
                + '        <div class="col-sm-12">'
                + '            <input type="password" class="form-control" id="current-psd" placeholder="当前密码">'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label class="sr-only" for="new-psd">新密码</label>'
                + '        <div class="col-sm-12">'
                + '            <input type="password" class="form-control" id="new-psd" placeholder="新密码">'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label class="sr-only" for="confirm-psd">确认密码</label>'
                + '        <div class="col-sm-12">'
                + '            <input type="password" class="form-control" id="confirm-psd" placeholder="确认密码">'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group modify-pwd-btn-box">'
                + '<div class="col-sm-12">'
                + '<div id="tips-pwd" class="alert alert-danger" role="alert"></div>'
                + '        <button class="btn btn-primary modify-pwd-btn" type="button" id="modify-pwd-btn">提交</button>'
                + '        <span id="tips"></span>'
                + '    </div>'
                + '    </div>'
                + '    </form>';
            dialog('修改密码', formHtml);


            //修改密码
            $('body').on('click', '#modify-pwd-btn', function () {
                var currentPsd = $('#current-psd');
                var newPsd = $('#new-psd');
                var confirmPsd = $('#confirm-psd');
                if (currentPsd.val() == '') {
                    tips('#tips-pwd', '当前密码不能为空');
                    return false;
                }
                if ($.trim(currentPsd.val()).length > 16 && $.trim(currentPsd.val()).length < 6) {
                    tips('#tips-pwd', '密码输入有误，6-16位');
                    return false;
                }
                if (newPsd.val() == '') {
                    tips('#tips-pwd', '新密码不能为空');
                    return false;
                }
                if ($.trim(newPsd.val()).length > 16 && $.trim(newPsd.val()).length < 6) {
                    tips('#tips-pwd', '新输入有误，6-16位');
                    return false;
                }
                if (confirmPsd.val() == '') {
                    tips('#tips-pwd', '确认密码不能为空');
                    return false;
                }
                if ($.trim(confirmPsd.val()).length > 16 && $.trim(confirmPsd.val()).length < 6) {
                    tips('#tips-pwd', '新输入有误，6-16位');
                    return false;
                }
                if ($.trim(confirmPsd.val()) != $.trim(newPsd.val())) {
                    tips('#tips-pwd', '两次密码输入不一致');
                    return false;
                }
                util.ajaxFun(util.INTERFACE_URL.postModifyPassword, 'POST', {
                    oldPassword: currentPsd.val(),//旧密码
                    password: newPsd.val()//新密码
                }, function (res) {
                    if (res.rtnCode == '0000000') {
                        window.location.href = 'http://' + window.location.host + '/static/login.html';
                    } else {
                        tips('#tips-pwd', res.msg);
                    }
                });
            });
        });



        $('#banner-info').prepend(require('html!../user-banner.html'));
        laydate({
            elem: '#user-birthday',
            festival: true,
            istoday: false,
            min: '1960-01-01 00:00:00',
            max: '2010-01-01 00:00:00'
        });
        var avatarImg = require('../img/icon_default.png');
        //获取用户信息
        util.ajaxFun(util.INTERFACE_URL.getUserInfo, 'GET', {}, function (res) {
            if (res.rtnCode == '0000000') {
                var personListData = res.bizData;
                var avatar = '';
                if (personListData.icon == '' || personListData.icon == null) {
                    avatar = avatarImg;
                } else {
                    avatar = personListData.icon
                }
                $('.number').text(cookie.getCookieValue('phone'));
                $('#avatar-img,.user-avatar').attr('src', avatar);
                $('#user-name').val(personListData.name);
                $('#school-name').val(personListData.schoolName);
                $('#user-birthday').val(personListData.birthdayDate ? timeFormat(personListData.birthdayDate, 'yyyy-MM-dd') : '1970-01-01');
                $('input[name="sex"][value="' + personListData.sex + '"]').attr('checked', true);
                $('input[name="subject"][value="' + personListData.subjectType + '"]').attr('checked', true);
                $('#user-email').val(personListData.mail);
                $('#user-qq').val(personListData.qq);
                Area.init(personListData);
                Area.addEventForArea();
            }
        });
        //省市地区
        var province = '';
        var city = '';
        var county = '';
        var Area = {
            data: [],
            init: function (personListData) {
                var that = this;
                util.ajaxFun(util.INTERFACE_URL.getAllRegion, 'GET', {}, function (ret) {
                    if ('0000000' === ret.rtnCode) {
                        that.data = ret.bizData;
                        $('#province').html(that.render(that.data, true));
                        if (personListData.provinceId) {
                            $('#province').val(personListData.provinceId);
                            that.changeProvince(personListData.provinceId);
                            $('#city').val(personListData.cityId);
                            that.changeCity(personListData.cityId)
                            $('#county').val(personListData.countyId);
                        }
                    } else {
                        tips('#tips', res.msg);
                    }
                });
            },
            render: function (data, flag) {
                var html = [];
                if (flag) {
                    html.push('<option>请选择...</option>');
                }
                $.each(data, function (i, value) {
                    html.push('<option value="' + value.id + '">' + value.name + '</option>');
                });
                return html.join('');
            },
            changeProvince: function (value) {
                if (value) {
                    var city = this.getCity(value);
                    if (city && city.length > 0) {
                        $('#city').html(this.render(city));
                        this.changeCity(city[0].id);
                    } else {
                        $('#city').html('<option>请选择...</option>');
                    }
                }
            },
            changeCity: function (value) {
                var provinceId = $('#province').val();
                if (value && provinceId) {
                    var countyList = this.getCounty(provinceId, value);
                    if (countyList && countyList.length > 0) {
                        $('#county').html(this.render(countyList));
                    } else {
                        $('#county').html('<option>请选择...</option>');
                    }
                }
            },
            addEventForArea: function () {
                var that = this;
                $('#province').change(function (e) {
                    var value = this.value;
                    that.changeProvince(value);
                });

                $('#city').change(function (e) {
                    var value = this.value;
                    that.changeCity(value);
                });
            },
            getCity: function (id) {
                for (var i = 0, len = this.data.length; i < len; i++) {
                    if (this.data[i].id == id) {
                        return this.data[i].cityList;
                    }
                }
            },
            getCounty: function (provinceId, cityId) {
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

        function transdate(endTime) {
            var date = new Date();
            date.setFullYear(endTime.substring(0, 4));
            date.setMonth(endTime.substring(5, 7) - 1);
            date.setDate(endTime.substring(8, 10));
            date.setHours(endTime.substring(11, 13));
            date.setMinutes(endTime.substring(14, 16));
            date.setSeconds(endTime.substring(17, 19));
            return Date.parse(date) / 1000;
        }


        // 登录提交
        $('#submit-btn').on('click', function () {
            //修改信息
            var sex = $('input[name="sex"]:checked').val()
                , subject = $('input[name="subject"]:checked').val()
                , school = $.trim($('#school-name').val())
                , str = $.trim($('#user-birthday').val())
                , birthdayDate = transdate(str)
                , name = $.trim($('#user-name').val()) // 用户名

            if (name.length == 0) {
                tips('#tips', '用户名不能为空');
                return false;
            }
            if (name.length > 10) {
                tips('#tips', '用户名不能大于10个字');
                return false;
            }
            if (school.length == 0) {
                tips('#tips', '学校名不能为空');
                return false;
            }
            if (school.length > 20) {
                tips('#tips', '学校名不能大于20个字');
                return false;
            }
            var qq = $.trim($('#user-qq').val());
            var mail = $.trim($('#user-email').val());

            if (qq.length != 0 || mail.length != 0) {
                var mail_reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                if (!mail_reg.test(mail)) {
                    tips('#tips', '邮箱填写有误');
                    return false;
                }
                var qq_reg = /^\s*[.0-9]{5,11}\s*$/;
                if (!qq_reg.test(qq) || qq.length > 20) {
                    tips('#tips', 'QQ号码输入有误');
                    return false;
                }
            }
            var img_url = $('#avatar-img').attr('src');

            var provinceId = $('#province').val(),
                cityId = $('#city').val(),
                countyId = $('#county').val();
            util.ajaxFun(util.INTERFACE_URL.postUpdateUserInfo, 'POST', {
                name: name,
                provinceId: provinceId,
                cityId: cityId,
                countyId: countyId,
                schoolName: school,
                sex: sex,
                birthdayDate: birthdayDate,
                subjectType: subject,
                mail: mail,
                icon: img_url,
                qq: qq
            }, function (res) {
                console.log(res)
                if (res.rtnCode == '0000000') {
                    tips('#tips', '信息更新成功');
                    window.location.href = "/static/user-account-info.html";
                } else {
                    tips('#tips', res.msg);
                }
            });


        });


    });


});



