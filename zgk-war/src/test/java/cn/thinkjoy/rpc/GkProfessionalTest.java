/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cmc
 * $Id:  BizSystemService.java 2014-10-21 18:49:46 $
 */

package cn.thinkjoy.rpc;

import cn.thinkjoy.zgk.remote.IGkProfessionalService;
import cn.thinkjoy.zgk.remote.IGkVideoService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Map;

;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:zgkwar-dubbo-consumer-test.xml")
public class GkProfessionalTest {

    @Autowired
    private IGkProfessionalService gkProfessionalService;
    @Test
    public void testGetProfessionalList() {

        Map<String,Object> map = Maps.newHashMap();
        Assert.notNull(gkProfessionalService.getProfessionalList(map, null, 4));
    }
    @Test
    public void testGetProfessionalInfo() {
        Assert.notNull(gkProfessionalService.getProfessionalInfo("32798"));
    }
    @Test
    public void testGetProfessionCategory() {
        Map<String,Object> map = Maps.newHashMap();
        Assert.notNull(gkProfessionalService.getProfessionCategory(map));
    }


}
