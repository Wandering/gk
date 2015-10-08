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

                    if (res.rtnCode == "0000000") {

                    }
                }
            });






        });

    })
});
