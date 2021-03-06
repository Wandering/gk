/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserAccountServiceImpl.java 2015-07-13 09:45:17 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.Utils.MatrixToImageWriter;
import cn.thinkjoy.gk.Utils.StaticSource;
import cn.thinkjoy.gk.dao.*;
import cn.thinkjoy.gk.domain.*;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.pojo.UserInfoPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("UserAccountExServiceImpl")
public class UserAccountExServiceImpl implements IUserAccountExService {

    @Autowired
    private IUserAccountExDAO userAccountExDAO;

    @Autowired
    private IUserAccountDAO userAccountDAO;

    @Autowired
    private IUserInfoDAO userInfoDAO;

    @Autowired
    private IUserExamDAO userExamDAO;

    @Autowired
    private IUserInfoExDAO userInfoExDAO;

    @Autowired
    private IUserVipDAO userVipDAO;
    @Autowired
    private IUserMarketDAO userMarketDAO;

    @Override
    public UserAccountPojo findUserAccountPojoByToken(String token) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("token",token);
        return userAccountExDAO.findUserAccountPojo(params);
    }

    @Override
    public UserAccountPojo findUserAccountPojoById(Long id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userAccountExDAO.findUserAccountPojo(params);
    }

    @Override
    public UserAccountPojo findUserAccountPojoByPhone(String account) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        return  userAccountExDAO.findUserAccountPojo(params);

    }

    @Override
    public int findUserAccountCountByPhone(String account,Long areaId) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        params.put("areaId",areaId);
        return userAccountExDAO.findUserAccountCount(params);
    }

    @Override
    public boolean insertUserAccount(UserAccount userAccount, Long sharerId, Integer sharerType) throws WriterException, IOException {
        boolean flag;
        userAccountDAO.insert(userAccount);
        long id = userAccount.getId();
        userAccount.setUserId(id);
        userAccountDAO.update(userAccount);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        String account = userAccount.getAccount();
        if(StringUtils.isNotBlank(userAccount.getNickName()))
        {
            userInfo.setName(userAccount.getNickName());
        }else if (StringUtils.isNotBlank(account))
        {
            userInfo.setName("gk-" + account.substring(0, 3) + "****" + account.substring(account.length() - 4, account.length()));
        }
        if(StringUtils.isNotBlank(userAccount.getAvatar()))
        {
            userInfo.setIcon(userAccount.getAvatar());
        }
        userInfo.setAlipayUserId(account);
        userInfo.setToken(UUID.randomUUID().toString());
        userInfo.setProvinceId(userAccount.getProvinceId());
        userInfo.setCityId(userAccount.getCityId());
        userInfo.setCountyId(userAccount.getCountyId());
        userInfo.setGrade(userAccount.getGrade());
        userInfoExDAO.insertUserInfo(userInfo);
        UserVip userVip = new UserVip();
        userVip.setId(id);
        userVip.setStatus(0);
        userVip.setCreateDate(System.currentTimeMillis());
        userVip.setUserId(id);
        userVipDAO.insert(userVip);
        UserExam userExam = new UserExam();
        userExam.setId(id);
        userExam.setIsReported(0);
        userExam.setIsSurvey(0);
        userExam.setUserId(id);
        userExamDAO.insert(userExam);
        insertUserMarketInfo(sharerId, sharerType, id);
        flag = true;

        return flag;
    }

    @Override
    public boolean insertUserMarketInfo(Long sharerId, Integer sharerType, long id) throws WriterException, IOException {
        boolean flag;
        UserMarket userMarket = new UserMarket();
        userMarket.setAccountId(id);
        userMarket.setUserId(id);
        Integer agentLevel = 0;
        if(sharerType == 0){//供货商
            agentLevel =1;
        }else if(sharerType == 1){//普通用户
            UserMarket userMarket1 = (UserMarket)userMarketDAO.findOne("accountId", sharerId, null, null);
            if(userMarket1 !=null){
                agentLevel = userMarket1.getAgentLevel()+1;
            }
        }
        userMarket.setSharerType(sharerType);
        userMarket.setAgentLevel(agentLevel);
        userMarket.setCreateDate(System.currentTimeMillis());
        userMarket.setCreator(id);
        userMarket.setFromType(1);//微信
        userMarket.setSharerId(sharerId);
        String uploadUrl = StaticSource.getSource("uploadUrl");
        String loginUrl = StaticSource.getSource("loginUrl")+"?sharerId="+id+"&sharerType="+1+"&state=user-detail";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(loginUrl, BarcodeFormat.QR_CODE, 400, 400,hints);
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path=path.substring(1, path.indexOf("classes")).replaceFirst("ile:","");
        String fileName = id+".jpg";
        File file = new File(path,fileName);
        MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(file);
        RestTemplate template = new RestTemplate();
        param.add("file", resource);
        param.add("productCode", "gk360");
        param.add("bizSystem", "gk360");
        param.add("spaceName", "gk360");
        param.add("userId", "gk360");
        param.add("dirId", "0");
        template.getMessageConverters().add(new FastJsonHttpMessageConverter());
        String returnJson = template.postForObject(uploadUrl, param, String.class);
        UploadFileReturn uploadFileReturn = JsonMapper.buildNormalMapper().fromJson(returnJson, UploadFileReturn.class);
        if(uploadFileReturn !=null && "0000000".equals(uploadFileReturn.getRtnCode())){
            userMarket.setQrcodeUrl(uploadFileReturn.getBizData().getFile().getFileUrl());//二维码地址
            file.delete();
        }
        userMarketDAO.insert(userMarket);
        flag = true;

        return flag;
    }

    @Override
    public boolean updateUserAccount(UserAccount userAccount){
        boolean flag = false;
        userAccountDAO.update(userAccount);
        flag = true;
        return flag;
    }

    @Override
    public UserAccount findUserAccountById(long id){
        return userAccountDAO.findOne("id",id,"id", SqlOrderEnum.ASC.getAction());
    }

    @Override
    public UserInfoPojo getUserInfoPojoById(long id){
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        return userAccountExDAO.getUserInfoPojoById(params);
    }

    @Override
    public Map<String, Object> findUserInfo(Map<String, String> paramMap) {
        return userAccountExDAO.findUserInfo(paramMap);
    }

    @Override
    public Department findDepartMent(Map<String, String> paramMap) {
        return userAccountExDAO.findDepartMent(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOrderList(Map<String, String> paramMap) {
        return userAccountExDAO.getOrderList(paramMap);
    }

    @Override
    public int updateUserAccountRegistXueTang(Map<String, Object> paramMap) {
        return userAccountExDAO.updateUserAccountRegistXueTang(paramMap);
    }

    @Override
    public Map<String, Object> findUserInfoByAlipayId(String alipayId)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("alipayUserId",alipayId);
        return userAccountExDAO.findUserInfoByAlipayId(params);
    }

    @Override
    public boolean bindUserAccount(UserAccount userAccount) {
        boolean flag;
        userAccountDAO.update(userAccount);
        flag = true;
        return flag;
    }

    @Override
    public boolean bindUserAccountExist(UserAccountPojo userAccountPojo, String userId, String aliUserId)
    {
        boolean flag;
        userAccountDAO.deleteById(userId);
        userInfoDAO.deleteById(userId);
        userVipDAO.deleteById(userId);
        userExamDAO.deleteById(userId);
        userMarketDAO.deleteById(userId);
        Long existUserId = userAccountPojo.getId();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(existUserId);
        userInfo.setAlipayUserId(aliUserId);
        userInfoExDAO.updateUserAliUserId(userInfo);
        flag = true;
        return flag;
    }

    @Override
    public void updateUserQQUserId(UserAccountPojo userAccountPojo, String userId, String qqUserId) {
        userAccountDAO.deleteById(userId);
        userInfoDAO.deleteById(userId);
        userVipDAO.deleteById(userId);
        userExamDAO.deleteById(userId);
        userMarketDAO.deleteById(userId);
        Long existUserId = userAccountPojo.getId();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(existUserId);
        userInfo.setQqUserId(qqUserId);
        userInfoExDAO.updateUserQQUserId(userInfo);
    }
}
