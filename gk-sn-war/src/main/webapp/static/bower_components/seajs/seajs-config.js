seajs.config({
    // 基础路径
    base: "/static/",
    // 别名配置
    alias: {
        "$": "bower_components/jquery/dist/jquery.js",
        "laydate": "bower_components/laydate/laydate.js",
        "uploadify": "bower_components/uploadify/jquery.uploadify.min.js",
        "getTimes": "bower_components/utils/getTimes",
        "backToTop": "bower_components/utils/backToTop",
        "pageErrorTip": "bower_components/utils/pageErrorTip",
        "belatedPNG": "bower_components/banner/belatedPNG",
        "cookie": "bower_components/cookie/jquery.cookie.js"
    },
    preload: ['$'],
    //map,批量更新时间戳
    map: [[/^(.*\.(?:css|js))(.*)$/i, '$1?v=' + new Date().getTime() ]],
    // 文件编码
    charset: 'utf-8'
});
