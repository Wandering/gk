package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ex.IEXVideoCourseDAO;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.service.IEXVideoCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhwang on 15/9/23.
 */
@Service("EXVideoCourseServiceImpl")
public class EXVideoCourseServiceImpl implements IEXVideoCourseService{
    @Autowired
    private IEXVideoCourseDAO iexVideoCourseDAO;
    /**
     * 根据类别和科目查询课程
     *
     * @return
     */
    @Override
    public List<VideoCoursePojo> getVideoListByParams(Long subjectId,Integer classifyType,Integer sortType,Integer offset,Integer rows) {
        return iexVideoCourseDAO.getVideoListByParams(subjectId,classifyType,sortType,offset,rows);
    }
}
