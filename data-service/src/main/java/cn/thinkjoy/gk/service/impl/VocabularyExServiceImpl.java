package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IVocabularyExDAO;
import cn.thinkjoy.gk.pojo.VocabularyPojo;
import cn.thinkjoy.gk.service.IVocabularyExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/12/29.
 */
@Service("VocabularyExServiceImpl")
public class VocabularyExServiceImpl implements IVocabularyExService {

    @Autowired
    private IVocabularyExDAO iVocabularyExDAO;

    @Override
    public List<VocabularyPojo> getVocabularyPojoList(Map<String, Object> params) {
        return iVocabularyExDAO.getVocabularyPojoList(params);
    }

    @Override
    public VocabularyPojo getVocabularyPojoById(long id) {
        return iVocabularyExDAO.getVocabularyPojoById(id);
    }
}
