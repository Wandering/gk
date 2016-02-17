package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.protocol.ResponseT;
import cn.thinkjoy.common.restful.ExtFastJsonHttpMessageConverter;
import cn.thinkjoy.common.utils.RtnCodeEnum;
import cn.thinkjoy.gk.util.CallbackContext;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by admin on 2016/2/16.
 */
public class FastJsonHttpMessageConverter<T> extends ExtFastJsonHttpMessageConverter<T> {
    private Charset charset  = UTF8;
    private SerializerFeature[] features = new SerializerFeature[0];
    @Override
    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        ResponseT<T> response = new ResponseT<T>(RtnCodeEnum.SUCCESS);
        response.setBizData(t);
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(response, features);
        String result =null;
        //兼容jsonp-start
        if(!StringUtils.isEmpty(CallbackContext.getCallback())) {
            result=CallbackContext.getCallback() + "(" + text + ")";
//            result=text;
        }else {
            result=text;
        }
        //兼容jsonp-end
//        byte[] bytes = text.getBytes(charset);
        byte[] bytes = result.getBytes(charset);
        out.write(bytes);
    }
}
