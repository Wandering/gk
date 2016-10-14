package cn.thinkjoy.gk.common;

import cn.thinkjoy.zgk.domain.BizData4Page;

import java.util.List;

public class ZGKBaseController extends BaseCommonController{

    protected BizData4Page doPage(List mainData,Integer records,  Integer page, Integer rows){
        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(page);
        bizData4Page.setRecords(records);
        int total = records / rows;
        int mod = records % rows;
        int pagesize=0;
        if(mainData!=null) {
            pagesize = mainData.size();
        }
        if(mod > 0){
            total = total + 1;
        }
        bizData4Page.setPagesize(pagesize);
        bizData4Page.setTotal(total);

        return bizData4Page;
    }


}
