/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cmc
 * $Id:  BizSystemService.java 2014-10-21 18:49:46 $
 */

package cn.thinkjoy.rpc;

import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.remote.IGkHotService;
import cn.thinkjoy.zgk.remote.IGkPolicyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:zgkwar-dubbo-consumer-test.xml")
public class GkPolicyTest {

    @Autowired
    private IGkPolicyService gkPolicyService;
    @Test
    public void testGetGkPolicyList() {
        Map<String,Object> map = new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","createDate");
        map.put("sortBy","desc");
        Assert.notNull(gkPolicyService.getGkPolicyList(map, null, 4));
        QueryUtil.setMapOp(map, "type", "=", 1);
        QueryUtil.setMapOp(map,"title","LIKE","%"+"标题1"+"%");
        Assert.notNull(gkPolicyService.getGkPolicyList(map, 1, 4));

    }
    @Test
    public void testGetGkPolicyInfo() {
        Assert.notNull(gkPolicyService.getGkPolicyInfo(new HashMap<String, Object>(),"38"));
        //baseDataService.
        //Assert.notNull(user, "");
    }

}
