package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.domain.Count;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.ICountExService;
import cn.thinkjoy.gk.service.ICountService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zuohao on 15/12/25.
 */
@Service("CountExServiceImpl")
public class CountExServiceImpl implements ICountExService {

    @Autowired
    private ICountService countService;

    @Override
    public void updateCount(long projectId, String type,String operation) {
        Map<String,Object> map= Maps.newHashMap();
        map.put("projectId",projectId);
        map.put("type",type);
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("condition",map);
        Count count= (Count) countService.queryOne(queryMap);
        if (operation.equals("add")){
            if (count==null){
                count=new Count();
                count.setProjectId(projectId);
                count.setType(type);
                count.setNumber(1);
                countService.add(count);
            }else {
                count.setNumber(count.getNumber() + 1);
                countService.update(count);
            }
        }else if(operation.equals("reduce")){
            if (count==null){
                new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
            }else {
                count.setNumber(count.getNumber() - 1);
                countService.update(count);
            }
        }

    }
}
