/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cmc
 * $Id:  BizSystemService.java 2014-10-21 18:49:46 $
 */

package cn.thinkjoy.rpc;

import cn.thinkjoy.gk.common.UserAreaContext;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.remote.IGkAdmissionLineService;
import cn.thinkjoy.zgk.remote.IGkScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

;import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:zgkwar-dubbo-consumer-test.xml")
public class GkAdmissionLineTest {

    @Autowired
    private IGkAdmissionLineService gkAdmissionLineService;
    @Test
    public void testGetGkAdmissionLineList() {
        UserAreaContext.setCurrentUserArea("sn");
        Map<String,Object> map=new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","lastModDate");
        map.put("sortBy","desc");
        Assert.notNull(gkAdmissionLineService.getGkAdmissionLineList(map, null, 4));
        map=new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","lastModDate");
        map.put("sortBy","desc");
//        年份
        QueryUtil.setMapOp(map,"enrollingyear","=","2016");
//        院校名称 模糊
        QueryUtil.setMapOp(map,"universityname","like","%"+"西安外"+"%");
//        地区
        QueryUtil.setMapOp(map,"universityareaid","=",370000);
//        特征
        QueryUtil.setMapOp(map,"universityproperty","like","%"+"33"+"%");
//        批次
        QueryUtil.setMapOp(map,"enrollingbatch","=",2);
//        文史/理工
        QueryUtil.setMapOp(map,"enrollinguniversityMajorType","=",2);

        Assert.notNull(gkAdmissionLineService.getGkAdmissionLineList(map,null,4));
    }


}
