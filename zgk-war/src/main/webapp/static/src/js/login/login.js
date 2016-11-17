$('#login-btn').on('click', function () {
    var account = $('#user-name').val();
    var password = $('#password').val();
    var url = '/expert/admin/login/login.do';
    var data = {
        account:account,
        password:password
    };
    Common.ajaxFun(url,"POST", data, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            Common.cookie.setCookie('user_id', data.id);
            Common.cookie.setCookie('user_name', data.expertName);
            var siderMenu = data.meuns;
            var siderMenuJson = {};
            for(var i=0;i<siderMenu.length;i++){
                siderMenuJson[i]=siderMenu[i];
            }
            siderMenuJson = JSON.stringify(siderMenuJson);
            Common.cookie.setCookie('siderMenu', siderMenuJson);
            window.location.href = '/expert/admin/index.do';
        } else {
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg('出错了');
    });
});
