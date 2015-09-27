package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ISubjectDAO;
import cn.thinkjoy.gk.domain.Subject;
import cn.thinkjoy.gk.pojo.SubjectPojo;
import cn.thinkjoy.gk.service.IEXSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhwang on 15/9/23.
 */
@Service("EXSubjectServiceImpl")
public class EXSubjectServiceImpl implements IEXSubjectService{
    @Autowired
    private ISubjectDAO subjectDAO;
    /**
     * 查询科目列表
     *
     * @return
     */
    @Override
    public List<SubjectPojo> getSubjectList() {
        List<Subject> subjects = subjectDAO.findAll();
        List<SubjectPojo> subjectPojos = new ArrayList<>();
        if(subjects != null && subjects.size() >0){
            for(Subject subject:subjects){
                subjectPojos.add(new SubjectPojo(subject.getId(),subject.getSubjectName()));
            }
        }
        return subjectPojos;
    }
}