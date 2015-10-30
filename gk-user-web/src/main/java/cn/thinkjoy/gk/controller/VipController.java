package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.pojo.CardPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.ICardExService;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/vip")
public class VipController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(VipController.class);
    @Autowired
    private ICardService cardService;
    @Autowired
    private ICardExService cardExService;

    @RequestMapping(value = "/upgradeVipByCard",method = RequestMethod.POST)
    @ResponseBody
    public String upgradeVipByCard(CardPojo cardPojo) throws Exception{
        UserAccountPojo userAccountPojo=super.getUserAccountPojo();
        if(null==userAccountPojo){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();
        if(null!=vipStatus&&vipStatus==1){
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
        }
        if(card.getUserId()!=0){
            throw new BizException(ERRORCODE.VIP_CARD_NOT_INVALID.getCode(), ERRORCODE.VIP_CARD_NOT_INVALID.getMessage());
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.YEAR, 1);
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DAY_OF_MONTH, 1);
        if(card.getEndDate()<=c.getTimeInMillis()){
            throw new BizException(ERRORCODE.VIP_CARD_NOT_INVALID.getCode(), ERRORCODE.VIP_CARD_NOT_INVALID.getMessage());
        }
        cardExService.updateUserVip(card.getId(),userAccountPojo.getId());
        userAccountPojo.setVipStatus(1);
        try {
            setUserAccountPojo(userAccountPojo);
        }catch(Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }

//        新增卡号的时候添加
//        Calendar c1 = Calendar.getInstance();
//        c1.setTimeInMillis(System.currentTimeMillis());
//        c1.add(Calendar.YEAR, 1);
//        c1.set(Calendar.MONTH, 8);
//        c1.set(Calendar.DAY_OF_MONTH, 1);

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
