package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.DESUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.pojo.CardPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.ICardExService;
import cn.thinkjoy.gk.service.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/vip")
public class VipController extends ZGKBaseController {

    @Autowired
    private ICardExService cardExService;

    @RequestMapping(value = "/upgradeVipByCard")
    @ResponseBody
    public UserAccountPojo upgradeVipByCard(CardPojo cardPojo) throws Exception{
        UserAccountPojo userAccountPojo=super.getUserAccountPojo();
        if(null==userAccountPojo){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        Integer vipStatus = userAccountPojo.getVipStatus();
        if(null!=vipStatus&&vipStatus==1){
            throw new BizException(ERRORCODE.VIP_EXIST.getCode(), ERRORCODE.VIP_EXIST.getMessage());
        }
        Map<String,String> map=new HashMap<>();
        map.put("cardNumber",cardPojo.getCardNumber());
        map.put("password",cardPojo.getPassword());
        map.put("status", "0");
        Card card=cardExService.getVipCardInfo(map);
        if(null==card ){
            throw new BizException(ERRORCODE.VIP_CARD_NOT_INVALID.getCode(), ERRORCODE.VIP_CARD_NOT_INVALID.getMessage());
        }
        Calendar c = getVipEndDate(card.getCardType());
        card.setEndDate(c.getTimeInMillis());
        cardExService.updateUserVip(card.getId(),userAccountPojo.getId(),card.getEndDate());
        userAccountPojo.setVipStatus(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userAccountPojo.setVipActiveDate(format.format(new Date(System.currentTimeMillis())));
        userAccountPojo.setVipEndDate(format.format(new Date(card.getEndDate())));
        try {
            String token = DESUtil.getEightByteMultypleStr(String.valueOf(userAccountPojo.getId()), userAccountPojo.getAccount());
            setUserAccountPojo(userAccountPojo, DESUtil.encrypt(token, DESUtil.key));
        } catch(Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        return userAccountPojo;
    }

    private Calendar getVipEndDate(String cardType) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        /**
         * 体验帐号激活1个月后失效,其他帐号失效日期为激活年份往后推三年的九月一号
         */
        if("4".equals(cardType))
        {
            c.add(Calendar.MONTH, 1);
        }
        else
        {
            c.add(Calendar.YEAR, 3);
            c.set(Calendar.MONTH, 8);
            c.set(Calendar.DAY_OF_MONTH, 1);
        }
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c;
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
