package cn.thinkjoy.gk.controller.guide;

import cn.thinkjoy.common.domain.SearchField;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.gk.domain.VolunteerSchool;
import cn.thinkjoy.gk.domain.VolunteerSchoolCategory;
import cn.thinkjoy.gk.service.IVolunteerSchoolCategoryService;
import cn.thinkjoy.gk.service.IVolunteerSchoolService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 志愿学堂
 */
@Controller
@RequestMapping("volunteerSchool")
public class VolunteerSchoolController {

    @Autowired
    private IVolunteerSchoolCategoryService volunteerSchoolCategoryService;
    @Autowired
    private IVolunteerSchoolService volunteerSchoolService;

    /** 志愿学堂分类 */
    @RequestMapping(value = "/categorys")
    @ResponseBody
    public List<VolunteerSchoolCategory> getCategorys() {
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("status", 1);
        return volunteerSchoolCategoryService.queryList(conditions, null, null);
    }
    /** 志愿学堂文章列表 */
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<VolunteerSchool> getArticles(@RequestParam("cateId") long cateId, @RequestParam("pn") int pn,
                                                     @RequestParam("ps") int ps, @RequestParam("lastId") long lastId) {
        if (pn == 0) pn = 1;
        if (ps == 0) ps = 10;
        Map<String, Object> conditions = Maps.newHashMap();
        SearchField category = new SearchField();
        category.setField("categoryId");
        category.setOp("=");
        category.setData(cateId+"");
        SearchField status = new SearchField();
        status.setField("status");
        status.setOp("=");
        status.setData("1");
        SearchField beginId = new SearchField();
        beginId.setField("id");
        beginId.setOp(">");
        beginId.setData(lastId + "");
        conditions.put(category.getField(), category);
        conditions.put(status.getField(), status);
        conditions.put(beginId.getField(), beginId);
        conditions.put("groupOp", "and");
        return volunteerSchoolService.queryPageByDataPerm(null, conditions, pn, (pn-1) * ps, ps);
    }

    @RequestMapping(value = "/article/{id}")
    @ResponseBody
    public VolunteerSchool getArticleDetial(@PathVariable long id) {
        return (VolunteerSchool) volunteerSchoolService.fetch(id);
    }

}
