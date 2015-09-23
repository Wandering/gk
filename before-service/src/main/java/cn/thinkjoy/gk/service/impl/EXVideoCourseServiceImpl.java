package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ex.IEXVideoCourseDAO;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.service.IEXVideoCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<VideoCoursePojo> getVideoListByParams(Map<String,Object> queryMap) {

        return iexVideoCourseDAO.getVideoListByParams(queryMap);
    }
}
