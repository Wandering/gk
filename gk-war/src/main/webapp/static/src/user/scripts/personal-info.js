/**
 * Created by pdeng on 15/9/24.
 */
define(function (require) {
    var $ = require('$');
    //获取用户信息
    $.get('/info/getUserInfo.do', function (res) {
        if (res.rtnCode == '0000000') {
            var personListData = res.bizData;
            console.log(personListData);
            $('.avatar-img').attr('src', personListData.icon);
            $('.account').text(personListData.account);
            $('.name').attr('value', personListData.name);
            $('.school').attr('value', personListData.schoolName);
            $('.birthdayDate').attr('value', personListData.birthdayDate);
            $('.sex').attr('value', personListData.sex);
            $('.subject').attr('value', personListData.subjectType);
            $('.mail').attr('value', personListData.mail);
            $('.qq').attr('value', personListData.qq);
        } else {
            alert(res.msg);
        }
    });
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