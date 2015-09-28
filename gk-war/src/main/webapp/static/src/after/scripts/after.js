define(function (require) {
    var $ = require('$');

    $('.open-flow3').on('click',function(){
        $('.tansLayer,.volunteer-flow3-layer').show();
    });

    $('.close-flow3-layer').on('click',function(){
        $('.tansLayer,.volunteer-flow3-layer').hide();
    })

});
