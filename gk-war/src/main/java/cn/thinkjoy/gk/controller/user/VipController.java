package cn.thinkjoy.gk.controller.user;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
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

@Scope("prototype")
@Controller("VipController")
@RequestMapping(value = "/vip")
public class VipController extends BaseController {

	private static final Logger LOGGER= LoggerFactory.getLogger(VipController.class);
	@Autowired
	private IUserVipService vipService;
	@Autowired
	private ICardService cardService;
	@RequestMapping(value = "/upgradeVipByCard",method = RequestMethod.POST)
	public String upgradeVipByCard(CardPojo cardPojo) {
		UserAccountPojo userAccountPojo=super.getUserAccountPojo();
		if(null==userAccountPojo ||  null==userAccountPojo.getId()){
			throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
		}

		Map<String,Object> map=new HashMap<String, Object>();
		map.put("cardNumber",cardPojo.getCardNumber());
		map.put("password",cardPojo.getPassword());
		Card card=(Card)cardService.queryOne(map);
		if(null==card ){
			throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
		}else if(0!=card.getUserId()){
			throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
		}else{
			card.setUserId(userAccountPojo.getId());
			cardService.update(card);
			UserVip userVip=new UserVip();
			userVip.setId(userAccountPojo.getId());
			userVip.setEndDate(card.getEndDate());
			vipService.insert(userVip);
			return  "success";
		}
	}
	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "/getAgent",method = RequestMethod.GET)
	@ResponseBody
	public String chargeAccount() {

		UserAccountPojo userAccountPojo=super.getUserAccountPojo();
		if(null==userAccountPojo ||  null==userAccountPojo.getId()){
			throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
		}
		return  userAccountPojo.getAccount();
	}

}
