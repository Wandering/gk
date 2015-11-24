seajs.config({
    // 基础路径
    base: "http://cdn.gaokao360.net/static/",
    // 别名配置
    alias: {
        "$": "bower_components/jquery/dist/jquery.min.js",
        "laydate": "bower_components/laydate/laydate.js",
        "uploadify": "bower_components/uploadify/jquery.uploadify.min.js",
        "getTime": "bower_components/utils/getTime",
        "backToTop": "bower_components/utils/backToTop",
        "pageErrorTip": "bower_components/utils/pageErrorTip",
        "belatedPNG": "bower_components/banner/belatedPNG"
    },
    preload: ['$'],
    //map,批量更新时间戳
    map: [[/^(.*\.(?:css|js))(.*)$/i, '$1?v=' + new Date().getTime() ]],
    // 文件编码
    charset: 'utf-8'
});
