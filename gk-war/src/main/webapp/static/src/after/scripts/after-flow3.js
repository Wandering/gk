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
        //console.log(JSON.parse(params).m_province);



        var paramsJson = JSON.parse(params);
        var params1 = {
            "m_candidateNumber":"0",
            "m_aggregateScore":390,
            "m_ranking":72465,
            "m_kelei":"文史",
            "m_batch_id":4,
            "m_batch":"高职（专科）",
            "m_province_id":"0",
            "m_province":"",
            "m_specialty_id":"",
            "m_specialty_name":"",
            "m_favorites_by_university_codes":""
        };

        getSchool(params1,"","");
        function getSchool(paramsJson,m_province,m_specialty_name){
            paramsJson.m_province = m_province;
            paramsJson.m_specialty_name = m_specialty_name;
            console.log(paramsJson);
            $.ajax({
                url: '/guide/school.do',
                type: 'GET',
                dataType: 'JSON',
                data:paramsJson,
                success: function (res) {
                    console.log(res);
                    if (res.rtnCode == "0100006" || res.rtnCode == "1000004" || res.rtnCode == "0100005") {
                        $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);
                        return;
                    }
                    if (res.rtnCode == "0000000") {
                        var data = $.parseJSON(res.bizData);
                        //console.log(data)
                        var m_batch_id = data.related.m_batch_id;
                        var listData = data.data.result;
                        if(listData.data.length==0){
                            $('.no-school').show();
                            $('.school-list').hide();
                        }

                        console.log(data)

                        var m_keleiType = '';
                        console.log(data.related.m_kelei)
                        if(data.related.m_kelei=="文史"){
                            m_keleiType = 0;
                        }else if(data.related.m_kelei=="理工"){
                            m_keleiType = 1;
                        }

                        $.each(listData.data, function (i, v) {
                            if(listData.data[i].data.length==0){
                                $('.no-school').show();
                                $('.school-list').hide();
                            }
                            if(listData.data[i].data.length > 0 && i < 4){
                                $('#no-school'+i).hide();
                                $('#school-list'+i).show();
                                $.each(listData.data[i].data, function (j, m) {
                                    var schoolListHtml = ''
                                        +'<div>'
                                        +'<span class="fl"><a target="_blank" href="/consult/school_detile.jsp?id='+ m.m_university_code +'&batch='+ m_batch_id +'" id="'+ m.m_university_code +'">'+ m.m_university_name +'</a></span>'
                                        +'<span class="fr" id="'+ m.m_university_code +'" type = "'+ m_keleiType + '">选择</span>'
                                        +'</div>';
                                    $('#school-list'+i).append(schoolListHtml);
                                })
                            }
                        });

                    }
                }
            });
        }

        // 获取省份
        $.get('/region/getAllRegion.do', function (result) {
            //console.log(result)
            var data = result.bizData;
            if ('0000000' === result.rtnCode) {
                $.each(data,function(i,v){
                    var provinceName = v.name.replace(/[省,市,自治区]/g,"");
                    //console.log(provinceName)
                    $('#province-list').append('<option value="'+ provinceName +'">' + provinceName + '</option>')
                });
            }
        });

        // 省份 专业查询
         $('#specialty-search-btn').on('click',function(){
             var specialtyV = $('#specialty-input').val().trim();
             var provinceV = $("#province-list option:selected").val();
             getSchool(params1,provinceV,specialtyV);
         });






        $.ajax({
            url: '/university/universityDetail.do',
            type: 'GET',
            dataType: 'JSON',
            data:{
                code:2406,
                type:0
            },
            success: function (res) {
                console.log(res);


            }
        });





    })
});
