<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <%@ include file="common/meta.jsp" %>
    <link rel="stylesheet" href="/static/dist/index/styles/index.css"/>
</head>
<body>
<%@ include file="common/header.jsp" %>
<div class="content">
    <%@ include file="common/banner-wipe.jsp" %>
    <!--指导流程-->
    <div class="section page-body">
        <div class="page-body hot-news-head">
            <section>
                <div class="content-title">
                    <p class="fl w40"></p>
                    <p class="fl w20">
                        <i class="fl arraw"></i>
                        <span>指导流程</span>
                        <i class="fr arraw"></i>
                    </p>

                    <p class="fl w40"></p>
                </div>
            </section>
        </div>
        <section style="padding:10px 0 0">
            <div class="w1000 flow-main">
                <ul class="flow-main-list">
                    <li class="item">
                        <a target="_blank" href="/guide/guide.jsp">
                            <img src="/static/dist/index/images/flow-img1.png" alt=""/>
                        </a>
                        <a target="_blank" href="/guide/guide.jsp" class="name">政策解读</a>
                        <span class="num">1</span>
                        <p class="txt">专业而通俗地解读本省高考招生文件、高考志愿填报和招生的相关政策</p>
                    </li>
                    <li class="item">
                        <a target="_blank" href="/guide/volunteer_forum.jsp">
                            <img src="/static/dist/index/images/flow-img2.png" alt=""/>
                        </a>
                        <a target="_blank" href="/guide/volunteer_forum.jsp" class="name">志愿讲堂</a>
                        <span class="num">2</span>

                        <p class="txt">全面介绍陕西省高考志愿填报的基础知识、规则及方法，帮您走出填报误区</p>
                    </li>
                    <li class="item">
                        <a href="/consult/school_info.jsp">
                            <img src="/static/dist/index/images/flow-img3.png" alt=""/>
                        </a>
                        <a href="/consult/school_info.jsp" class="name">院校信息</a>
                        <span class="num">3</span>
                        <p class="txt">快速查询招生院校信息及政策，完整准确提供历年高考招录数据，及相关信息</p>
                    </li>
                    <li class="item">
                        <a target="_blank" href="/consult/profession_info.jsp">
                            <img src="/static/dist/index/images/flow-img4.png" alt=""/>
                        </a>
                        <a target="_blank" href="/consult/profession_info.jsp" class="name">专业选择</a>
                        <span class="num">4</span>
                        <p class="txt">专业测评系统测定适合专业及就业方向，查询本，专科近两千个专业相关信息</p>
                    </li>
                    <li class="item">
                        <a target="_blank" href="/after/after.jsp">
                            <img src="/static/dist/index/images/flow-img5.png" alt=""/>
                        </a>
                        <a target="_blank" href="/after/after.jsp" class="name">志愿指导</a>
                        <span class="num">5</span>
                        <p class="txt">根据分数和位次快速锁定院校，并对院校进行分类和排序，同时提供线上线下专家一对一服务</p>
                    </li>
                </ul>
            </div>
        </section>
    </div>
    <!--关于平台-->
    <div class="section page-body about-platform">
        <section>
            <h1>关于平台</h1>
            <ul>
                <li>
                    <div class="info-content">
                        <div class="info bgR">
                            <span>优势一</span>
                        </div>
                        <h2>强大的学术保障和专家保障</h2>

                        <p>拥有陕西省最具实力的“高考研究院”，具备强大的高考数据研究和分析能力。</p>

                        <p>本省一线招生专家，省高考阅卷组专家，高考心理专家，职业规划师为家长和考生提供专业，权威的服务和指导。</p>
                    </div>
                </li>
                <li class="ml">
                    <div class="info-content">
                        <div class="info bgB">
                            <span>优势二</span>
                        </div>
                        <h2>个性化备考及志愿指导服务</h2>

                        <p>高考公开课：名师备考复习考试视频,由名师视频讲解，包括学科冲刺课程视频、志愿填报等独家课程资源

                        <p>

                        <p>专家一对一地面辅导： 针对考生进行"量身定做",个性化指导、独享专家服务</p>

                        <p>名校模拟卷：独家提供省内名校内部模拟卷</p>

                        <p>在线专家： 提供学习方法、备考心理等在线服务</p>
                    </div>
                </li>
                <li class="ml">
                    <div class="info-content">
                        <div class="info bgG">
                            <span>优势三</span>
                        </div>
                        <h2>精准可靠 超低误差</h2>

                        <p>
                            独创高考大数据算法规则，有效规避志愿风险，能够实现数据的智能修正，依据“冲、稳、保、垫”的原则为考生精准锁定理想院校和专业范围，为考生提供“预估精度高“、”超低失误率”的智能专业的志愿指导</p>
                    </div>
                </li>
            </ul>
        </section>
    </div>

    <!--在线互动-->
    <div class="section page-body">
        <section>
            <div class="content-title">
                <p class="fl w40"></p>

                <p class="fl w20">
                    <i class="fl arraw"></i>
                    <span>在线互动</span>
                    <i class="fr arraw"></i>
                </p>
                <p class="fl w40"></p>
            </div>
            <h6 class="w1000 ta sub-title c888 mt20">每周5x8专业客服为您竭诚服务</h6>

            <div class="search-content ta mt40">
                 <span>
                     <input type="text" placeholder="请输入你要提问的问题关键字" class="search-val"/>
                     <input type="button" value="搜索" class="go-search"/>
                     <button class="ask-question">我要提问</button>
                </span>
            </div>
            <ul class="tabs-list mt20" id="tabs-online">
                <li class="active">最新解答</li>
                <li>热门解答</li>
                <span class="more">更多</span>
            </ul>
            <div class="tab tab-0" id="tab_0">
                <%--<div class="detile-content mt20">--%>
                    <%--<div class="detile-header">--%>
                        <%--<span class="order-number">1</span>--%>
                        <%--<span class="detile-title">军队、武警部队院校招生，国防生体格检查标准</span>--%>
                    <%--</div>--%>
                    <%--<div class="detile-info mt20">--%>
                        <%--徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="detile-content mt20">--%>
                    <%--<div class="detile-header">--%>
                        <%--<span class="order-number">2</span>--%>
                        <%--<span class="detile-title">军队、武警部队院校招生，国防生体格检查标准</span>--%>
                    <%--</div>--%>
                    <%--<div class="detile-info mt20">--%>
                        <%--徐老师，正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>

            <div class="tab tab-1 hide" id="tab_1">
                <%--<div class="detile-content mt20">--%>
                    <%--<div class="detile-header">--%>
                        <%--<span class="order-number">1</span>--%>
                        <%--<span class="detile-title">正高级教师，国防生体格检查标准</span>--%>
                    <%--</div>--%>
                    <%--<div class="detile-info mt20">--%>
                        <%--徐老师徐老师，正高级教师，任教于西北工业大学附属中学，徐老师，正高级教师，任教于西北工业大学附属中学正高级教师，任教于西北工业大学附属中学，高中语文教研组长。西安市教育学会特聘研究员。陕西省教学能手，陕西省首批学科带头--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </section>
    </div>

    <!--热门资讯 -->
    <div class="section">
        <div class="page-body hot-news-head">
            <section>
                <div class="content-title">
                    <p class="fl w40"></p>

                    <p class="fl w20">
                        <i class="fl arraw"></i>
                        <span>热门资讯</span>
                        <i class="fr arraw"></i>
                    </p>

                    <p class="fl w40"></p>
                </div>
            </section>
        </div>

        <div class="page-body hot-news-body">
            <section>
                <ul class="tabs-list" id="tabs-hosts">
                    <li class="active">高考热点</li>
                    <li>各地招办联系方式</li>
                    <span class="more" id="hot-info">更多</span>
                </ul>
                <ul class="tab-info hot-list mt20">
                    <%--<li>--%>
                        <%--<div class="icon ta">--%>
                            <%--<span>4月25日</span>--%>
                        <%--</div>--%>
                        <%--<div class="title-info">--%>
                            <%--<h3>2016年贵州高考：千万别错过了这些时间点！</h3>--%>
                            <%--<h6>虽然离高考还有九个多月，不过，想要在明年6月的高考中想要在明年6月的高考中</h6>--%>
                        <%--</div>--%>
                    <%--</li>--%>
                </ul>
                <ul class="tab-info hide">
                    <div class="tel-content-box">
                        <div class="row" id='address-box'>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <%--<div class="col-3">--%>
                                <%--<p class="area-name">陕西省延安市</p>--%>

                                <%--<p class="tel-num"><img src="/static/dist/user/images/icon-tel-area.png"><span class="tel">4008-005-0001</span>--%>
                                    <%--陕西省延安市--%>
                                <%--</p>--%>
                            <%--</div>--%>

                        </div>
                    </div>
                </ul>
            </section>
        </div>
    </div>

    <!--高考志愿填报日程 -->
    <div class="section page-body">
        <section>
            <div class="content-title">
                <p class="fl w40 w35"></p>

                <p class="fl w20 w30">
                    <i class="fl arraw"></i>
                    <span>高考志愿填报日程</span>
                    <i class="fr arraw"></i>
                </p>

                <p class="fl w40 w35"></p>
            </div>
            <div class="calendar">
                <img src="/static/dist/index/images/calendar_img.png"/>
            </div>
        </section>
    </div>
</div>
<%@ include file="common/footer.jsp" %>
<script>
    seajs.use("/static/src/index/scripts/index");
</script>
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1256565213'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1256565213%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
</body>
</html>
