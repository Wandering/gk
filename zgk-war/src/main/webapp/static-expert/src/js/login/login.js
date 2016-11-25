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
            var dataObj = res.bizData;
            Common.cookie.setCookie('expertsId', dataObj.id);
            Common.cookie.setCookie('expertsName', dataObj.expertName);

            // "{\"id\"|10|\"account\"|\"19283734748\"|\"expertsName\"|\"陈国庆\"}"
            Common.cookie.setCookie2('expert_user_info', dataObj.id+"|"+dataObj.account+"|"+dataObj.expertName);
            console.log(dataObj.id+"="+dataObj.expertName);
            var siderMenu = dataObj.meuns;
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
