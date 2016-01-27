/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cmc
 * $Id:  BizSystemService.java 2014-10-21 18:49:46 $
 */

package cn.thinkjoy.rpc;

import cn.thinkjoy.zgk.remote.IGkScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

;import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:zgkwar-dubbo-consumer-test.xml")
public class GkScheduleTest {

    @Autowired
    private IGkScheduleService gkScheduleService;
    @Test
    public void testGetGkScheduleList() {

//        Assert.notNull(gkScheduleService.getScheduleList(6));
//        System.out.println(gkScheduleService.getScheduleList(4));
    }
    @Test
    public void testGetGkScheduleInfo() {
        Assert.notNull(gkScheduleService.getScheduleInfo(new HashMap<String, Object>(),"19"));
    }

}
