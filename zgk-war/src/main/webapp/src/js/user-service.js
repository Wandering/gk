define(['commonjs'], function (util) {
    require('../css/user/user-account-info.css');
    $(function () {
        $('#banner-info').prepend(require('html!../user-banner.html'));
        var yxxxImg = require('../img/yxxx-img.png');
        $('#yxxx-img').attr('src',yxxxImg);
        var zyxxImg = require('../img/zyxx-img.png');
        $('#zyxx-img').attr('src',zyxxImg);
        var zyxxsImg = require('../img/zyxxs-img.png');
        $('#zyxxs-img').attr('src',zyxxsImg);
        var lqnyyc = require('../img/lqnyyc.png');
        $('#lqnyyc').attr('src',lqnyyc);
        var yxyc = require('../img/yxyc.png');
        $('#yxyc').attr('src',yxyc);

        var zytb = require('../img/zytb.png');
        $('#zytb').attr('src',zytb);
        var zypc = require('../img/zypc.png');
        $('#zypc').attr('src',zypc);
        var gkxt = require('../img/gkxt.png');
        $('#gkxt').attr('src',gkxt);





    });


});



