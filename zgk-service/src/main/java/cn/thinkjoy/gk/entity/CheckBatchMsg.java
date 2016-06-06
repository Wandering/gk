package cn.thinkjoy.gk.entity;

import java.io.Serializable;

/**
 * Created by douzy on 16/5/18.
 */
public class CheckBatchMsg implements Serializable {
    /**
     * 是否可选
     */
    private boolean isCheck;

    /**
     * 当前分数匹配的批次
     */
    private String match;

    /**
     * 建议填报的批次  --
     */
    private String suggested;
    /**
     * 提示信息 isCheck:true 为空
     */
    private String msg;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getSuggested() {
        return suggested;
    }

    public void setSuggested(String suggested) {
        this.suggested = suggested;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
