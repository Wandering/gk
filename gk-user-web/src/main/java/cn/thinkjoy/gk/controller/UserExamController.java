package cn.thinkjoy.gk.controller;


import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.UserExam;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zuohao on 15/9/23.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/exam")
public class UserExamController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserExamController.class);

    @Autowired
    private IUserExamService userExamService;

    /**
     * 更新个人考试成绩信息
     * @return
     */
    @RequestMapping(value = "updateUserExam")
    @ResponseBody
    public boolean getUserInfo(UserExam userExam) throws Exception{

        String id=getAccoutId();

        if(StringUtils.isEmpty(id)){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        userExam.setId(Long.valueOf(id));

        boolean flag = false;
        try {
            userExamService.update(userExam);
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }

        return flag;
    }

    /**
     * 获取用户考试信息
     * @return
     */
    @RequestMapping(value = "findUserExam",method = RequestMethod.GET)
    @ResponseBody
    public UserExam findUserExam() throws Exception{

        String id=getAccoutId();

        if(StringUtils.isEmpty(id)){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        UserExam userExam = (UserExam)userExamService.findOne("id", id);

        return userExam;
    }

}
