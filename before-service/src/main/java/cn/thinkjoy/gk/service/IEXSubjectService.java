package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.SubjectPojo;

import java.util.List;

/**
 * Created by yhwang on 15/9/23.
 */
public interface IEXSubjectService {
    /**
     * 查询科目列表
     * @return
     */
    List<SubjectPojo> getSubjectList();
}
