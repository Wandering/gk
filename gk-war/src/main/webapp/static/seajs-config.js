seajs.config({
    // 基础路径
    base: "/static/",
    // 别名配置
    alias: {
        "$": "bower_components/jquery/dist/jquery.js",
        "swiper-jquery": "bower_components/swiper/dist/js/swiper.jquery.min.js",
        "swiper": "bower_components/utils/swiper.js"
    },
    preload: ['$'],
    //map,批量更新时间戳
    map: [[/^(.*\.(?:css|js))(.*)$/i, '$1?v=' + new Date().getTime() ]],
    // 文件编码
    charset: 'utf-8'
});