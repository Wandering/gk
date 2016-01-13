/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cmc
 * $Id:  BizSystemService.java 2014-10-21 18:49:46 $
 */

package cn.thinkjoy.rpc;

import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.remote.IGkHotService;
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
public class GkHotTest {

    @Autowired
    private IGkHotService gkHotService;
    @Test
    public void testGetGkHotList() {
        Map<String,Object> map = new HashMap<>();
        map.put("groupOp","and");
        QueryUtil.setMapOp(map, "type", "=", 0);
        map.put("orderBy","createDate");
        map.put("sortBy","desc");
        Assert.notNull(gkHotService.getGkHotList(map, null, 4));
        QueryUtil.setMapOp(map, "type", "=", 1);
        Assert.notNull(gkHotService.getGkHotList(map, 1, 4));

    }
    @Test
    public void testGetGkHotInfo() {
        Assert.notNull(gkHotService.getGkHotInfo("255"));
        //baseDataService.
        //Assert.notNull(user, "");
    }

}
