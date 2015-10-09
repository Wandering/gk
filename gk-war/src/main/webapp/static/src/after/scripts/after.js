define(function (require) {
    var $ = require('$');
    $(function(){
        //
        $('#volunteer-flow1-btn').on('click',function(){
            var candidateNumberV = $('#candidateNumber-input').val().trim();
            var aggregateScoreV = $('#aggregateScore-input').val().trim();
            var rankingV = $('#ranking-input').val().trim();
            var subjectTypeV = $('input[name="subjectType"]:checked').val();
            if (candidateNumberV == '') {
                $('.error-tips').text('请输入考号').fadeIn(1000).fadeOut(1000);
                return false;
            }
            if (aggregateScoreV == '') {
                $('.error-tips').text('请输入分数').fadeIn(1000).fadeOut(1000);
                return false;
            }
            if (rankingV == '') {
                $('.error-tips').text('请输入位次').fadeIn(1000).fadeOut(1000);
                return false;
            }


            $.ajax({
                url: '/guide/batch.do',
                type: 'GET',
                dataType: 'JSON',
                data: {
                    "m_candidateNumber": candidateNumberV,
                    "m_aggregateScore": aggregateScoreV,
                    "m_ranking": rankingV,
                    "m_kelei": subjectTypeV
                },
                success: function (res) {
                    console.log(res)
                    if (res.rtnCode == "0100006" || res.rtnCode == "1000004" || res.rtnCode == "0100005") {
                        $('.error-tips').text(res.msg).fadeIn(1000).fadeOut(1000);
                        return;
                    }
                    if (res.rtnCode == "0000000") {
                        var data = $.parseJSON(res.bizData);
                        $('#volunteer-flow1').hide();
                        $('#volunteer-flow2').show();
                        //console.log(data)
                        $('#scoresNum').text(aggregateScoreV);
                        $('#subType').text(subjectTypeV+"类");
                        $.each(data.data, function (i, v) {
                            $('#batch').text();
                            var params = {
                                "m_candidateNumber":candidateNumberV,
                                "m_aggregateScore":aggregateScoreV,
                                "m_ranking":rankingV,
                                "m_kelei":subjectTypeV,
                                "m_batch_id":v.m_batch_id,
                                "m_batch":v.m_batch,
                                "m_province_id":"",
                                "m_province":"陕西",
                                "m_specialty_id":"",
                                "m_specialty_name":"",
                                "m_favorites_by_university_codes":""
                            };
                            var jsons = JSON.stringify(params);
                            var schoolListHtml = ''
                                +'<div class="info2">'
                                +'<h3>普通'+ v.m_batch +'院校</h3>'
                                +'2015年控制线：<strong>文科分</strong>，<strong>理科分</strong>'
                                +'<form id="forwardForm" method="post" action="/forward.do">'
                                +'<input type="hidden" name="params" value="'+encodeURIComponent(jsons)+'" />'
                                +'<input type="hidden" name="url" value="/after/volunteer-flow3.jsp" />'
                                +'<input type="submit" class="btn" value="开始"></a>'
                                +'</form>'
                                +'</div>';
                            $('#school-list-info').append(schoolListHtml);
                        });
                    }

                }
            });
        });

        $('#prev-btn').on('click',function(){
            $('#volunteer-flow1').show();
            $('#volunteer-flow2').hide();
        });

    })


});
