$('#login-btn').on('click', function () {
    var account = $('#user-name').val();
    var password = $('#password').val();
    var url = '/expert/admin/login/login.do';
    var data = {
        account:account,
        password:$.md5(password)
    };
    // var md5ChildLogin = $.md5($childPwd);
    // var baseChildPwd = $.base64.btoa($childPwd);
    // $.base64.utf8encode = true;
    Common.ajaxFun(url,"POST", data, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            Common.cookie.setCookie('expertsId', data.id);
            Common.cookie.setCookie('expertsName', data.expertName);
            Common.cookie.setCookie2('expert_user_info', data.id+"|"+data.expertName);
            console.log(data.id+"="+data.expertName);
            var siderMenu = data.meuns;
            var siderMenuJson = {};
            for(var i=0;i<siderMenu.length;i++){
                siderMenuJson[i]=siderMenu[i];
            }
            siderMenuJson = JSON.stringify(siderMenuJson);
            Common.cookie.setCookie('siderMenu', siderMenuJson);
            window.location.href = '/expert/admin/info-management.do';
        } else {
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg('出错了');
    });
});
