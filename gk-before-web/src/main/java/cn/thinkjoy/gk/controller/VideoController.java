package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.VideoCourse;
import cn.thinkjoy.gk.domain.VideoSection;
import cn.thinkjoy.gk.pojo.SubjectPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.*;
import cn.thinkjoy.gk.util.HttpUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/23.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/before/video")
public class VideoController extends ZGKBaseController {
    @Autowired
    private IEXSubjectService iexSubjectService;

    @Autowired
    private IEXVideoCourseService iexVideoCourseService;

    @Autowired
    private IVideoSectionService videoSectionService;

    @Autowired
    private IVideoCourseService videoCourseService;

    /**
     * 根据分类获取视频课程列表
     * @return
     */
    @RequestMapping(value = "getVideoList",method = RequestMethod.GET)
    @ResponseBody
    public List<VideoCoursePojo> getVideoList() throws Exception{
        String pageNo = HttpUtil.getParameter(request,"pageNo","0");
        String pageSize = HttpUtil.getParameter(request,"pageSize","10");
        String classifyType = request.getParameter("classifyType");
        String subjectId = request.getParameter("subjectId");
        String sortType = HttpUtil.getParameter(request,"sortType","1");//默认按照创时间倒序排序
        String searchName = request.getParameter("searchName");
        if(StringUtils.isBlank(classifyType)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        long areaId= getAreaId();
        List<VideoCoursePojo> videoCoursePojos = iexVideoCourseService.getVideoListByParams(StringUtils.isBlank(subjectId)? null : Long.valueOf(subjectId), Integer.valueOf(classifyType), Integer.parseInt(sortType),searchName, Integer.valueOf(pageNo) * Integer.valueOf(pageSize), Integer.valueOf(pageSize),areaId);
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
    public List<SubjectPojo> getSubjectList() throws Exception{
        long areaId= getAreaId();
        List<SubjectPojo> subjectPojos = iexSubjectService.getSubjectList(areaId);
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
    public Map<String,Object> getVideoSectionList(){
        String courseId = request.getParameter("courseId");
        UserAccountPojo user=null;
        try{
            user = getUserAccountPojo();
        }catch (Exception e){
           throw  new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }
        if(StringUtils.isBlank(courseId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        List<VideoSection> videoSectionList=new ArrayList<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("courseId",courseId);
        List<VideoSection> videoSections = videoSectionService.queryList(queryMap, "sectionSort", "ASC");
        if(videoSections == null  || videoSections.size() == 0){
            throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
        }
        for(VideoSection videoSection:videoSections){
            if(user.getVipStatus() == 0){
                if(videoSection.getSectionSort() == 1){
                    videoSection.setIsAccept(1);
                }
            }else {
                videoSection.setIsAccept(1);
            }
            videoSectionList.add(videoSection);
        }
        VideoCourse videoCourse =(VideoCourse) videoCourseService.findOne("id",courseId);
        videoCourse.setHit(videoCourse.getHit() + 1);
        videoCourseService.update(videoCourse);
        Map<String,Object> returnMap= Maps.newHashMap();
        returnMap.put("videoCourse",videoCourse);
        returnMap.put("videoSectionList",videoSectionList);
        return returnMap;
    }

    /**
     * 根据课时ID获取课时详情
     * @return
     */
    @RequestMapping(value = "getVideoSectionDetail",method = RequestMethod.GET)
    @ResponseBody
    public VideoSection getVideoSectionDetail(){
        String videoSectionId = request.getParameter("videoSectionId");
        if(StringUtils.isBlank(videoSectionId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        VideoSection videoSection = (VideoSection)videoSectionService.findOne("id",Long.valueOf(videoSectionId));
        if(videoSection == null){
            throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
        }
        VideoCourse videoCourse =(VideoCourse) videoCourseService.findOne("id",videoSection.getCourseId());
        videoCourse.setId(videoSection.getCourseId());
        videoCourse.setHit(videoCourse.getHit() + 1);
        videoCourseService.update(videoCourse);
        return videoSection;
    }


}
