define(function (require) {
    var $ = require('$');
    $(function(){
        //
        $('.volunteer-flow3-table').on('click','.open-flow3',function(){
            $('.tansLayer,.volunteer-flow3-layer').show();
            $('#volunteer-flow3-layer').attr('dataType',$(this).attr('dataType'))
        });


        $('#volunteer-flow3-layer').on('click','.close-btn',function(){
            $('#volunteer-flow3-layer,.tansLayer').hide();
        });

        //console.log("params:"+params);
        var params1 = {
            "m_candidateNumber":"0",
            "m_aggregateScore":280,
            "m_ranking":111458,
            "m_kelei":"文史",
            "m_batch_id":4,
            "m_batch":"高职（专科）",
            "m_province_id":"0",
            "m_province":"",
            "m_specialty_id":"",
            "m_specialty_name":"",
            "m_favorites_by_university_codes":""
        };

        $.ajax({
            url: '/guide/school.do',
            type: 'GET',
            dataType: 'JSON',
            data:params1,
            success: function (res) {
                console.log(res);
                if (res.rtnCode == "0100006" || res.rtnCode == "1000004" || res.rtnCode == "0100005") {
                    $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);
                    return;
                }
                if (res.rtnCode == "0000000") {
                    var data = $.parseJSON(res.bizData);
                    console.log(data)
                    var m_batch_id = data.related.m_batch_id;
                    var listData = data.data.result;
                    if(listData.data.length==0){
                        $('.no-school').show();
                        $('.school-list').hide();
                    }

                    $.each(listData.data, function (i, v) {
                        if(listData.data[i].data.length==0){
                            $('.no-school').show();
                            $('.school-list').hide();
                        }
                        //console.log(listData.data[i].data.length)
                        if(listData.data[i].data.length > 0 && i < 4){
                            $('#no-school'+i).hide();
                            $('#school-list'+i).show();
                            $.each(listData.data[i].data, function (j, m) {
                                var schoolListHtml = ''
                                    +'<div>'
                                    +'<span class="fl"><a target="_blank" href="/consult/school_detile.jsp?id='+ m.m_university_code +'&batch='+ m_batch_id +'" id="'+ m.m_university_code +'">'+ m.m_university_name +'</a></span>'
                                    +'<span class="fr">选择</span>'
                                    +'</div>';
                                $('#school-list'+i).append(schoolListHtml);
                            })
                        }
                    });

                }
            }
        });
    })
});
