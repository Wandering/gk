define(function (require) {
    var $ = require('$');
    $(function(){
        //
        $('.volunteer-flow3-table').on('click','.open-flow3',function(){
            $('.tansLayer,.volunteer-flow3-layer').show();
        });


        $('#volunteer-flow3-layer').on('click','.close-btn',function(){
            $('#volunteer-flow3-layer,.tansLayer').hide();
        });

        console.log("params:"+params);



        $.ajax({
            url: '/guide/school.do',
            type: 'GET',
            dataType: 'JSON',
            data:JSON.parse(params),
            success: function (res) {
                console.log(res);

                if (res.rtnCode == "0000000") {


                }
            }
        });


    })
});
