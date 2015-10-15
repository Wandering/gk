package cn.thinkjoy.gk.controller.before;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.ExaminationPaper;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IEXPaperService;
import cn.thinkjoy.gk.service.IExaminationPaperService;
import cn.thinkjoy.gk.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yhwang on 15/9/23.
 */
@Controller
@Scope("prototype")
@RequestMapping("/before/paper")
public class PaperController extends BaseController {
    @Autowired
    private IExaminationPaperService iExaminationPaperService;
    @Autowired
    private IEXPaperService iexPaperService;
    /**
     * 获取试卷列表
     * @return
     */
    @RequestMapping(value = "getPaperList",method = RequestMethod.GET)
    @ResponseBody
    public List<ExaminationPaper> getPaperList() throws Exception{
        long areaId=getAreaCookieValue();
        String pageNo = HttpUtil.getParameter(request, "pageNo", "0");
        String pageSize = HttpUtil.getParameter(request,"pageSize","10");
        String sortType = HttpUtil.getParameter(request,"sortType","1");
        String subjectId = request.getParameter("subjectId");
        String years = request.getParameter("years");
        String searchName = request.getParameter("searchName");
        List<ExaminationPaper> papers = iexPaperService.getPaperPage(StringUtils.isBlank(subjectId)? null : Long.valueOf(subjectId),Integer.parseInt(sortType),years,searchName,Integer.parseInt(pageNo)*Integer.parseInt(pageSize),Integer.parseInt(pageSize),areaId);
        if(papers == null || papers.size() == 0){
            throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
        }
        return papers;
    }

    /**
     * 获取试卷详情
     * @return
     */
    @RequestMapping(value = "getPaperDetail",method = RequestMethod.GET)
    @ResponseBody
    public ExaminationPaper getPaperDetail(){
        String paperId = request.getParameter("paperId");
        if(StringUtils.isBlank(paperId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        ExaminationPaper paper = (ExaminationPaper)iExaminationPaperService.findOne("id",paperId);
        if(paper == null ){
            throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
        }
        ExaminationPaper newPaper = new ExaminationPaper();
        newPaper.setDownloadsAutomatic(paper.getDownloadsAutomatic()+1);
        newPaper.setId(paper.getId());
        iExaminationPaperService.update(newPaper);
        return paper;
    }
}
