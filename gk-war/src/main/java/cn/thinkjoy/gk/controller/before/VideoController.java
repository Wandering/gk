package cn.thinkjoy.gk.controller.before;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.pojo.SubjectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.service.IEXSubjectService;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yhwang on 15/9/23.
 */
@Controller
@RequestMapping("/before/video")
public class VideoController extends BaseController {
    private IEXSubjectService iexSubjectService;
    /**
     * 根据分类获取视频课程列表
     * @return
     */
    @RequestMapping(value = "getVideoList",method = RequestMethod.GET)
    @ResponseBody
    public List<VideoCoursePojo> getVideoList(){
        String pageNo = HttpUtil.getParameter(request,"pageNo","0");
        String pageSize = HttpUtil.getParameter(request,"pageSize","10");
        String classifyType = request.getParameter("classifyType");

        if(StringUtils.isBlank(classifyType)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        return null;
    }

    /**
     * 获取科目列表
     * @return
     */
    @RequestMapping(value = "getSubjectList",method = RequestMethod.GET)
    @ResponseBody
    public List<SubjectPojo> getSubjectList(){
        List<SubjectPojo> subjectPojos = iexSubjectService.getSubjectList();
        if(subjectPojos == null || subjectPojos.size() == 0){
            throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
        }
        return subjectPojos;
    }

}
