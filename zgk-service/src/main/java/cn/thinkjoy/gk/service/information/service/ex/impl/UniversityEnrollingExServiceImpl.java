package cn.thinkjoy.gk.service.information.service.ex.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.gk.dao.information.ex.IPolicyInterpretationExDAO;
import cn.thinkjoy.gk.dao.information.ex.IUniversityEnrollingExDAO;
import cn.thinkjoy.gk.pojo.UniversityEnrollingDTO;
import cn.thinkjoy.gk.service.information.service.ex.IUniversityEnrollingExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyongping on 2016/10/28.
 */
@Service("UniversityEnrollingExServiceImpl")
@Scope("prototype")
public class UniversityEnrollingExServiceImpl extends AbstractPageService<IBaseDAO<UniversityEnrollingDTO>, UniversityEnrollingDTO> implements IUniversityEnrollingExService<IBaseDAO<UniversityEnrollingDTO>,UniversityEnrollingDTO> {
    @Autowired
    private IUniversityEnrollingExDAO universityEnrollingExDAO;

    @Override
    public IBaseDAO<UniversityEnrollingDTO> getDao() {
        return universityEnrollingExDAO;
    }

    @Override
    public List<String> getYear() {
        return universityEnrollingExDAO.getYear();
    }
}
