package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.gk.dao.IMajoredDAO;
import cn.thinkjoy.gk.pojo.MajorDetailPojo;
import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SubjectDto;
import cn.thinkjoy.gk.query.MajoredQuery;
import cn.thinkjoy.gk.service.IMajoredService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.MD5Util;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
@Service("MajoredServiceImpl")
public class MajoredServiceImpl implements IMajoredService {

    @Autowired
    private IMajoredDAO majoredDAO;

    @Override
    public List<Map<String, Object>> getMajoreByParentId(Long i) {
        return majoredDAO.getMajoreByParentId(i);
    }

    @Override
    public List<SubjectDto> searchMajored(MajoredQuery query) {
        return majoredDAO.searchMajored(query);
    }

    @Override
    public Integer searchMajoredCount(MajoredQuery query) {
        return majoredDAO.searchMajoredCount(query);
    }

    @Override
    public MajoredDto getMajoredById(String majoredCode) {
        return majoredDAO.getMajoredById(majoredCode);
    }

    @Override
    public List<Map<String, Object>> getUniversityByCode(String majoredCode, String name) {
        return majoredDAO.getUniversityByCode(majoredCode,name);
    }

    @Override
    public List<MajorDetailPojo> getMajorDetailList(String code, String batch, Integer year,String subject) {
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("batch",batch);
        params.put("year",year);
        params.put("subject",subject);
        return majoredDAO.getMajorDetailList(params);
    }

    @Override
    public Map<String, String> getMajoredInfoByKeywords(String keywords) {

        Map<String,String> map = Maps.newHashMap();

        String key = "majored" + MD5Util.md5String(keywords);
        RedisRepository redisRepository = RedisUtil.getInstance();
        boolean flag = redisRepository.exists(key);
        if (flag) {
            map = JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        } else {

            List<MajorDetailPojo> pojos = majoredDAO.getMajoredInfoByKeywords(keywords);

            for(MajorDetailPojo pojo : pojos){
                map.put(pojo.getId().toString(),pojo.getName());
            }

            redisRepository.set(key,JSON.toJSON(map));
        }

        return map;
    }
}
