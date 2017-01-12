package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.*;
import cn.thinkjoy.gk.common.observer.Watched;
import cn.thinkjoy.gk.common.observer.Watcher;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.constant.VipConst;
import cn.thinkjoy.gk.domain.Card;
import cn.thinkjoy.gk.domain.UserVip;
import cn.thinkjoy.gk.pojo.CardPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.ModelUtil;
import cn.thinkjoy.gk.service.ICardExService;
import cn.thinkjoy.gk.service.ICardService;
import cn.thinkjoy.gk.service.IUserVipService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/vip")
public class VipController extends ZGKBaseController implements Watched {

    private static Logger logger=LoggerFactory.getLogger(VipController.class);

    @Autowired
    private Watcher watcher;
    @Autowired
    private ICardExService cardExService;
    @Autowired
    private ICardService cardService;
    @Autowired
    private IUserVipService userVipService;

    private static RedisDisLock redisDisLock = new RedisDisLock(RedisUtil.getInstance().getRedisTemplate());

    private static Comparator comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return getLongNum(o1).compareTo(getLongNum(o2));
        }

        private String getLongNum(Object o) {
            if (o == null) {
                return "";
            }
            return VipTimeUtil.getLastActiveDate(Long.valueOf(((Map<String, Object>) o).get("activeDate").toString()));
        }
    };

    //高考学堂注册接口
    private String gkxtActiveUrl = "http://xuetang.zhigaokao.cn/userapi/tovip?mobile=%s&duration=12&unit=month&levelId=2";

    @RequestMapping(value = "/upgradeVipByCard")
    @ResponseBody
    public UserAccountPojo upgradeVipByCard(CardPojo cardPojo){
        UserAccountPojo userAccountPojo=super.getUserAccountPojo();
        if(null==userAccountPojo){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        String redisLockKey = VipConst.VIP_REDIS_LOCK_KEY+userAccountPojo.getAccount();
        try {
            //分布式锁
            redisDisLock.lock(redisLockKey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        RedisUtil.getInstance().
//        Integer vipStatus = userAccountPojo.getVipStatus();
//        if(null!=vipStatus&&vipStatus==1){
//            throw new BizException(ERRORCODE.VIP_EXIST.getCode(), ERRORCODE.VIP_EXIST.getMessage());
//        }
        Map<String,String> map=new HashMap<>();
        map.put("cardNumber",cardPojo.getCardNumber());
        Card card= null;
        try {
            card = cardExService.getVipCardInfo(map);
        }catch (MyBatisSystemException e){
            throw new BizException("error","卡号异常");
        }

        if(null == card){
            ModelUtil.throwException(ERRORCODE.VIP_CARD_NOT_INVALID);
        }
        if(card.getStatus() == 1){
            ModelUtil.throwException(ERRORCODE.VIP_CARD_USED);
        }

        if(!card.getPassword().equals(cardPojo.getPassword())){
            ModelUtil.throwException(ERRORCODE.VIP_CARD_NOT_INVALID);
        }
        //状元及第会员卡激活高考学堂
        boolean gkxtActiveStatus = false;
        Integer productType = card.getProductType();
        //TODO 写死绑卡流程,金榜题名和金榜登科不绑定智学堂
        if(productType!=1 && productType!=3)
        {
            String account = getUserAccountPojo().getAccount();
            gkxtActiveUrl = String.format(gkxtActiveUrl, account);
            String result = HttpClientUtil.getContents(gkxtActiveUrl);
            if(result.contains("\"ret\":\"200\""))
            {
                logger.debug("帐号"+account+"激活高考学堂会员成功!");
                gkxtActiveStatus =true;
            }else
            {
                logger.debug("帐号"+account+"激活高考学堂会员失败!");
            }
        }
        try {
            Calendar c = getVipEndDate(card.getCardType());
            card.setEndDate(c.getTimeInMillis());
            cardExService.updateUserVip(card.getId(),userAccountPojo.getId(),card.getEndDate(), gkxtActiveStatus);
            userAccountPojo.setVipStatus(1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userAccountPojo.setVipActiveDate(format.format(new Date(System.currentTimeMillis())));
            userAccountPojo.setVipEndDate(format.format(new Date(card.getEndDate())));
            String token = DESUtil.getEightByteMultypleStr(String.valueOf(userAccountPojo.getId()), userAccountPojo.getAccount());
            setUserAccountPojo(userAccountPojo, DESUtil.encrypt(token, DESUtil.key));
        } catch(Exception e) {
            ModelUtil.throwException(ERRORCODE.VIP_UPGRADE_FAIL);
        }
        try {
            //该给用户绑定专家服务状态了

            cardExService.bindUserExportService(userAccountPojo.getId(),card,this.getAreaId());
        }catch (Exception e){
            ModelUtil.throwException(ERRORCODE.VIP_UPGRADE_FAIL);
        }
        /**
         * 当所有操作执行完成之后通知该更新代理商后台了
         */
        Map<String,Object> notify=new HashMap();
        notify.put("cardNumber",card.getCardNumber());
        notify.put("userId",userAccountPojo.getId());
        try {
            notifyWatchers(notify);
        }catch (Exception e){
            logger.info("卡号异常防窜货代理商系统调用失败！",e);
        }
        //解锁  只针对相同用户
        redisDisLock.unlock(redisLockKey);
        return userAccountPojo;
    }

    private Calendar getVipEndDate(String cardType) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        /**
         * 体验帐号激活1个月后失效,其他帐号失效日期为激活年份往后推一年的九月一号
         */
        if("4".equals(cardType))
        {
            c.add(Calendar.MONTH, 1);
        }
        else
        {
            if (VipTimeUtil.getVipTag()) {
                c.add(Calendar.YEAR, 2);
            }else {
                c.add(Calendar.YEAR, 3);
            }
            c.set(Calendar.MONTH, 8);
            c.set(Calendar.DAY_OF_MONTH, 1);
        }
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND,0);
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

    /**
     *
     * @return
     */
    @RequestMapping(value = "/hasExpertVip",method = RequestMethod.GET)
    @ResponseBody
    public Object hasExpertVip() {
        return  hasExpertPrem();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/hasExpertVipByExpert",method = RequestMethod.GET)
    @ResponseBody
    public Object hasExpertVip(@RequestParam Integer expertId) {
        hasExpertPrem();
        //获取专家所有服务类型
        List<Integer> expertService = cardExService.getServiceByExpertId(expertId);
        //获取用户所拥有的服务类型
        //判断该用户是否具有该专家的服务
        Long userId = getUserAccountPojo().getId();
        List<Integer> userService =  cardExService.getServiceByUserId(userId);
        //判断该用户是否已经预定该专家的服务
//        if (userService==null){
//            throw new BizException(ERRORCODE.NO_EXPERT_SERVICE.getCode(), ERRORCODE.NO_EXPERT_SERVICE.getMessage());
//        }
//        userService.retainAll(expertService);
//        if (userService.size()==0){
//            throw new BizException(ERRORCODE.NO_EXPERT_SERVICE.getCode(), ERRORCODE.NO_EXPERT_SERVICE.getMessage());
//        }
//        else {
//            Integer count = cardExService.getServiceByUserIdAndExpertId(userId,expertId);
//            if (count>0){
//                throw new BizException(ERRORCODE.YES_EXPERT_SERVICE.getCode(), ERRORCODE.YES_EXPERT_SERVICE.getMessage());
//            }
//        }
        return "true";
    }


    private Object hasExpertPrem(){
        UserAccountPojo userAccountPojo=super.getUserAccountPojo();
        if(null==userAccountPojo ||  null==userAccountPojo.getId()){
            throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
        }
        Long userId = userAccountPojo.getId();

        if(1!=userAccountPojo.getVipStatus()){
            throw new BizException(ERRORCODE.NO_VIP.getCode(), ERRORCODE.NO_VIP.getMessage());
        }
        //在特权表中查找该用户所有的特权

        Integer count = cardExService.countUserServiceByUserId(userId);

        //当为null时候
        if(null==count){
            throw new BizException(ERRORCODE.EXPERT_VIP_UN_EXIST.getCode(), ERRORCODE.EXPERT_VIP_UN_EXIST.getMessage());
        }
        //当为0的时候
        if(0==count){
            throw new BizException(ERRORCODE.EXPERT_VIP_ZERO.getCode(), ERRORCODE.EXPERT_VIP_ZERO.getMessage());
        }
        return "true";
    }

    // 存放观察者
    private List<Watcher> list = new ArrayList<Watcher>();

    @Override
    public void addWatcher(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatchers(Map<String,Object> map) {
        // 自动调用实际上是主题进行调用的
        for (Watcher watcher : list)
        {
            watcher.update(map);
        }
    }
    @PostConstruct
    public void init(){
        addWatcher(watcher);
    }
    @RequestMapping(value = "/getUserVipInfo")
    @ResponseBody
    public Object getUserVipInfo(){
        String userId = this.getAccoutId();
        Map<String,Object> rtnMap = new HashedMap();
        //获取卡的专家状态
        List<Map<String,Object>> vipServiceNames = cardExService.getUserVipServiceName(userId);
        Collections.sort(vipServiceNames,comparator);
        if (vipServiceNames.size()>0) {
            rtnMap.put("diffServiceName", "状元及第");
            //卡到期时间
            Map<String, Object> userParamMap = Maps.newHashMap();
            userParamMap.put("userId", userId);
            UserVip userVip = (UserVip) userVipService.queryOne(userParamMap);

            rtnMap.put("cardTime", VipTimeUtil.format.format(new Date(userVip.getEndDate())));

            /**
             * 判断最大的是不是金榜登科 并且绑了多张卡
             */



            List<Map<String,Object>> delMaps = new ArrayList<>();
            for (Map<String,Object> map : vipServiceNames){
                Integer ptType = (Integer) map.get("type");
                Long activeDate = (Long) map.get("activeDate");
                try {
                    if (ptType <=2 && System.currentTimeMillis()>VipTimeUtil.getCardTimeOut(activeDate)){
                        delMaps.add(map);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            vipServiceNames.removeAll(delMaps);
            if (vipServiceNames.size()>0) {

                //取最后一张卡
                Map<String, Object> lastCard = vipServiceNames.get(vipServiceNames.size() > 1 ? vipServiceNames.size() - 1 : 0);
                //判断用户绑定的卡有没有金榜登科
                if (vipServiceNames.size() > 0) {
                    for (Map<String, Object> map : vipServiceNames) {
                        if (map.get("type") == 1) {
                            rtnMap.put("diffServiceName", "金榜登科");
                        }
                    }
                }
                if (Integer.valueOf(lastCard.get("type").toString()) == 1 && vipServiceNames.size() > 1) {
                    Map<String, Object> serviceCard = vipServiceNames.get(vipServiceNames.size() - 2);
                    Map<String, Object> paramMap = new HashedMap();
                    paramMap.put("productId", lastCard.get("type"));
                    paramMap.put("areaId", getAreaId());
                    paramMap.put("status", UserVipConstant.DEFULT_STATUS);
                    //是金榜登科的话取得卡status为1的所有
                    List<Map<String, Object>> cardServices1 = cardExService.getCardService(paramMap);
                    if (cardServices1.size() == 0) {
                        paramMap.put("areaId", UserVipConstant.DEFULT_AREA_ID);
                        cardServices1 = cardExService.getCardService(paramMap);
                    }
                    paramMap = new HashedMap();
                    paramMap.put("productId", serviceCard.get("type"));
                    paramMap.put("areaId", getAreaId());
                    paramMap.put("status", UserVipConstant.DEFULT_STATUS);
                    //是金榜登科的话取得卡status为1的所有
                    List<Map<String, Object>> cardServices = cardExService.getCardService(paramMap);
                    if (cardServices.size() == 0) {
                        paramMap.put("areaId", UserVipConstant.DEFULT_AREA_ID);
                        cardServices = cardExService.getCardService(paramMap);
                    }
                    //取两张卡不重叠项
                    cardServices.removeAll(cardServices1);
                    StringBuffer buffer = new StringBuffer();
                    if (cardServices.size() > 0) {
                        for (Map<String, Object> map : cardServices)
                            buffer.append(map.get("serviceType")).append("、");
                        if (buffer.length() > 0)
                            buffer.delete(buffer.length() - 1, buffer.length());
                        rtnMap.put("diffService", buffer.toString());
                        rtnMap.put("diffServiceTime", VipTimeUtil.getLastActiveDate(Long.valueOf(serviceCard.get("activeDate").toString())));
                    }
                }

                if (vipServiceNames.size() > 0) {
                    StringBuffer buffer = new StringBuffer();
                    for (Map<String, Object> map : vipServiceNames)
                        buffer.append(map.get("productName")).append("、");
                    if (buffer.length() > 0)
                        buffer.delete(buffer.length() - 1, buffer.length());
                    rtnMap.put("cardNames", buffer.toString());

                }
            }
                List<Map<String, Object>> vipServices = cardExService.getUserVipService(userId);
                //判断所拥有的VIP卡类型(另一个维度)

                rtnMap.put("expertService", new ArrayList<>());

                //不是专家卡
                //end
                int count = 0;
                for (Map<String, Object> map : vipServices) {
                    count += Integer.valueOf(map.get("count").toString());
                }
                if (vipServiceNames.size()>0){

                }
                rtnMap.put("cardType", count > 0 ? UserVipConstant.EXPERT_VIP_STATUS : UserVipConstant.DEFULT_VIP_STATUS);
                //获取卡的专家状态
                if (vipServices.size() > 0 && count > 0) {
                    //是专家
                    //统计该用户专家卡所拥有的服务和次数
                    rtnMap.put("expertService", vipServices);
                    //end
                }
                if (count == 0 && vipServiceNames.size() == 0) {
                    throw new BizException(ERRORCODE.NO_VIP.getCode(), ERRORCODE.NO_VIP.getMessage());
                }
        }
        return rtnMap;
    }


}
