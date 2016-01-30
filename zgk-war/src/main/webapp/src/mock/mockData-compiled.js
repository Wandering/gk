/**
 * Created by pdeng on 15/12/30.
 */
module.exports = function () {
    var Mock = require('mockjs');
    Mock.Random.extend({
        constellation: function () {
            var constellations = ['白羊座', '金牛座', '双子座', '巨蟹座', '狮子座', '处女座', '天秤座', '天蝎座', '射手座', '摩羯座', '水瓶座', '双鱼座'];
            return this.pick(constellations);
        }
    });
    return data = Mock.mock({
        'list|1-10': [{

            //'id|+1': 1,
            //'email': '@EMAIL',
            //'title|+1': '@title',
            //'boolean1|1': true,
            //'price|1-100.1-10': 1,
            //'url': '@url',
            //'ip': '@ip',
            //'constellation': '@CONSTELLATION', //自定义星座
            //'avator': '@image',
            //'name': '@cname',
            //'nick': '@region' + '@area',
            //'color': '@color',
            //'time': '@datetime',
            //'content': '@paragraph',
            //'subContent': '@sentence',
            //'md5': '@guid'
        }]
    });
};

//# sourceMappingURL=mockData-compiled.js.map