package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.UserCollect;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserCollectPojo;
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
     * 保存当前用户的收藏院校
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/saveUserCollect",method = RequestMethod.GET)
    @ResponseBody
    public boolean saveUserCollect(@RequestParam(value = "universityId",required = true)long universityId){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        UserCollect userCollect=new UserCollect();
        userCollect.setUserId(userId);
        userCollect.setUniversityId(universityId);
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
     * 删除指定收藏院校
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUserCollect",method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUserCollect(@RequestParam(value = "id",required = true)long id){
        boolean flag = false;
        try {
            userCollectService.deleteById(id);
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
    @RequestMapping(value = "/getUserCollectPojoList",method = RequestMethod.GET)
    @ResponseBody
    public List<UserCollectPojo> getUserCollectPojoList(@RequestParam(value ="offset",required = false)Integer offset, @RequestParam(value = "rows",required = false)Integer rows){
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        long userId=userAccountPojo.getId();
        Map<String,Object> param= Maps.newHashMap();
        param.put("userId",userId);
        param.put("offset",offset);
        param.put("rows",rows);
        List<UserCollectPojo> userCollectPojoList=userCollectExService.getUserCollectPojoList(param);
        return userCollectPojoList;
    }

}
