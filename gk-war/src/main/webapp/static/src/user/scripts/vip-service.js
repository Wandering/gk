define(function (require) {
    var $ = require('$');


    $.getJSON("/product/findProductPage.do?type=0&startSize=1&endSize=10",function(result){
        console.log(result)

    })



});
