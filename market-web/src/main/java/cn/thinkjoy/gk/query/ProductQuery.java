package cn.thinkjoy.gk.query;



import cn.thinkjoy.gk.protocol.PageQuery;

import java.io.Serializable;

/**
 * Created by clei on 15/9/7.
 */
public class ProductQuery extends PageQuery implements Serializable{

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
