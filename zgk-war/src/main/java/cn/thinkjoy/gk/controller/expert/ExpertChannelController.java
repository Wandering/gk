package cn.thinkjoy.gk.controller.expert;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.common.Constants;
import cn.thinkjoy.gk.common.RandomCodeUtil;
import cn.thinkjoy.gk.entity.ExpertChannel;
import cn.thinkjoy.gk.entity.ExpertChannelRequest;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.ModelUtil;
import cn.thinkjoy.gk.service.IExpertService;
import cn.thinkjoy.gk.util.DateUtil;
import cn.thinkjoy.gk.util.MessageDigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by yangguorong on 16/11/22.
 *
 * 专家频道相关接口
 */
@Controller
@RequestMapping("/expertChannel")
public class ExpertChannelController {

    private static final Logger logger = Logger.getLogger(ExpertChannelController.class);

    @Autowired
    private IExpertService expertService;

    @ResponseBody
    @ApiDesc(value = "创建频道",owner = "杨国荣")
    @RequestMapping(value = "/createChannel",method = RequestMethod.POST)
    public ExpertChannel createChannel(ExpertChannelRequest request) throws IOException {

        String expertId = request.getExpertId();
        String stuId = request.getStuId();
        int type = request.getType();

        if(StringUtils.isBlank(expertId) || StringUtils.isBlank(stuId)){
            ModelUtil.throwException(ERRORCODE.PARAM_ERROR);
            logger.error("请求参数错误: expertId = "+expertId+" ,stuId = "+stuId);
        }

        ExpertChannel tmpChannel = expertService.getChannelByexpertIdAndStuId(
                Long.valueOf(expertId),
                Long.valueOf(stuId),
                type
        );

        if(tmpChannel != null){
            return tmpChannel;
        }

        // 构造频道名称
        String name = expertId + "_" + stuId;
        if(type == 0){ //
            name += "_expert";
        }else if(type == 1){
            name += "_stu";
        }else {
            ModelUtil.throwException(ERRORCODE.PARAM_ERROR);
            logger.error("请求参数错误: type = "+type);
        }

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Constants.CREATE_CHANNEL_URL;
        HttpPost httpPost = new HttpPost(url);

        String appKey = Constants.APP_KEY;
        String appSecret = Constants.APP_SERCERT;
        String nonce = RandomCodeUtil.generateCharCode(6);
        String curTime = DateUtil.DateToString(new Date(),DateUtil.YYYYMMDDHHMMSS);
        String checkSum = MessageDigestUtil.getCheckSum(appSecret, nonce ,curTime);//鉴权

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        // 设置请求的参数
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("name",name+"_"+curTime);
        paramMap.put("type",0);

        StringEntity params = new StringEntity(JSON.toJSONString(paramMap), Consts.UTF_8);
        httpPost.setEntity(params);

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        Map<String,Object> retMap = JSONObject.parseObject(EntityUtils.toString(response.getEntity()),Map.class);
        ExpertChannel channel = null;
        if("200".equals(retMap.get("code").toString())){
            channel = JSONObject.parseObject(retMap.get("ret").toString(),ExpertChannel.class);
        }else {
            logger.error("创建频道失败,原因:"+retMap.get("msg"));
            ModelUtil.throwException(ERRORCODE.CREATE_CHANNEL_FAIL);
        }

        channel.setCreateDate(System.currentTimeMillis());
        channel.setModifyDate(System.currentTimeMillis());
        channel.setStatus(0);
        channel.setStuId(Long.valueOf(stuId));
        channel.setExpertId(Long.valueOf(expertId));
        channel.setType(type);

        expertService.insertChannel(channel);

        return channel;
    }

    @ResponseBody
    @ApiDesc(value = "获取频道相关信息",owner = "杨国荣")
    @RequestMapping(value = "/getChannel",method = RequestMethod.GET)
    public ExpertChannel getChannel(@RequestParam("expertId") long expertId,
                                    @RequestParam("stuId") long stuId,
                                    @RequestParam("type") int type){

        return expertService.getChannelByexpertIdAndStuId(expertId,stuId,type);
    }

    @ResponseBody
    @ApiDesc(value = "删除频道",owner = "杨国荣")
    @RequestMapping(value = "/deleteChannel",method = RequestMethod.GET)
    public Map<String,String> deleteChannel(@RequestParam("creatorId") long creatorId,
                                    @RequestParam("cid") String cid) throws IOException {

        ExpertChannel channel = expertService.getChannelByCid(cid);
        if(channel == null){
            ModelUtil.throwException(ERRORCODE.PARAM_ERROR);
            logger.error("请求参数错误: cid = "+cid);
        }

        if(channel.getType() == 0 && channel.getExpertId() != creatorId){
            ModelUtil.throwException(ERRORCODE.DELETE_CHANNEL_FAIL);
        }

        if(channel.getType() == 1 && channel.getStuId() != creatorId){
            ModelUtil.throwException(ERRORCODE.DELETE_CHANNEL_FAIL);
        }

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Constants.DELETE_CHANNEL_URL;
        HttpPost httpPost = new HttpPost(url);

        String appKey = Constants.APP_KEY;
        String appSecret = Constants.APP_SERCERT;
        String nonce = RandomCodeUtil.generateCharCode(6);
        String curTime = DateUtil.DateToString(new Date(),DateUtil.YYYYMMDDHHMMSS);
        String checkSum = MessageDigestUtil.getCheckSum(appSecret, nonce ,curTime);//鉴权

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        // 设置请求的参数
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("cid",channel.getCid());

        StringEntity params = new StringEntity(JSON.toJSONString(paramMap), Consts.UTF_8);
        httpPost.setEntity(params);

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        Map<String,Object> retMap = JSONObject.parseObject(EntityUtils.toString(response.getEntity()),Map.class);
        if(!"200".equals(retMap.get("code").toString())){
            logger.error("删除频道失败,原因:"+retMap.get("msg"));
            ModelUtil.throwException(ERRORCODE.DELETE_CHANNEL_ERROR);
        }

        expertService.updateChannelByCid(cid);

        return Maps.newHashMap();
    }
}
