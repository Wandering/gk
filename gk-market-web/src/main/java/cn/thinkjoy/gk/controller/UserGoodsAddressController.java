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
        List addressList =userGoodsAdressService.findList("userId", getAccoutId());
        if(null != addressList && addressList.size() > 0)
        {
            return addressList.get(0);
        }
        return new HashMap<>();
    }

    /**
     * 添加收货地址
     * @return
     */
    @RequestMapping(value = "addUserGoodsAddress", method = RequestMethod.POST)
    @ResponseBody
    public boolean addUserGoodsAddress(
            @RequestParam(value = "address", required = true) String address,
            @RequestParam(value = "contactPhone", required = true) String contactPhone,
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "contactName", required = true) String contactName)
    {
        boolean result = false;
        UserGoodsAdress userGoodsAdress = setDomain(address, contactPhone, contactName, "add", null);
        if(1 == userGoodsAdressService.insert(userGoodsAdress))
        {
            result = true;
            LOGGER.debug("添加收货地址成功!");
        }
        return result;
    }

    /**
     * 更新收货地址
     * @return
     */
    @RequestMapping(value = "updateUserGoodsAddress", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateUserGoodsAddress(
            @RequestParam(value = "address", required = true) String address,
            @RequestParam(value = "contactPhone", required = true) String contactPhone,
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "contactName", required = true) String contactName)
    {
        boolean result = false;
        Object obj = userGoodsAdressService.findOne("userId",getUserAccountPojo().getId());
        if(null == obj)
        {
            throw new BizException("0000017", "未找到用户收货信息");
        }
        UserGoodsAdress userGoodsAdress = setDomain(address, contactPhone, contactName, "update",obj);
        if(1 == userGoodsAdressService.update(userGoodsAdress))
        {
            result = true;
            LOGGER.debug("更新收货地址成功!");
        }
        return result;
    }

    private UserGoodsAdress setDomain(String address,  String contactPhone, String contactName,String type, Object obj) {
        UserGoodsAdress userGoodsAdress = new UserGoodsAdress();
        UserAccountPojo userAccountPojo = getUserAccountPojo();
        userGoodsAdress.setUserId(userAccountPojo.getId());
        userGoodsAdress.setReceivingAddress(address);
        userGoodsAdress.setContactPhone(contactPhone);
        userGoodsAdress.setContactName(contactName);
        if ("add".equals(type))
        {
            userGoodsAdress.setCreateDate(System.currentTimeMillis());
        }else
        {
            UserGoodsAdress addObj = (UserGoodsAdress) obj;
            userGoodsAdress.setCreateDate(addObj.getCreateDate());
            userGoodsAdress.setId(addObj.getId());
        }
        userGoodsAdress.setUpdateDate(System.currentTimeMillis());
        userGoodsAdress.setStatus("1");
        return userGoodsAdress;
    }
}
