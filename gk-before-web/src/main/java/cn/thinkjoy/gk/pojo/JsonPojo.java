package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/28.
 */
public class JsonPojo implements Serializable{
    private ResultDataPojo  result;

    public ResultDataPojo getResult() {
        return result;
    }

    public void setResult(ResultDataPojo result) {
        this.result = result;
    }
}
