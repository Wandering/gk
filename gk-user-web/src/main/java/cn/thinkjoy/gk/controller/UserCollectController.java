package cn.thinkjoy.gk.controller;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.UserCollect;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserCollectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.ICountExService;
import cn.thinkjoy.gk.service.IUserCollectExService;
import cn.thinkjoy.gk.service.IUserCollectService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zuohao on 15/11/2.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "userCollection")
public class UserCollectController extends ZGKBaseController {
    public int TOKEN_EXPIRE_TIME = 60 * 60;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserCollectController.class);

    @Autowired
    private IUserCollectService userCollectService;

    @Autowired
    private IUserCollectExService userCollectExService;

    @Autowired
    private ICountExService countExService;
    @Autowired
    private cn.thinkjoy.zgk.remote.IUniversityService iremoteUniversityService;

    /**
     * 保存当前用户的收藏项目（学校或课程）
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/saveUserCollect",method = RequestMethod.GET)
    @ResponseBody
    public boolean saveUserCollect(@RequestParam(value = "projectId",required = true)long projectId,@RequestParam(value = "type",required = true)String type){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param=Maps.newHashMap();
        param.put("userId",userId);
        param.put("projectId",projectId);
        param.put("type",type);
        if(userCollectExService.isCollect(param)>0){
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        UserCollect userCollect=new UserCollect();
        userCollect.setUserId(userId);
        userCollect.setProjectId(projectId);
        userCollect.setType(type);
        userCollect.setCreateDate(System.currentTimeMillis());
        boolean flag = false;
        try {
            userCollectService.add(userCollect);
            countExService.updateCount(projectId, type,"add");
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }

        return flag;
    }

    /**
     * 删除指定收藏项目（学校或课程）
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/deleteUserCollect",method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUserCollect(@RequestParam(value = "projectId",required = true)long projectId,@RequestParam(value = "type",required = true)String type){
        boolean flag = false;
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param=Maps.newHashMap();
        param.put("userId", userId);
        param.put("projectId", projectId);
        param.put("type", type);
        try {
            userCollectService.deleteByCondition(param);
            countExService.updateCount(projectId, type,"reduce");
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return flag;
    }

    /**
     * 查询当前用户收藏的特定项目列表
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getUserCollectList",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserCollectList(@RequestParam(value ="type",required = true)String type,@RequestParam(value ="offset",required = false)Integer offset, @RequestParam(value = "rows",required = false)Integer rows){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param= Maps.newHashMap();
        param.put("userId",userId);
        param.put("offset",offset);
        param.put("rows",rows);
        param.put("type",type);
        Map<String, Object> returnParam = Maps.newHashMap();
        //收藏学校
        if(type.equals("1")) {
            int sum = userCollectExService.getUserCollectCount(param);
            List<UserCollectPojo> userCollectPojoList = userCollectExService.getUserCollectUniversityPojoList(param);
            userCollectPojoList=lists(userCollectPojoList);
            returnParam.put("sum", sum);
            returnParam.put("userCollectPojoList", userCollectPojoList);
        }
        //收藏课程
        else if(type.equals("2")){
            List<VideoCoursePojo> videoCoursePojoList=userCollectExService.getUserCollectVideoCoursePojoList(param);
            int sum = userCollectExService.getUserCollectCount(param);
            returnParam.put("sum", sum);
            returnParam.put("videoCoursePojoList",videoCoursePojoList);
        }
        return returnParam;
    }

    /**
     * 判断指定用户是否收藏指定项目（学校或课程），0为否，1为是
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/isUniversityCollect",method = RequestMethod.GET)
    @ResponseBody
    public int isUniversityCollect(@RequestParam(value = "projectId",required = true)String projectId,@RequestParam(value = "type",required = true)String type){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param=Maps.newHashMap();
        param.put("userId",userId);
        param.put("projectId",projectId);
        param.put("type",type);
        return userCollectExService.isCollect(param);
    }
    private Map<String, Object> getPropertys() {
        List<Map<String, Object>> list = null;
        Map<String, Object> propertysMap = new HashMap<>();

        String key = "universityPropertys";
        RedisRepository redisRepository = RedisUtil.getInstance();
        boolean flag = redisRepository.exists(key);
        if (flag) {
            propertysMap = JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        } else {
            list = iremoteUniversityService.getDataDictListByType("FEATURE");
            for (Map<String, Object> map : list) {
                propertysMap.put(map.get("dictId").toString(), map.get("name").toString());
            }
            redisRepository.set(key, JSON.toJSON(propertysMap), TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return propertysMap;
    }

    private List lists(List<UserCollectPojo> userCollectPojoList){
        for(UserCollectPojo userCollectPojo:userCollectPojoList){
            Map<String, Object> propertysMap = getPropertys();

            if(!StringUtils.isEmpty(userCollectPojo.getPropertyName())) {
                for (String str :userCollectPojo.getPropertyName().split(",")) {
                    Iterator<String> propertysIterator = propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()) {
                        String key = propertysIterator.next();
                        String value = propertysMap.get(key).toString();
                        if (str.indexOf(value) > -1) {
                            propertysMap.put(key, value);
                        }
                    }
                }
            }
            userCollectPojo.setPropertys(propertysMap);

        }


        return userCollectPojoList;
    }

}
