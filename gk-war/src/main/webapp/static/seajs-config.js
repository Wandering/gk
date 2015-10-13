seajs.config({
    // 基础路径
    base: "/static/",
    // 别名配置
    alias: {
        "$": "bower_components/jquery/dist/jquery.js",
        "swiper-jquery": "bower_components/swiper/dist/js/swiper.jquery.min.js",
        "swiper": "bower_components/utils/swiper.js",
        "laydate": "bower_components/laydate/laydate.js",
        "uploadify": "bower_components/uploadify/jquery.uploadify.min.js",
        "header-user":'src/common/scripts/header-user-base.js',
        "getTime": "bower_components/utils/getTime",
        "backToTop": "bower_components/utils/backToTop",
        "bsModal": "bower_components/utils/bootstrap-modal",
        "bsModalManager": "bower_components/utils/bootstrap-modalmanager",
        "modalLayer": "bower_components/utils/modalLayer",
        "pageErrorTip": "bower_components/utils/pageErrorTip"
    },
    preload: ['$'],
    //map,批量更新时间戳
    map: [[/^(.*\.(?:css|js))(.*)$/i, '$1?v=' + new Date().getTime() ]],
    // 文件编码
    charset: 'utf-8'
});
