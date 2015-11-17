define(function (require) {
    var $ = require('$');
    require('backToTop');
    $(function () {
        //
        var paramsJson = JSON.parse(params);

        console.log(paramsJson)
        $('.volunteer-flow3-table').on('click', '.open-flow3', function () {
            $('.tansLayer,.volunteer-flow3-layer').show();
            $('#volunteer-flow3-layer').attr('dataType', $(this).attr('dataType'))
            getSchool(paramsJson, "", "");
        });

        $('#volunteer-flow3-layer').on('click', '.close-btn', function () {
            $('#volunteer-flow3-layer,.tansLayer').hide();
        });
        //console.log(JSON.parse(params).m_province);



        //var params1 = {
        //    "m_candidateNumber": "0",
        //    "m_aggregateScore": 390,
        //    "m_ranking": 72465,
        //    "m_kelei": "文史",
        //    "m_batch_id": 4,
        //    "m_batch": "高职（专科）",
        //    "m_province_id": "0",
        //    "m_province": "",
        //    "m_specialty_id": "",
        //    "m_specialty_name": "",
        //    "m_favorites_by_university_codes": ""
        //};



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
                        var m_keleiType = '';
                        if (data.related.m_kelei == "文史") {
                            m_keleiType = 0;
                        } else if (data.related.m_kelei == "理工") {
                            m_keleiType = 1;
                        }
                        var datatypeId = $('#volunteer-flow3-layer').attr('datatype');

                        $('.school-list').html('');
                        $.each(listData.data, function (i, v) {
                            //console.log(listData.data[i].data.length)

                            if (listData.data[i].data.length > 0 && i < 4) {
                                $('#no-school' + i).hide();
                                $('#school-list' + i).show();
                                    $.each(listData.data[i].data, function (j, m) {
                                        var schoolListHtml = ''
                                            + '<div>'
                                            + '<span class="fl"><a target="_blank" href="/consult/school_detail.jsp?code=' + m.m_university_code + '&batch=' + m_batch_id + '" id="' + m.m_university_code + '">' + m.m_university_name + '</a></span>'
                                            + '<span class="fr selSchool" datatypeId="' + datatypeId + '" m_university_name="' + m.m_university_name + '" id="' + m.m_university_code + '" type = "' + m_keleiType + '" m_batch_id="'+ m_batch_id +'" m_batch="' + m_batch + '">选择</span>'
                                            + '</div>';
                                        $('#school-list' + i).append(schoolListHtml);
                                    })
                            }else{
                                $('#no-school' + i).show();
                                $('#school-list' + i).hide();
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
            getSchool(paramsJson, provinceV, specialtyV);
        });

        // 选择学校
        $('#volunteer-flow3-layer').on('click', '.selSchool', function () {
            var code = $(this).attr('id');
            var type = $(this).attr('type');
            var m_batch = $(this).attr('m_batch');
            var m_batch_id = $(this).attr('m_batch_id');
            var datatypeid = $(this).attr('datatypeid');
            var m_university_name = $(this).attr('m_university_name');
            //console.log(code + "+" + type + "+" + m_batch + "+" + m_batch_id + "+" + datatypeid + "+" + m_university_name)
            var years = 2014;
            //console.log($(this).parents('.school-list').attr('dataType'))
            var star = '';
            var starType = $(this).parents('.school-list').attr('dataType');
            if (starType == "A") {
                star = '★';
            } else if (starType == "B") {
                star = '★★';
            } else if (starType == "C") {
                star = '★★★';
            } else {
                star = '★★★★';
            }
            //console.log(code + "=" + type + "==" + m_batch);
            //console.log(star);
            $.ajax({
                url: '/university/universityDetail.do',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    code: code,
                    type: type,
                    year: years,
                    batch: m_batch
                },
                success: function (res) {
                    //console.log(res);
                    var data = res.bizData;
                    if ('0000000' === res.rtnCode) {
                        var dicName = '';
                        if (data.dictName) {
                            dicName = data.dictName;
                        }
                        var infoHtml = ''
                            + '<p>'
                            + '院校特征：' + dicName + '<br/>'
                            + '院校隶属：' + data.subjection + '<br/>'
                            + '院校类型：' + data.type + '<br/>'
                            + '2014年最低投档分：' + data.lowestScore + '<br/>'
                            + '2014年最低位次：' + data.lowestRanking + ' <br/>'
                            + '2014年录取平均分：' + data.averageScore + ' <br/>'
                            + '2014年平均分位次：' + data.averageScoresRanking + ' <br/>'
                            + '历年招生情况：' + data.enrollIntro + ' <br/>'
                            + '录取指数：' + star
                            + '</p>';
                        $('.open-flow3[type="text"][dataType="' + datatypeid + '"]').val(m_university_name).attr({'code':code,'m_batch':m_batch,'m_batch_id':m_batch_id});
                        $('#result-info' + datatypeid).html(infoHtml);
                        $('#tips' + datatypeid).hide();
                        $('#volunteer-flow3-layer,.tansLayer').hide();
                        $('#specialty' + datatypeid).show()
                            .attr({'m_batch': m_batch, 'code': code, 'year': years})
                            .find('input').val('');
                    }
                }
            });
        });

        // 获取专业
        $('.specialty').on('click', '.specialty-click', function () {
            var boxId = $(this).parents('.specialty').attr('id');
            var index = $(this).parents('li').index();
            var parents = $(this).parents('.specialty');
            var m_batch = parents.attr('m_batch');
            var code = parents.attr('code');
            var year = parents.attr('year');
            //console.log(m_batch + "=" + code + "=" + year)
            $('#specialty-content').html('');
            $('#specialty-layer,.tansLayer').show();
            $.ajax({
                url: '/majored/majorList.do',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    code: code,
                    year: year,
                    batch: m_batch
                },
                success: function (res) {
                    //console.log(res);
                    var data = res.bizData;
                    if ('0000000' === res.rtnCode) {
                        $.each(data, function (i, v) {
                            var specialtyTotal = data.length;
                            var name = v.name;
                            var subject = v.subject;
                            var schoolLength = v.schoolLength;
                            var feeStandard = v.feeStandard;
                            var planNum = v.planNum;
                            var id = v.id;
                            var tbody = ''
                                + '<tr>'
                                + '<td class="tl"><label id="' + boxId + '" name="'+ name +'" index="' + index + '" specialtyTotal="'+ specialtyTotal +'"><input type="radio" name="schoolType" id=""/> ' + name + '</label></td>'
                                + '<td>' + m_batch + '</td>'
                                + '<td>' + subject + '</td>'
                                + '<td>' + planNum + '</td>'
                                + '<td>' + schoolLength + '</td>'
                                + '<td>' + feeStandard + '</td>'
                                + '</tr>';
                            $('#specialty-content').append(tbody)
                        });
                    }
                }
            });
        });








        $('#specialty-layer').on('click', 'label', function () {
            var Eid = $(this).attr('id');
            var index = $(this).attr('index');
            var name = $(this).attr('name');
            //console.log(Eid + "=" + name);
            var specialtyTotalN = $(this).attr('specialtyTotal')
            $('#'+Eid +' li:eq('+ index +')').find('input').val(name).attr({'specialtyTotalN':specialtyTotalN}).addClass('write');
            $('#specialty-layer,.tansLayer').hide();

        });


        $('#specialty-layer').on('click', '.close-btn', function () {
            $('#specialty-layer,.tansLayer').hide();
        });


        // 下一步
        $('#volunteer-flow3-btn').on('click',function(){
            $('.school-list-col,.enrollmentSchool,#exchange,#integrity,#eva,#enrollment').html('');
            if($('#result-info1').text()==""){
                $('.error-tips2').text("请在A志愿中选择学校").fadeIn(1000).fadeOut(1000);
                return false;
            }
            if($('#result-info2').text()==""){
                $('.error-tips2').text("请在B志愿中选择学校").fadeIn(1000).fadeOut(1000);
                return false;
            }
            if($('#result-info3').text()==""){
                $('.error-tips2').text("请在C志愿中选择学校").fadeIn(1000).fadeOut(1000);
                return false;
            }
            if($('#result-info4').text()==""){
                $('.error-tips2').text("请在D志愿中选择学校").fadeIn(1000).fadeOut(1000);
                return false;
            }
            $('#main1').hide();
            $('#main2').show();
            // 学校信息
            $('#print-result-info1').html($('#result-info1').html()).prepend('<p>'+$('input[type="text"][dataType="1"]').val()+'</p>');
            $('#print-result-info2').html($('#result-info2').html()).prepend('<p>'+$('input[type="text"][dataType="2"]').val()+'</p>');
            $('#print-result-info3').html($('#result-info3').html()).prepend('<p>'+$('input[type="text"][dataType="3"]').val()+'</p>');
            $('#print-result-info4').html($('#result-info4').html()).prepend('<p>'+$('input[type="text"][dataType="4"]').val()+'</p>');
            // 专业信息
            function getSpecialtyList(n){
                $('#specialty-list-info'+n).html('');
                $.each($('#specialty'+n).find('input'),function(i,v){
                    var specialtyListInfo ='<p>'+ (i+1) + '.' + $(v).val() +'</p>';
                    //console.log((i+1) + "." + $(v).val());
                    $('#specialty-list-info'+n).append(specialtyListInfo)
                });
                $.each($('#specialty'+n),function(i,v){
                    //console.log($(this).find('input').attr('specialtytotaln'))
                    //console.log($(this).find('input.write').length)
                    var specialtytotaln = $(this).find('input').attr('specialtytotaln');
                    var specialtyLength = $(this).find('input.write').length;
                    //console.log(specialtytotaln + "--" + specialtyLength)

                    var schoolListColHtml = '';
                    if(specialtyLength < 6){
                        schoolListColHtml += '<div class="col-list">志愿专业填写不完整</div>';
                    }else{
                        schoolListColHtml += '<div class="col-list">志愿专业填写完整</div>';

                    }
                    $('#integrity').append(schoolListColHtml);

                })
            }
            getSpecialtyList(1);
            getSpecialtyList(2);
            getSpecialtyList(3);
            getSpecialtyList(4);
            var isFun1 = $('input[name="isFun1"]:checked').val();
            $('#isFunRadio1').html(isFun1);
            var isFun2 = $('input[name="isFun2"]:checked').val();
            $('#isFunRadio2').html(isFun2);
            var isFun3 = $('input[name="isFun3"]:checked').val();
            $('#isFunRadio3').html(isFun3);
            var isFun4 = $('input[name="isFun4"]:checked').val();
            $('#isFunRadio4').html(isFun4);


            //合理性分析
            var m_university_codeA = $('input[type="text"][dataType="1"]').attr('code');
            var m_university_nameA = $('input[type="text"][dataType="1"]').val();
            var m_university_codeB = $('input[type="text"][dataType="2"]').attr('code');
            var m_university_nameB = $('input[type="text"][dataType="2"]').val();
            var m_university_codeC = $('input[type="text"][dataType="3"]').attr('code');
            var m_university_nameC = $('input[type="text"][dataType="3"]').val();
            var m_university_codeD = $('input[type="text"][dataType="4"]').attr('code');
            var m_university_nameD = $('input[type="text"][dataType="4"]').val();
            var m_batch = $('input[type="text"][dataType="1"]').attr('m_batch');
            var m_batch_id = $('input[type="text"][dataType="1"]').attr('m_batch_id');
            var data = [
                    {
                        "sequence": 1,
                        "m_university_code": m_university_codeA,
                        "m_university_name": m_university_nameA

                    },
                    {
                        "sequence": 2,
                        "m_university_code": m_university_codeB,
                        "m_university_name": m_university_nameB

                    },
                    {
                        "sequence": 3,
                        "m_university_code": m_university_codeC,
                        "m_university_name": m_university_nameC

                    },
                    {
                        "sequence": 4,
                        "m_university_code": m_university_codeD,
                        "m_university_name": m_university_nameD

                    }
                ];
            var related = {
                    "m_batch_id": m_batch_id,
                    "m_batch": m_batch
                };


            var typeT = '';
            var type = paramsJson.m_kelei;
            if(type=="文史"){
                typeT=0;
            }else{
                typeT=1;
            }
            var m_batch = paramsJson.m_batch;
            var params = {
                "data":data,
                "related":related
            };
            $.ajax({
                url: '/guide/report.do',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    params: JSON.stringify(params),
                    type:typeT,
                    year:2014,
                    batch:m_batch
                },
                success: function (res) {
                    //console.log($.parseJSON(res.bizData));

                    if (res.rtnCode == "0000000") {
                        var dataJson = $.parseJSON(res.bizData).report;
                        var description = dataJson.result;
                        var descriptionResultA = description[1].description;
                        var descriptionResultB = description[2].description;
                        var descriptionResultC = description[3].description;

                        $('#eva').text(descriptionResultA.substr(0,descriptionResultA.length-1));
                        $('#eva-txt').html(descriptionResultB + descriptionResultC)
                        $.each(dataJson.data,function(i,v){
                            var schoolListColHtml = '<div class="col-list">'+ v.m_university_name +'</div>';
                            $('.school-list-col').append(schoolListColHtml);
                        });
                        var dataEnroll =$.parseJSON(res.bizData).enroll;
                        //console.log($.parseJSON(res.bizData));
                        $.each(dataEnroll,function(i,v){

                            var enrollIntro = v.enrollIntro;
                            var name = v.name;
                            var enrollmentSchoolHtml = '<div class="col-list">'+ name +'</div>';
                            $('#enrollmentSchool').append(enrollmentSchoolHtml);
                            var schoolListColHtml = '<div class="col-list">'+ enrollIntro +'</div>';
                            $('#enrollment').append(schoolListColHtml);
                        });
                    }
                }
            });


            // 是否调剂专业
            $.each($('.isFunRadio'),function(i,v){
                var isF = '';
                var isFt = $(v).text();
                if(isFt=="是"){
                    isF += '<div class="col-list obey">志愿专业服从调剂</div>';
                }else{
                    isF += '<div class="col-list no-obey">志愿专业不服从调剂</div>';
                }

                var schoolListColHtml = isF;
                $('#exchange').append(schoolListColHtml);
            });
            console.log($('#exchange').find('.obey').length)
            var exchangeLen = $('#exchange').find('.col-list').length;
            if($('#exchange').find('.obey').length==exchangeLen){
                 $('#noObey').hide();
                 $('#allObey').show();
            }else{
                $('#noObey').show();
                $('#allObey').hide();
            }
        });

        // 上一步
        $('#prev-btn').on('click',function(){
            $('#main1').show();
            $('#main2').hide();
        });


        // 个人信息
        $.get('/info/getUserInfo.do', function (res) {
            if (res.rtnCode == '0000000') {
                var personListData = res.bizData;
                //console.log(res);
                var name = personListData.name;
                var schoolName = personListData.schoolName;
                var sex = personListData.sex;

                $('#m_candidateNumber').text(paramsJson.m_candidateNumber);
                $('#m_aggregateScore').text(paramsJson.m_aggregateScore);
                $('#m_ranking').text(paramsJson.m_ranking);
                $('#m_kelei').text(paramsJson.m_kelei);

                var m_aggregateScore = paramsJson.m_aggregateScore;

                var sexT='';
                if(sex=="1"){
                    sexT="男";
                }else{
                    sexT="女";
                }
                var m_kelei = paramsJson.m_kelei;
                if(m_kelei=="理工"){
                    if(m_aggregateScore >= 510){
                        $('#controlLine').text("510");
                        $('#controlLine-txt').text('一批本科省控线');
                    }else if( m_aggregateScore >= 467 && m_aggregateScore < 510){
                        $('#controlLine').text("467");
                        $('#controlLine-txt').text('二批本科省控线');
                    }else if( m_aggregateScore >= 382 && m_aggregateScore < 467){
                        $('#controlLine').text("382");
                        $('#controlLine-txt').text('三批本科省控线');
                    }else{
                        $('#controlLine').text("220");
                        $('#controlLine-txt').text('高专高职省控线');
                    }
                }else if(m_kelei=="文史"){
                    if(m_aggregateScore >= 480){
                        $('#controlLine').text("480");
                        $('#controlLine-txt').text('一批本科省控线');
                    }else if( m_aggregateScore >= 440 && m_aggregateScore < 480){
                        $('#controlLine').text("440");
                        $('#controlLine-txt').text('二批本科省控线');
                    }else if( m_aggregateScore >= 350 && m_aggregateScore < 440){
                        $('#controlLine').text("350");
                        $('#controlLine-txt').text('三批本科省控线');
                    }else{
                        $('#controlLine').text("200");
                        $('#controlLine-txt').text('高专高职省控线');
                    }
                }
                $('#studentName').text(name);
                $('#schoolName').text(schoolName);
                $('#sexT').text(sexT);
            }
        });
        // 打印
        $('#print-btn').on('click',function(){
            window.print();
        });
    })
});
