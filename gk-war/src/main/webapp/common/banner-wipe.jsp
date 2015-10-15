<%@ page language="java" pageEncoding="UTF-8" %>
<%--<div class="wipe">--%>
  <%--<div class="swiper-container">--%>
    <%--<div class="swiper-wrapper">--%>
      <%--<div class="swiper-slide slide-1"><img src="/static/dist/common/images/banner-0.png" alt=""/></div>--%>
      <%--<div class="swiper-slide slide-2"><img src="/static/dist/common/images/banner-1.png" alt=""/></div>--%>
      <%--<div class="swiper-slide slide-3"><img src="/static/dist/common/images/banner-2.png" alt=""/></div>--%>
      <%--&lt;%&ndash;<div class="swiper-slide slide-3"><img src="/static/dist/common/images/banner.png" alt="" class="banner-toggle"/></div>&ndash;%&gt;--%>
    <%--</div>--%>
    <%--<div class="swiper-pagination"></div>--%>
    <%--<div class="swiper-button-prev"></div>--%>
    <%--<div class="swiper-button-next"></div>--%>
  <%--</div>--%>
<%--</div>--%>

<style>
  /* slide */
  .slide-main{height:360px;position:relative;}
  .prev,.next{display:block;width:44px;height:44px;position:absolute;z-index:222;top:150px;overflow:hidden;cursor:pointer;opacity:0.6;-moz-opacity:0.6;filter:alpha(opacity=60);_border:1px solid none;}
  .prev{left:70px;}
  .next{right:70px;}
  .prev:hover,.next:hover,.nav-main a:hover,.nav-main a.cur{opacity:1;-moz-opacity:1;filter:alpha(opacity=100);}
  .item{display:block;width:100%;height:5px;position:absolute;z-index:124;top:330px;left:0;text-align:center;}
  .item a{display:inline-block;width:24px;height:5px;margin-right:11px;background:#333;opacity:0.6;-moz-opacity:0.6;filter:alpha(opacity=60);overflow:hidden;}
  .item a.cur{background:#fff;}
  .slide-box,.slide{display:block;width:100%;height:360px;overflow:hidden;}
  .slide-box{position:relative;}
  .slide{display:none;height:360px;background:#ff6900;position:relative;position:absolute;z-index:8;}
  /*.slide a{display:block;width:100%;height:360px;cursor:pointer;}*/
  /*.obj-a,.obj-b,.obj-c,.obj-d,.obj-e,.obj-f{position:absolute;z-index:9;left:50%;}*/
  /*.obj-a,.obj-b{width:952px;margin-left:-476px;text-align:center;}*/
  /*.obj-a{display:block;height:352px;top:100px;}*/
  /*.obj-b{top:406px;height:100px;}*/
  /*.obj-c{display:block;height:582px;top:0;margin-left:-540px;}*/
  /*.obj-d{top:228px;height:164px;}*/
  /*.obj-d p{display:block;padding-top:20px;font-size:16px;color:#fff;clear:both;}*/
  /*.obj-e{width:366px;height:170px;margin-left:-460px;top:223px;z-index:12;}*/
  /*.obj-f{width:692px;height:394px;top:158px;margin-left:-180px;}*/
  /*.banAnimate .obj-a{display:block;animation-name:baoAni;-webkit-animation:baoAni 0.4s linear 0s normal none;-moz-animation:baoAni 0.4s linear 0s normal none;animation:baoAni 0.4s linear 0s normal none;}*/
  /*@-webkit-keyframes baoAni{from{opacity:0;left:60%;} to{opacity:1;left:50%;} }*/
  /*@-moz-keyframes baoAni{from{opacity:0;left:60%;} to{opacity:1;left:50%;} }*/
  /*@keyframes baoAni{from{opacity:0;left:60%;} to{opacity:1;left:50%;} }*/
  /*.banAnimate .obj-b{display:block;animation-name:saAni;-webkit-animation:saAni 0.4s linear 0s normal none;-moz-animation:saAni 0.4s linear 0s normal none;animation:saAni 0.4s linear 0s normal none;}*/
  /*@-webkit-keyframes saAni{from{opacity:0;-webkit-transform:scale(0,0);} to{opacity:1;-webkit-transform:scale(1,1);} }*/
  /*@-moz-keyframes saAni{from{opacity:0;-moz-transform:scale(0,0);} to{opacity:1;-moz-transform:scale(1,1);} }*/
  /*@keyframes saAni{from{opacity:0;transform:scale(0,0);} to{opacity:1;transform:scale(1,1);} }*/
  /*.watch-code.code-show{display:block;animation-name:scode;-webkit-animation:scode 0.3s linear 0s normal none;-moz-animation:scode 0.3s linear 0s normal none;animation:scode 0.3s linear 0s normal none;}*/
  /*@-webkit-keyframes scode{from{opacity:0;-webkit-transform:scale(1,0);transform-origin:left bottom;} to{opacity:1;-webkit-transform:scale(1,1); transform-origin: left bottom;} }*/
  /*@-moz-keyframes scode{from{opacity:0;-moz-transform:scale(1,0); transform-origin:left bottom;} to{opacity:1;-moz-transform:scale(1,1);transform-origin:bottom bottom;} }*/
  /*@keyframes scode{from{opacity:0;transform:scale(1,0);transform-origin:left bottom;} to{opacity:1;transform:scale(1,1);transform-origin: left bottom;} }*/
  /*.banAnimate .obj-c,.banAnimate .obj-e{display:block;animation-name:saAnic;-webkit-animation:saAnic 0.3s linear 0s normal none;-moz-animation:saAnic 0.3s linear 0s normal none;animation:saAnic 0.3s linear 0s normal none;}*/
  /*@-webkit-keyframes saAnic{from{opacity:0;left:47%;} to{opacity:1;left:50%;} }*/
  /*@-moz-keyframes saAnic{from{opacity:0;left:47%;} to{opacity:1;left:50%;} }*/
  /*@keyframes saAnic{from{opacity:0;left:47%;} to{opacity:1;left:50%;} }*/
  /*.banAnimate .obj-d,.banAnimate .obj-f{display:block;animation-name:saAnid;-webkit-animation:saAnid 0.3s linear 0s normal none;-moz-animation:saAnid 0.3s linear 0s normal none;animation:saAnid 0.3s linear 0s normal none;}*/
  /*@-webkit-keyframes saAnid{from{opacity:0;left:53%;} to{opacity:1;left:50%;} }*/
  /*@-moz-keyframes saAnid{from{opacity:0;left:53%;} to{opacity:1;left:50%;} }*/
  /*@keyframes saAnid{from{opacity:0;left:53%;} to{opacity:1;left:50%;} }*/
</style>
<!--[if lte IE 6]>
<script type="text/javascript" src="/static/bower_components/banner/belatedPNG.js"></script>
<script type="text/javascript">
var __IE6=true;
DD_belatedPNG.fix('.logo img,.prev img,.next img,img');
</script>
<![endif]-->
<!-- 代码 开始 -->
<div class="slide-main" id="touchMain">
  <a class="prev" href="javascript:;" stat="prev1001"><img src="/static/src/common/images/l-btn.png" /></a>
  <div class="slide-box" id="slideContent">
    <div class="slide" id="bgstylec" style="background: #000;">
      <img src="/static/dist/common/images/banner-0.png" alt=""/>
    </div>
    <div class="slide" id="bgstylea" style="background: #000;">
      <img src="/static/dist/common/images/banner-1.png" alt=""/>
    </div>
    <div class="slide" id="bgstyleb" style="background: #000;">
      <img src="/static/dist/common/images/banner-2.png" alt=""/>
    </div>
  </div>
  <a class="next" href="javascript:;" stat="next1002"><img src="/static/src/common/images/r-btn.png" /></a>
  <div class="item">
    <a class="cur" stat="item1001" href="javascript:;"></a><a href="javascript:;" stat="item1002"></a><a href="javascript:;" stat="item1003"></a>
  </div>
</div>
<!-- 代码 结束 -->
<script>
  seajs.use("/static/src/common/scripts/banner");
</script>
