package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.pojo.CardPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.ICardService;
import cn.thinkjoy.gk.service.IUserVipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/vip")
public class VipController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(VipController.class);
    @Autowired
    private IUserVipService vipService;
    @Autowired
    private ICardService cardService;

    @RequestMapping(value = "/upgradeVipByCard",method = RequestMethod.POST)
    @ResponseBody
    public String upgradeVipByCard(CardPojo cardPojo) throws Exception{
        UserAccountPojo userAccountPojo=super.getUserAccountPojo();
        if(null==userAccountPojo){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();
        if(userAccountPojo.getVipStatus().intValue()==1){
            throw new BizException(ERRORCODE.VIP_EXIST.getCode(), ERRORCODE.VIP_EXIST.getMessage());

        }
        long areaId=getAreaCookieValue();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("cardNumber",cardPojo.getCardNumber());
        map.put("password",cardPojo.getPassword());
        map.put("areaId",areaId);
        Card card=(Card)cardService.queryOne(map);
        if(null==card ){
            throw new BizException(ERRORCODE.VIP_CARD_NOT_INVALID.getCode(), ERRORCODE.VIP_CARD_NOT_INVALID.getMessage());
        }else if(0!=card.getUserId()){
            throw new BizException(ERRORCODE.VIP_CARD_NOT_INVALID.getCode(), ERRORCODE.VIP_CARD_NOT_INVALID.getMessage());
        }
        card.setUserId(userAccountPojo.getId());
        cardService.update(card);
        UserVip userVip=new UserVip();
        userVip.setId(userAccountPojo.getId());
        userVip.setEndDate(card.getEndDate());
        userVip.setStatus(1);
        vipService.update(userVip);

        userAccountPojo.setVipStatus(1);

        try {
            setUserAccountPojo(userAccountPojo);
        }catch(Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }

        return  "success";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getAccount",method = RequestMethod.GET)
    @ResponseBody
    public UserAccountPojo getChargeAccount() {

        UserAccountPojo userAccountPojo=super.getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        return  userAccountPojo;
    }

}
