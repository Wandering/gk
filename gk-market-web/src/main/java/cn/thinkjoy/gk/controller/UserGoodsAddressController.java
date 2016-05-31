/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgk
 * $Id:  UserGoodsAdressController.java 2016-05-24 11:14:24 $
 */

package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.domain.UserGoodsAdress;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserGoodsAdressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value="/userGoodsAddress")
public class UserGoodsAddressController extends ZGKBaseController {
    @Autowired
    private IUserGoodsAdressService userGoodsAdressService;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserGoodsAddressController.class);

    /**
     * 获取用户收货地址
     * @return
     */
    @RequestMapping(value = "getUserGoodsAddress", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserGoodsAddress(
            @RequestParam(value = "token", required = true) String token)
    {
        UserAccountPojo userAccountPojo = getUserAccount();
        List addressList =userGoodsAdressService.findList("userId", userAccountPojo.getId());
        if(null != addressList && addressList.size() > 0)
        {
            return addressList.get(0);
        }
        return new HashMap<>();
    }

    private UserAccountPojo getUserAccount() {
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        if(null == userAccountPojo)
        {
            throw new BizException("100001","token无效或者已过期!");
        }
        return userAccountPojo;
    }

    /**
     * 添加或更新收货地址
     * @return
     */
    @RequestMapping(value = "saveOrUpdateUserGoodsAddress", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveOrupdateUserGoodsAddress(UserGoodsAdress userGoodsAddress,
            @RequestParam(value = "token", required = true) String token)
    {
        boolean result = false;
        UserAccountPojo userAccountPojo = getUserAccount();
        userGoodsAddress.setUserId(userAccountPojo.getId());
        Object obj = userGoodsAdressService.findOne("userId",userAccountPojo.getId());
        if(null == obj)
        {
            UserGoodsAdress address = setDomain(userGoodsAddress, "add",null);
            if(1 == userGoodsAdressService.add(address))
            {
                result = true;
                LOGGER.debug("添加收货地址成功!");
            }
        }
        else{
            UserGoodsAdress address = setDomain(userGoodsAddress, "update",obj);
            if(1 == userGoodsAdressService.update(address))
            {
                result = true;
                LOGGER.debug("更新收货地址成功!");
            }
        }
        return result;
    }

    private UserGoodsAdress setDomain(UserGoodsAdress userGoodsAddress,String type, Object obj) {
        if ("add".equals(type))
        {
            userGoodsAddress.setCreateDate(System.currentTimeMillis());
        }else
        {
            UserGoodsAdress addObj = (UserGoodsAdress) obj;
            userGoodsAddress.setCreateDate(addObj.getCreateDate());
            userGoodsAddress.setId(addObj.getId());
        }
        userGoodsAddress.setUpdateDate(System.currentTimeMillis());
        userGoodsAddress.setStatus("1");
        return userGoodsAddress;
    }
}
