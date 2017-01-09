package cn.thinkjoy.gk.dto;

import cn.thinkjoy.gk.domain.SaleProductService;

import java.util.List;

/**
 * Created by yangyongping on 2016/12/20.
 */
public class SaleProductServiceDTO{

    private SaleProductService saleProductService;
    private List<SaleProductServiceDTO> list;

    public List<SaleProductServiceDTO> getList() {
        return list;
    }

    public void setList(List<SaleProductServiceDTO> list) {
        this.list = list;
    }

    public SaleProductService getSaleProductService() {
        return saleProductService;
    }

    public void setSaleProductService(SaleProductService saleProductService) {
        this.saleProductService = saleProductService;
    }
}
