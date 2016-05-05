package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IProfessionDao;
import cn.thinkjoy.gk.entity.Profession;
import cn.thinkjoy.gk.service.IProfessionService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/5/5.
 */
@Service("ProfessionServiceImpl")
public class ProfessionServiceImpl implements IProfessionService{

    @Autowired
    private IProfessionDao professionDao;

    @Override
    public Map<String, String> getProfessionalInfoByKeywords(String keywords) {
        // TODO 加入redis缓存
        Map<String,String> map = Maps.newHashMap();

        List<Profession> professions = professionDao.getProfessionalInfoByKeywords(keywords);
        for(Profession profession : professions){
            map.put(profession.getId().toString(),profession.getProfessionName());
        }
        return map;
    }
}
