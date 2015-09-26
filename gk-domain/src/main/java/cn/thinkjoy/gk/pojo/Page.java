package cn.thinkjoy.gk.pojo;

import java.util.List;

/**
 * Created by thinkjoy on 15/9/26.
 */
public class Page<T> {
    private Integer count;
    private List<T> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
