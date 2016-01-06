package cn.thinkjoy.gk.controller.data;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.pojo.VocabularyPojo;
import cn.thinkjoy.gk.service.IVocabularyExService;
import com.google.common.collect.Maps;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/12/29.
 */
@Controller
@RequestMapping(value = "/vocabulary")
public class VocabularyController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(VocabularyController.class);

    @Autowired
    private IVocabularyExService vocabularyExService;

    @RequestMapping(value = "/getVocabularyPojoList" ,method = RequestMethod.GET)
    @ResponseBody
    public List<VocabularyPojo> getVocabularyPojoList(@RequestParam(value = "title",required = false)String title,
                                                      @RequestParam(value = "offset",required = false)String offset,
                                                      @RequestParam(value = "rows",required = false)String rows) throws Exception {
        long areaId=getAreaCookieValue();
        Map<String,Object> params= Maps.newHashMap();
        params.put("title",title);
        params.put("offset",offset);
        params.put("rows",rows);
        params.put("orderBy","createDate");
        params.put("sortBy","desc");
        params.put("areaId",areaId);
        List<VocabularyPojo> vocabularyPojoList=vocabularyExService.getVocabularyPojoList(params);
        return vocabularyPojoList;
    }

}
