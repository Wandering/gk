define(function (require) {
    var $ = require('$');



    $(function(){



        var data = [
                {
                    "sequence": 1,
                    "m_university_code": "1305",
                    "m_university_name": "天津滨海职业学院"

                },
                {
                    "sequence": 2,
                    "m_university_code": "1122",
                    "m_university_name": "北京社会管理职业学院"

                },
                {
                    "sequence": 3,
                    "m_university_code": "1328",
                    "m_university_name": "天津广播影视职业学院"

                },
                {
                    "sequence": 6,
                    "m_university_code": "4533",
                    "m_university_name": "宜春职业技术学院"

                },
                {
                    "sequence": 5,
                    "m_university_code": "1517",
                    "m_university_name": "河北能源职业技术学院"

                },
                {
                    "sequence": 4,
                    "m_university_code": "1312",
                    "m_university_name": "天津公安警官职业学院"


                }
            ]
            ;
        var related = {
                "m_batch_id": 4,
                "m_batch": "高职（专科）" }
            ;
        $.ajax({
            url: '/guide/report.do',
            type: 'GET',
            dataType: 'JSON',
            data: {
                data: data,
                related: related
            },
            success: function (res) {
                console.log(res);

            }
        });



    });

});
