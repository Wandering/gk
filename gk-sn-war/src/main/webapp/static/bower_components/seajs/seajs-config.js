seajs.config({
    // 基础路径
    //base: "/static/",
    base: "http://cdn.gaokao360.net/static/",
    // 别名配置
    alias: {
        "$": "bower_components/jquery/dist/jquery-1.11.3.min",
        //"$": "bower_components/jquery/dist/jquery.min",
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
