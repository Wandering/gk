package cn.thinkjoy.gk.common;

import cn.thinkjoy.zgk.cloudstack.InterfaceConst;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clei on 15/8/8.
 */
public class InterfaceMethodConst extends InterfaceConst{
    static{
        INTERFACEMETHODS.add("getGkEntryList");
        INTERFACEMETHODS.add("getGkEntryInfo");
        INTERFACEMETHODS.add("getGkHotList");
        INTERFACEMETHODS.add("getGkHotInfo");
        INTERFACEMETHODS.add("getGkPhoneList");
        INTERFACEMETHODS.add("getGkPolicyList");
        INTERFACEMETHODS.add("getGkPolicyInfo");
        INTERFACEMETHODS.add("getGkPolicyInfo");
        INTERFACEMETHODS.add("getGkVideoList");
        INTERFACEMETHODS.add("getGkVideoInfo");
        INTERFACEMETHODS.add("hitInc");
    }
}
