package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.UserCollect;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserCollectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserCollectExService;
import cn.thinkjoy.gk.service.IUserCollectService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/11/2.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "userCollection")
public class UserCollectController extends BaseController{

    private static final Logger LOGGER= LoggerFactory.getLogger(UserCollectController.class);

    @Autowired
    private IUserCollectService userCollectService;

    @Autowired
    private IUserCollectExService userCollectExService;

    /**
     * 保存当前用户的收藏项目（学校或课程）
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/saveUserCollect",method = RequestMethod.GET)
    @ResponseBody
    public boolean saveUserCollect(@RequestParam(value = "projectId",required = true)long projectId){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param=Maps.newHashMap();
        param.put("userId",userId);
        param.put("projectId",projectId);
        if(userCollectExService.isUniversityCollect(param)>0){
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        UserCollect userCollect=new UserCollect();
        userCollect.setUserId(userId);
        userCollect.setProjectId(projectId);
        userCollect.setType("1");
        userCollect.setCreateDate(System.currentTimeMillis());
        boolean flag = false;
        try {
            userCollectService.add(userCollect);
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
//        UserAccountPojo userAccountPojo=getUserAccountPojo();
//        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
//            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
//        }
//        long userId=userAccountPojo.getId();
        long userId=1l;
        Map<String,Object> param=Maps.newHashMap();
        param.put("userId",userId);
        param.put("projectId", projectId);
        param.put("type", type);
        try {
            userCollectService.deleteByCondition(param);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return flag;
    }

    /**
     * 查询当前用户的收藏院校
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getUserCollectUniversityPojoList",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserCollectUniversityPojoList(@RequestParam(value ="offset",required = false)Integer offset, @RequestParam(value = "rows",required = false)Integer rows){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param= Maps.newHashMap();
        param.put("userId",userId);
        param.put("offset",offset);
        param.put("rows",rows);
        int sum=userCollectExService.getUserCollectUniversityPojoCount(param);
        List<UserCollectPojo> userCollectPojoList=userCollectExService.getUserCollectUniversityPojoList(param);
        Map<String,Object> returnParam=Maps.newHashMap();
        returnParam.put("sum",sum);
        returnParam.put("userCollectPojoList",userCollectPojoList);
        return returnParam;
    }

    /**
     * 判断指定用户是否收藏指定项目（学校或课程），0为否，1为是
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/isUniversityCollect",method = RequestMethod.GET)
    @ResponseBody
    public int isUniversityCollect(@RequestParam(value = "projectId")String projectId){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param=Maps.newHashMap();
        param.put("userId",userId);
        param.put("projectId",projectId);
        return userCollectExService.isUniversityCollect(param);
    }

    /**
     * 查询指定用户收藏的所有课程
     * @param offset
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getUserCollectVideoCoursePojoList",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserCollectVideoCoursePojoList(@RequestParam(value ="offset",required = false)Integer offset, @RequestParam(value = "rows",required = false)Integer rows){
//        UserAccountPojo userAccountPojo=getUserAccountPojo();
//        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
//            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
//        }
//        long userId=userAccountPojo.getId();
        long userId=1L;
        Map<String,Object> param= Maps.newHashMap();
        param.put("userId",userId);
        param.put("offset",offset);
        param.put("rows",rows);
        List<VideoCoursePojo> videoCoursePojoList=userCollectExService.getUserCollectVideoCoursePojoList(param);
        Map<String,Object> returnParam=Maps.newHashMap();
        returnParam.put("videoCoursePojoList",videoCoursePojoList);
        return returnParam;
    }

}
