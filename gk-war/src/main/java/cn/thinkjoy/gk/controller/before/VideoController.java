package cn.thinkjoy.gk.controller.before;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.ExaminationPaper;
import cn.thinkjoy.gk.pojo.PaperPojo;
import cn.thinkjoy.gk.pojo.SubjectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.pojo.VideoSectionPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IEXSubjectService;
import cn.thinkjoy.gk.service.IEXVideoCourseService;
import cn.thinkjoy.gk.service.IExaminationPaperService;
import cn.thinkjoy.gk.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/23.
 */
@Controller
@RequestMapping("/before/video")
public class VideoController extends BaseController {
    @Autowired
    private IEXSubjectService iexSubjectService;
    @Autowired
    private IEXVideoCourseService iexVideoCourseService;
    @Autowired
    private IExaminationPaperService iExaminationPaperService;
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
        String subjectId = request.getParameter("subjectId");
        String sortType = HttpUtil.getParameter(request,"sortType","1");//默认按照创时间倒序排序
        if(StringUtils.isBlank(classifyType)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        List<VideoCoursePojo> videoCoursePojos = iexVideoCourseService.getVideoListByParams(subjectId == null?null:Long.valueOf(subjectId),Integer.valueOf(classifyType),Integer.parseInt(sortType),Integer.valueOf(pageNo)*Integer.valueOf(pageSize),Integer.valueOf(pageSize));
        if(videoCoursePojos == null || videoCoursePojos.size() == 0){
            throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
        }
        return videoCoursePojos;
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

    /**
     * 根据课程ID获取课时列表
     * @return
     */
    @RequestMapping(value = "getVideoSectionList",method = RequestMethod.GET)
    @ResponseBody
    public List<VideoSectionPojo> getVideoSectionList(){
        String courseId = request.getParameter("courseId");
        if(StringUtils.isBlank(courseId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        return null;
    }

    /**
     * 根据课时ID获取课时详情
     * @return
     */
    @RequestMapping(value = "getVideoSection",method = RequestMethod.GET)
    @ResponseBody
    public VideoSectionPojo getVideoSectionDetail(){
        return null;
    }
    /**
     * 获取试卷列表
     * @return
     */
    @RequestMapping(value = "getPaperList",method = RequestMethod.GET)
    @ResponseBody
    public List<PaperPojo> getPaperList(){
        Map<String,Object> queryMap = new HashMap<>();
        return null;
    }

    /**
     * 获取试卷详情
     * @return
     */
    @RequestMapping(value = "getPaper",method = RequestMethod.GET)
    @ResponseBody
    public ExaminationPaper getPaperDetail(){
        String paperId = request.getParameter("paperId");
        if(StringUtils.isBlank(paperId)){
        }
      return null;
    }

}
