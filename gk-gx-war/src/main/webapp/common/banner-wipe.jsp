<%@ page language="java" pageEncoding="UTF-8" %>
<style>
  /* slide */
  .slide-main{height:360px;position:relative;
    z-index: 88;
    clear: both;}
  .slide-main .prev,.slide-main .next{display:block;width:44px;height:44px;position:absolute;z-index:222;top:150px;overflow:hidden;cursor:pointer;opacity:0.6;-moz-opacity:0.6;filter:alpha(opacity=60);_border:1px solid none;}
  .slide-main .prev{left:70px;}
  .slide-main .next{right:70px;}
  .slide-main .prev:hover,.slide-main .next:hover,.slide-main .nav-main a:hover,.slide-main .nav-main a.cur{opacity:1;-moz-opacity:1;filter:alpha(opacity=100);}
  .slide-main .item{display:block;width:100%;height:5px;position:absolute;z-index:124;top:330px;left:0;text-align:center;}
  .slide-main .item a{display:inline-block;width:24px;height:5px;margin-right:11px;background:#333;opacity:0.6;-moz-opacity:0.6;filter:alpha(opacity=60);overflow:hidden;}
  .slide-main .item a.cur{background:#fff;}
  .slide-main .slide-box,.slide{display:block;width:100%;height:360px;overflow:hidden;}
  .slide-main .slide-box{position:relative;}
  .slide-main .slide{display:none;height:360px;background:#ff6900;position:relative;position:absolute;z-index:8;}
  .slide-main .slide img{
    width: 1000px;
    height: 360px;
    margin: 0 auto;
    display: block;
  }
  .slide-main .slide a.banner-max{
    height: 360px;
    display: inline-block;
    width: 100%;
    background: url(http://cdn.gaokao360.net/static/global/common/images/banner1.png) center center;
  }
</style>
<!-- 代码 开始 -->
<div class="slide-main" id="touchMain">
  <a class="prev" href="javascript:;" stat="prev1001"><img src="http://cdn.gaokao360.net/static/global/common/images/l-btn.png" /></a>
  <div class="slide-box" id="slideContent" style="background-color: #f1f1f1">
    <div class="slide" id="bgstylec" style="background: #ce4230;">
      <a href="http://ke.qq.com/course/92607" target="_blank" class="banner-max"></a>
    </div>
    <div class="slide" id="bgstylea" style="background: #0d63b2;">
      <img src="http://cdn.gaokao360.net/static/global/common/images/banner-1.png" alt=""/>
    </div>
    <div class="slide" id="bgstyleb" style="background: #25b76f;">
      <img src="http://cdn.gaokao360.net/static/global/common/images/banner-2.png" alt=""/>
    </div>
  </div>
  <a class="next" href="javascript:;" stat="next1002"><img src="http://cdn.gaokao360.net/static/global/common/images/r-btn.png" /></a>
  <div class="item">
    <a class="cur" stat="item1001" href="javascript:;"></a><a href="javascript:;" stat="item1002"></a><a href="javascript:;" stat="item1003"></a>
  </div>
</div>
<!-- 代码 结束 -->
<script>
  seajs.use("http://cdn.gaokao360.net/static/global/common/scripts/banner.min");
</script>
