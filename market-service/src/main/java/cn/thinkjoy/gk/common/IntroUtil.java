package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.domain.SaleProductService;
import cn.thinkjoy.gk.dto.SaleProductServiceDTO;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yangyongping on 2016/12/20.
 */
public class IntroUtil {
    public static List<SaleProductServiceDTO> getTreeIntro(List<SaleProductService> list)
    {
        Collections.sort(list,new Comparator(){
            @Override

            public int compare(Object o1, Object o2) {

                return (((SaleProductService) o1).getSort()).compareTo(((SaleProductService) o2).getSort());

            }
        });
        List<SaleProductServiceDTO> dtoList = new ArrayList<>();
        SaleProductServiceDTO saleProductServiceDTO = null;
        for(SaleProductService saleProductService:list)
        {
            saleProductServiceDTO = new SaleProductServiceDTO();
            if(saleProductService.getParentId()==0)
            {
                saleProductServiceDTO.setSaleProductService(saleProductService);
                saleProductServiceDTO.setList(getList(list, saleProductService.getId()));
                dtoList.add(saleProductServiceDTO);
            }
        }
        return dtoList;
    }
    private static List<SaleProductServiceDTO> getList(List<SaleProductService> list,int parentId)
    {
        List<SaleProductServiceDTO> dtoList = new ArrayList<>();
        SaleProductServiceDTO saleProductServiceDTO = null;
        for(SaleProductService saleProductService:list)
        {
            saleProductServiceDTO = new SaleProductServiceDTO();
            if(saleProductService.getParentId()==parentId)
            {
                saleProductServiceDTO.setSaleProductService(saleProductService);
                saleProductServiceDTO.setList(getList(list, saleProductService.getId()));
                dtoList.add(saleProductServiceDTO);
            }
        }
        return dtoList;
    }

    public static void main(String[] args) {
        System.out.println("1".compareTo("2"));
    }
}
