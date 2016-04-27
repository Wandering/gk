package cn.thinkjoy.gk.common;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by liusven on 16/4/27.
 */
public class HttpClientUtil {


    public static String getContents(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        try {
            HttpRequestBase httpget = new HttpGet(url);
            HttpParams params = new BasicHttpParams();
            if(null != paramMap && !paramMap.isEmpty())
            {
                Set<Map.Entry<String,Object>> paramEntry = paramMap.entrySet();
                for (Map.Entry<String, Object> entry: paramEntry) {
                    params.setParameter(entry.getKey(), entry.getValue());
                }
            }
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getContents(String url)
    {
        return getContents(url,null);
    }
}
