define(function (require) {
    var $ = require('$');
    $(function () {
        //
        $('.volunteer-flow3-table').on('click', '.open-flow3', function () {
            $('.tansLayer,.volunteer-flow3-layer').show();
            $('#volunteer-flow3-layer').attr('dataType', $(this).attr('dataType'))
            getSchool(params1, "", "");
        });


        $('#volunteer-flow3-layer').on('click', '.close-btn', function () {
            $('#volunteer-flow3-layer,.tansLayer').hide();
        });
        //console.log(JSON.parse(params).m_province);


        var paramsJson = JSON.parse(params);
        var params1 = {
            "m_candidateNumber": "0",
            "m_aggregateScore": 390,
            "m_ranking": 72465,
            "m_kelei": "文史",
            "m_batch_id": 4,
            "m_batch": "高职（专科）",
            "m_province_id": "0",
            "m_province": "",
            "m_specialty_id": "",
            "m_specialty_name": "",
            "m_favorites_by_university_codes": ""
        };


        function getSchool(paramsJson, m_province, m_specialty_name) {
            paramsJson.m_province = m_province;
            paramsJson.m_specialty_name = m_specialty_name;
            //console.log(paramsJson);
            $.ajax({
                url: '/guide/school.do',
                type: 'GET',
                dataType: 'JSON',
                data: paramsJson,
                success: function (res) {
                    //console.log(res);
                    if (res.rtnCode == "0100006" || res.rtnCode == "1000004" || res.rtnCode == "0100005") {
                        $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);
                        return;
                    }
                    if (res.rtnCode == "0000000") {
                        var data = $.parseJSON(res.bizData);
                        //console.log(data)
                        var m_batch_id = data.related.m_batch_id;
                        var m_batch = data.related.m_batch;
                        var listData = data.data.result;
                        if (listData.data.length == 0) {
                            $('.no-school').show();
                            $('.school-list').hide();
                        }

                        console.log(data);

                        var m_keleiType = '';
                        //console.log(data.related.m_kelei)
                        if (data.related.m_kelei == "文史") {
                            m_keleiType = 0;
                        } else if (data.related.m_kelei == "理工") {
                            m_keleiType = 1;
                        }

                        var datatypeId = $('#volunteer-flow3-layer').attr('datatype');
                        console.log(datatypeId)

                        $.each(listData.data, function (i, v) {
                            if (listData.data[i].data.length == 0) {
                                $('.no-school').show();
                                $('.school-list').hide();
                            }
                            if (listData.data[i].data.length > 0 && i < 4) {
                                $('#no-school' + i).hide();
                                $('#school-list' + i).show();
                                $.each(listData.data[i].data, function (j, m) {
                                    var schoolListHtml = ''
                                        + '<div>'
                                        + '<span class="fl"><a target="_blank" href="/consult/school_detile.jsp?id=' + m.m_university_code + '&batch=' + m_batch_id + '" id="' + m.m_university_code + '">' + m.m_university_name + '</a></span>'
                                        + '<span class="fr selSchool" datatypeId="'+ datatypeId +'" id="' + m.m_university_code + '" type = "' + m_keleiType + '" m_batch="'+ m_batch +'">选择</span>'
                                        + '</div>';
                                    $('#school-list' + i).append(schoolListHtml);
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
                $.each(data, function (i, v) {
                    var provinceName = v.name.replace(/[省,市,自治区]/g, "");
                    //console.log(provinceName)
                    $('#province-list').append('<option value="' + provinceName + '">' + provinceName + '</option>')
                });
            }
        });

        // 省份 专业查询
        $('#specialty-search-btn').on('click', function () {
            var specialtyV = $('#specialty-input').val().trim();
            var provinceV = $("#province-list option:selected").val();
            getSchool(params1, provinceV, specialtyV);
        });


        $('#volunteer-flow3-layer').on('click','.selSchool',function(){
            var code = $(this).attr('id');
            var type = $(this).attr('type');
            var m_batch = $(this).attr('m_batch');
            console.log(code+"=" + type+"=="+m_batch)
            $.ajax({
                url: '/university/universityDetail.do',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    code: code,
                    type: type,
                    year: 2014,
                    batch: m_batch
                },
                success: function (res) {
                    console.log(res);
                    var data = res.bizData;
                    if ('0000000' === result.rtnCode) {
                        var dicName = '';
                        if(data.dictName){
                            dicName = data.dictName;
                        }
                        var infoHtml = ''
                            + '<p>'
                            + '院校代码：'+ data.code +'  <br/>'
                            + '院校特征：'+ dicName +'<br/>'
                            + '院校隶属：'+ data.subjection +'<br/>'
                            + '院校类型：'+ data.type +'<br/>'
                            + '2014年最低投档分：'+ data.lowestScore +'<br/>'
                            + '2014年最低位次：'+ data.lowestRanking +' <br/>'
                            + '2014年录取平均分：'+ data.averageScore +' <br/>'
                            + '2014年平均分位次：'+ data.averageScoresRanking +' <br/>'
                            + '历年招生情况：'+ data.enrollIntro +' <br/>'
                            + '录取指数：★★'
                            + '</p>'
                    }



                }
            });
        });



    })
});
