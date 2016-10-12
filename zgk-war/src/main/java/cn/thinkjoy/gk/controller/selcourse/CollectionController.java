package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.selcourse.ICollectionService;
import cn.thinkjoy.zgk.common.StringUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/10/12.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/collection")
public class CollectionController extends ZGKBaseController{


    @Autowired
    private ICollectionService collectionService;

    /**
     * 用户收藏7选3专业信息
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    @ResponseBody
    public Object save(@RequestParam Integer universityId,
                       @RequestParam String majorCode,
                       @RequestParam Integer batch){
        if (StringUtil.isNulOrBlank(majorCode)){
            throw new BizException("error","majorCode is not null or blank!");
        }
        //获取当前用户的ID
        String userId = this.getAccoutId();
        //组织保信息
        Map<String,Object> map = new HashedMap();
        map.put("universityId",universityId);
        map.put("majorCode",majorCode);
        map.put("batch",batch);
        map.put("userId",userId);
        map.put("createDate",System.currentTimeMillis());
        //保存
        return collectionService.save(map);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public Object query(@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "10") Integer rows){
        Map<String,Object> conditions = new HashedMap();

        conditions.put("userId", this.getAccoutId());
        conditions.put("offset",(page-1)*rows);
        conditions.put("rows",rows);
        List mainData = collectionService.queryPage(conditions);
    int records = collectionService.count(conditions);
        return this.doPage(mainData,records,page,rows);
}


}
