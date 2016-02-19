package cn.thinkjoy.gk.common;

import cn.thinkjoy.zgk.cloudstack.BaseWhiteList;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by admin on 2016/2/4.
 */
@Component("BaseWhiteList")
public class WhiteList extends BaseWhiteList {
    @PostConstruct
    public void init() {
//        INTERFACEMETHODS.add("getGkVideoList");
        INTERFACEMETHODS.add("getGkAdmissionLineList");
    }
}
