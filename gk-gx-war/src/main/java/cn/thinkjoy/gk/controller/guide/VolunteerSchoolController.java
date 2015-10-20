package cn.thinkjoy.gk.controller.guide;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.domain.SearchField;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.VolunteerSchool;
import cn.thinkjoy.gk.domain.VolunteerSchoolCategory;
import cn.thinkjoy.gk.service.IVolunteerSchoolCategoryService;
import cn.thinkjoy.gk.service.IVolunteerSchoolService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 志愿学堂
 */
@Controller
@Scope("prototype")
@RequestMapping("volunteerSchool")
public class VolunteerSchoolController extends BaseController {

    @Autowired
    private IVolunteerSchoolCategoryService volunteerSchoolCategoryService;
    @Autowired
    private IVolunteerSchoolService volunteerSchoolService;

    /** 志愿学堂分类 */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<VolunteerSchoolCategory> getCategorys() throws Exception{
        long areaId=getAreaCookieValue();
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("status", 1);
        conditions.put("areaId", areaId);
        return volunteerSchoolCategoryService.queryList(conditions, null, null);
    }
    /** 志愿学堂文章列表 */
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<VolunteerSchool> getArticles(HttpServletRequest request,
                                                     @RequestParam("pn") int pn,@RequestParam("ps") int ps) throws Exception{
        long areaId=getAreaCookieValue();
        long cateId = ServletRequestUtils.getLongParameter(request, "cateId", 0);
        String keyword = request.getParameter("kw");
        if (pn == 0) pn = 1;
        if (ps == 0) ps = 10;
        Map<String, Object> conditions = Maps.newHashMap();
        if (StringUtils.isNotBlank(keyword)) {
            conditions.put("keyword", keyword);
        }
        if (cateId > 0) {
            SearchField category = new SearchField();
            category.setField("categoryId");
            category.setOp("=");
            category.setData(cateId + "");
            conditions.put(category.getField(), category);
        }
        SearchField status = new SearchField();
        status.setField("status");
        status.setOp("=");
        status.setData("1");
        conditions.put(status.getField(), status);

        conditions.put("groupOp", "and");
        status.setField("areaId");
        status.setOp("=");
        status.setData(String.valueOf(areaId));
        conditions.put(status.getField(), status);
        return volunteerSchoolService.queryPageByDataPerm(null, conditions, pn, (pn-1) * ps, ps);
    }

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    @ResponseBody
    public VolunteerSchool getArticleDetial(@RequestParam("id") long id) {
        VolunteerSchool volunteerSchool = (VolunteerSchool) volunteerSchoolService.fetch(id);
        if (volunteerSchool != null) {
//            //记录点击量到缓存
//            Map<Long, Integer> hitsMap = null;
//            RedisRepository redis = RedisUtil.getInstance();
//            Object o = redis.get("volunteerSchoolHitsMap");
//            if (o != null) {
//                hitsMap = (Map<Long, Integer>) o;
//            }
//            if (hitsMap == null) {
//                hitsMap = Maps.newHashMap();
//            }
//            Integer hits = hitsMap.get(id);
//            if (hits == null) {
//                hits = 0;
//            }
//            hitsMap.put(id, hits+1);
//            redis.set("volunteerSchoolHitsMap", hitsMap);
            //直接加到数据库
            volunteerSchoolService.addHits(id, 1);

        }

        return volunteerSchool;
    }

    @RequestMapping(value = "/ranks", method = RequestMethod.GET)
    @ResponseBody
    public List<VolunteerSchool> articleRanks(HttpServletRequest request) throws Exception{
        long areaId=getAreaCookieValue();
        long cateId = ServletRequestUtils.getLongParameter(request, "cateId", 0);
        int ps = ServletRequestUtils.getIntParameter(request, "ps", 3);
        Map<String, Object> conditions = Maps.newHashMap();
        SearchField category = new SearchField();
        category.setField("categoryId");
        category.setOp("=");
        category.setData(cateId + "");
        conditions.put(category.getField(), category);

        SearchField status = new SearchField();
        status.setField("status");
        status.setOp("=");
        status.setData("1");
        conditions.put(status.getField(), status);

        conditions.put("groupOp", "and");
        conditions.put("areaId",areaId);

        BizData4Page<VolunteerSchool> page = volunteerSchoolService.queryPageByDataPerm("", conditions, 1, 0, ps, "hits", "desc");

        return page.getRows();
    }

}
