define(function (require) {
    var $ = require('$');
    require('laydate');
    var getTime = require('getTimes');
    function errorTips(txt) {
        $('.error-tips').text(txt).fadeIn(1000).fadeOut(1000);
    }
    // 获取手机号
    $.get('/vip/getAccount.do', function (res) {
        console.log(res)
        var phoneN = res.bizData.account;
        if (res.rtnCode == '0000000') {
            if(phoneN){
                $('#account-tel').text(phoneN);
            }
        } else {
            console.log(res.msg);
        }
    });
    // 获取用户信息
    $.get('/info/getUserInfo.do?'+new Date().getTime(), function (res) {
        console.log(res)
        if (res.rtnCode == '0000000') {
            var personListData = res.bizData;
            var avatar = '';
            if (personListData.icon == '' || personListData.icon == null) {
                avatar = 'http://cdn.gaokao360.net/static/global/common/images/icon_default.png';
            } else {
                avatar = personListData.icon
            }
            $('#avatar-img').attr('src', avatar);
            $('#userName').val(personListData.name);
            $('#school').val(personListData.schoolName);
            $('#birthdayDate').val(personListData.birthdayDate ? getTime.getTime(personListData.birthdayDate,'yyyy-MM-dd') : '1970-01-01');
            $('input[name="sex"][value="'+ personListData.sex +'"]').attr('checked',true);
            $('input[name="subject"][value="'+ personListData.subjectType +'"]').attr('checked',true);
            $('#email').val(personListData.mail);
            $('#qq').val(personListData.qq);
            Area.init(personListData);
            Area.addEventForArea();
        }
    });

    //省市地区
    var cmbProvince = '';
    var cmbCity = '';
    var cmbArea = '';
    var Area = {
        data: [],
        init: function (personListData) {
            var that = this;
            $.get('/region/getAllRegion.do', function (ret) {
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
                    $('#cmbCity').html(this.render(city));
                    this.changeCity(city[0].id);
                } else {
                    $('#cmbCity').html('<option>请选择...</option>');
                }
            }
        },
        changeCity: function (value) {
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
        addEventForArea: function () {
            var that = this;
            $('#cmbProvince').change(function (e) {
                var value = this.value;
                that.changeProvince(value);
            });

            $('#cmbCity').change(function (e) {
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

    function transdate(endTime){
        var date=new Date();
        date.setFullYear(endTime.substring(0,4));
        date.setMonth(endTime.substring(5,7)-1);
        date.setDate(endTime.substring(8,10));
        date.setHours(endTime.substring(11,13));
        date.setMinutes(endTime.substring(14,16));
        date.setSeconds(endTime.substring(17,19));
        return Date.parse(date)/1000;
    }


    $('#btn-submit').click(function () {

        //修改信息
        var sex = $('input[name="sex"]:checked').val()
            , subject = $('input[name="subject"]:checked').val()
            , school = $.trim($('#school').val())
            , str = $.trim($('#birthdayDate').val())
            , birthdayDate = transdate(str)
            , name = $.trim($('#userName').val());

        if (name.length == 0) {
            errorTips('用户名不能为空');
            return false;
        }
        if (name.length > 10) {
            errorTips('用户名不能大于10个字');
            return false;
        }
        if( school.length==0){
            errorTips('学校名不能为空');
            return false;
        }
        if( school.length>20){
            errorTips('学校名不能大于20个字');
            return false;
        }
        var qq = $.trim($('#qq').val());
        var mail = $.trim($('#email').val());

        if (qq.length != 0 || mail.length != 0) {
            var mail_reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (!mail_reg.test(mail)) {
                errorTips('邮箱填写有误');
                return false;
            }
            var qq_reg = /^\s*[.0-9]{5,11}\s*$/;
            if (!qq_reg.test(qq) || qq.length > 20) {
                $('.error-tips').text('QQ号码输入有误').fadeIn();
                return false;
            }
        }
        var img_url = $('#avatar-img ').attr('src');

        var provinceId = $('#cmbProvince').val(),
            cityId = $('#cmbCity').val(),
            countyId = $('#cmbArea').val();
        $.ajax({
            url: '/info/updateUserInfo.do',
            type: 'POST',
            dataType: 'json',
            data: {
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
            },
            success: function (res) {
                console.log(res)
                if (res.rtnCode == '0000000') {
                    $('.error-tips').text('信息更新成功').fadeIn(1000).fadeOut(2000);
                    window.location.href="/user/personal-info.jsp";
                } else {
                    $('.content').text(res.msg);
                }
            }
        })
    });
});