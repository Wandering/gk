/**
 * Created by pdeng on 15/9/24.
 */
define(function (require) {
    var $ = require('$');
    $.get('/info/getUserInfo.do', function (res) {
        if (res.rtnCode == '0000000') {
            var personListData = res.bizData;
            console.log(personListData);
            $('.account').text(personListData.account);
            $('.name').attr('value',personListData.name);
            $('.school').attr('value',personListData.schoolName);
            $('.birthdayDate').attr('value',personListData.birthdayDate);
            $('.sex').attr('value',personListData.sex);
            $('.subject').attr('value',personListData.subject);
            $('.mail').attr('value',personListData.mail);
            $('.qq').attr('value',personListData.qq);
        }
    })

});