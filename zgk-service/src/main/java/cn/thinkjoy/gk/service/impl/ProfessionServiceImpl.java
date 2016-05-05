package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.gk.dao.IProfessionDao;
import cn.thinkjoy.gk.entity.Profession;
import cn.thinkjoy.gk.service.IProfessionService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.MD5Util;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangguorong on 16/5/5.
 */
@Service("ProfessionServiceImpl")
public class ProfessionServiceImpl implements IProfessionService{

    @Autowired
    private IProfessionDao professionDao;

    @Override
    public Map<String, String> getProfessionalInfoByKeywords(String keywords) {

        Map<String,String> map = Maps.newHashMap();

        String key = MD5Util.md5String(keywords);
        RedisRepository redisRepository = RedisUtil.getInstance();
        boolean flag = redisRepository.exists(key);
        if (flag) {
            map = JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        } else {

            List<Profession> professions = professionDao.getProfessionalInfoByKeywords(keywords);

            for(Profession profession : professions){
                map.put(profession.getId().toString(),profession.getProfessionName());
            }

            redisRepository.set(key,JSON.toJSON(map));
        }

        return map;
    }
}
