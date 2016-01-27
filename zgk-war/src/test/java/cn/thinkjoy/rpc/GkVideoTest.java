/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cmc
 * $Id:  BizSystemService.java 2014-10-21 18:49:46 $
 */

package cn.thinkjoy.rpc;

import cn.thinkjoy.zgk.remote.IGkScheduleService;
import cn.thinkjoy.zgk.remote.IGkVideoService;
import com.google.common.collect.Maps;
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
public class GkVideoTest {

    @Autowired
    private IGkVideoService gkVideoService;
    @Test
    public void testGetGkVideoList() {

        Map<String,Object> map = Maps.newHashMap();
        map.put("isIgnore",false);
        Assert.notNull(gkVideoService.getGkVideoList(map,null,4));
    }
    @Test
    public void testGetGkVideoInfo() {
        Assert.notNull(gkVideoService.getGkVideoInfo(new HashMap<String, Object>(),"29"));
    }
    @Test
    public void testHitInc() {
        Long l1=gkVideoService.getGkVideoInfo(new HashMap<String, Object>(),"29").getGkVideoInfo().getHit();
        gkVideoService.hitInc(new HashMap<String, Object>(),29);
        Long l2=gkVideoService.getGkVideoInfo(new HashMap<String, Object>(),"29").getGkVideoInfo().getHit();
        Assert.isTrue(l1==l2);
    }


}
