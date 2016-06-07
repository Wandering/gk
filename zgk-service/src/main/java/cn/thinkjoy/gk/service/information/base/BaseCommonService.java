package cn.thinkjoy.gk.service.information.base;

import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.domain.BizData4Page;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/1/12.
 */
public class BaseCommonService {

    //部分内容加载开关，默认关闭
    private boolean isIgnore=false;
    public boolean isIgnore() {
        return isIgnore;
    }

    public void setIsIgnore(boolean isIgnore) {
        this.isIgnore = isIgnore;
    }
    /**
     * 生成 BizData4Page 实例
     * @param dao
     * @param conditions
     * @param curPage
     * @param offset
     * @param rows
     * @return
     */
    public BizData4Page createBizData4Page(IBaseService service, Map<String, Object> conditions, int curPage, int offset, int rows){

        String orderBy = null;
        String sortBy = null;
        conditions.put("groupOp","and");
        if (conditions.containsKey("orderBy")) {
            orderBy = conditions.get("orderBy").toString();
        }else {
            orderBy="lastModDate";
        }
        if (conditions.containsKey("sortBy")) {
            sortBy = conditions.get("sortBy").toString();
        }else {
            sortBy="desc";
        }
        enhanceSearchFilter(conditions);
        List mainData = service.queryPage(conditions, offset, rows, orderBy, SqlOrderEnum.valueOf(sortBy.toUpperCase()));
        mainData=(List)enhanceStateTransition(mainData);
        int records = service.count(conditions);

        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(curPage);
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

    protected BizData4Page doPage(Map<String, Object> conditions,IBaseService service,Integer page,Integer rows){
        if(page==null){
            page = 1;
        }
        if(rows==null){
            rows = 10;
        }
        enhanceSortBy(conditions);
        return createBizData4Page(service,conditions, page, (page - 1) * rows, rows);
    }


    /**
     * 增强 或 改变 过滤条件内容
     * @param conditions
     */
    protected void enhanceSearchFilter(Map<String, Object> conditions) {
    }

    /**
     * 增强查询条件
     * @param conditions
     */
    protected void enhanceSortBy(Map<String, Object> conditions) {
    }

    /**
     * domain转换
     * @param conditions
     */
    protected Object enhanceStateTransition(List conditions) {
        return conditions;
    }

}
