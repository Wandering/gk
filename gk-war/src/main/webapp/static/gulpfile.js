// 载入外挂
var gulp = require('gulp'),
    sass = require('gulp-sass'),
    autoprefixer = require('gulp-autoprefixer'),
    minifycss = require('gulp-minify-css'),
    jshint = require('gulp-jshint'),
    uglify = require('gulp-uglify'),
    imagemin = require('gulp-imagemin'),
    rename = require('gulp-rename'),
    clean = require('gulp-clean'),
    concat = require('gulp-concat'),
    notify = require('gulp-notify'),
    cache = require('gulp-cache')
// 样式
gulp.task('styles', function () {
    gulp.src('src/*/styles/**/*.scss')
        .pipe(sass())
        .pipe(autoprefixer('last 2 version', 'safari 5', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'))
        .pipe(gulp.dest([
            'dist/after/styles',
            'dist/before/styles',
            'dist/consult/styles',
            'dist/guide/styles',
            'dist/index/styles',
            'dist/login/styles',
            'dist/market/styles',
            'dist/question/styles',
            'dist/user/styles'
        ]))
        .pipe(rename({suffix: '.min'}))
        .pipe(minifycss())
        .pipe(gulp.dest([
            'dist/after/styles',
            'dist/before/styles',
            'dist/consult/styles',
            'dist/guide/styles',
            'dist/index/styles',
            'dist/login/styles',
            'dist/market/styles',
            'dist/question/styles',
            'dist/user/styles'
        ]))
});
//html
gulp.task('html', function () {
    return gulp.src('src/*/html/**/*.html')
        //.pipe(jshint('.jshintrc'))
        .pipe(jshint.reporter('default'))
        //.pipe(concat('main.js'))
        .pipe(gulp.dest([
            'dist/after/html',
            'dist/before/html',
            'dist/consult/html',
            'dist/guide/html',
            'dist/index/html',
            'dist/login/html',
            'dist/market/html',
            'dist/question/html',
            'dist/user/html'
        ]))
        .pipe(rename({suffix: '.min'}))
        .pipe(uglify())
        .pipe(gulp.dest([
            'dist/after/html',
            'dist/before/html',
            'dist/consult/html',
            'dist/guide/html',
            'dist/index/html',
            'dist/login/html',
            'dist/market/html',
            'dist/question/html',
            'dist/user/html'
        ]))
        .pipe(notify({message: 'html task complete'}));
});


// 脚本
gulp.task('scripts', function () {
    return gulp.src('src/*/scripts/**/*.js')
        //.pipe(jshint('.jshintrc'))
        .pipe(jshint.reporter('default'))
        //.pipe(concat('main.js'))
        .pipe(gulp.dest([
            'dist/after/scripts',
            'dist/before/scripts',
            'dist/consult/scripts',
            'dist/guide/scripts',
            'dist/index/scripts',
            'dist/login/scripts',
            'dist/market/scripts',
            'dist/question/scripts',
            'dist/user/scripts'
        ]))
        .pipe(rename({suffix: '.min'}))
        .pipe(uglify())
        .pipe(gulp.dest([
            'dist/after/scripts',
            'dist/before/scripts',
            'dist/consult/scripts',
            'dist/guide/scripts',
            'dist/index/scripts',
            'dist/login/scripts',
            'dist/market/scripts',
            'dist/question/scripts',
            'dist/user/scripts'
        ]))
        .pipe(notify({message: 'Scripts task complete'}));
});

// 图片
gulp.task('images', function () {
    return gulp.src('src/*/images/**/*')
        .pipe(cache(imagemin({optimizationLevel: 3, progressive: true, interlaced: true})))
        .pipe(gulp.dest([
            'dist/after/images',
            'dist/before/images',
            'dist/consult/images',
            'dist/guide/images',
            'dist/index/images',
            'dist/login/images',
            'dist/market/images',
            'dist/question/images',
            'dist/user/images'
        ]))
        .pipe(notify({message: 'Images task complete'}));
});

// 清理
//gulp.task('clean', function () {
//    return gulp.src([
//        'dist/gk-consult-war/styles',
//        'dist/gk-consult-war/scripts',
//        'dist/gk-consult-war/html',
//        'dist/gk-consult-war/images'], {read: false})
//        .pipe(clean());
//});

// 预设任务['clean'],
gulp.task('default',  function () {
    gulp.start('styles', 'scripts', 'images','html');
});

// 检测变化
gulp.task('watch', function () {

    // 看守所有.scss档
    gulp.watch('src/*/styles/**/*.scss', ['styles']);

    gulp.watch('src/*/html/**/*.html', ['html']);

    // 看守所有.js档
    gulp.watch('src/*/scripts/**/*.js', ['scripts']);

    // 看守所有图片档
    gulp.watch('src/*/images/**/*', ['images']);


});
