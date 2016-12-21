package cn.thinkjoy.gk.controller;

/**
 * Created by clei on 15/5/4.
 */


import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.IntroUtil;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.constant.VipConst;
import cn.thinkjoy.gk.domain.Product;
import cn.thinkjoy.gk.domain.SaleProductService;
import cn.thinkjoy.gk.dto.SaleProductServiceDTO;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.query.ProductQuery;
import cn.thinkjoy.gk.service.IExpertProductServiceService;
import cn.thinkjoy.gk.service.IProductExService;
import cn.thinkjoy.gk.service.IProductService;
import cn.thinkjoy.gk.service.ISaleProductServiceService;
import cn.thinkjoy.gk.service.ex.IExpertProductServiceExService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.domain.DepartmentProductRelation;
import cn.thinkjoy.zgk.zgksystem.pojo.DepartmentProductRelationPojo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 商品
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/product")
public class ProductController extends ZGKBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductExService productExService;

    @Autowired
    private IProductService productService;

    @Autowired
    private DeparmentApiService deparmentApiService;
    @Autowired
    private ISaleProductServiceService saleProductService;

    private static RedisRepository instance = RedisUtil.getInstance();


    @Autowired
    private IExpertProductServiceExService expertProductServiceExService;

    /**
     * 获取商品
     *
     * @return
     */
    @RequestMapping(value = "findProductPage", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public List<Product> findProductPage(ProductQuery productQuery) {

        if (productQuery == null) {
            LOGGER.info("====product /findProductPage PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        List<Product> products = productExService.findProductList(productQuery.getType());

        return products;
    }

    /**
     * 获取商品详情
     *
     * @return
     */
    @RequestMapping(value = "findProduct", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public Product findProduct(@RequestParam(value = "code", required = false) Integer code) {

        if (code == null) {
            LOGGER.info("====product /payType AUTHENTICATION_FAIL ");
            throw new BizException(ERRORCODE.AUTHENTICATION_FAIL.getCode(), ERRORCODE.AUTHENTICATION_FAIL.getMessage());
        }

        Product product = (Product) productService.findOne("code", code);

        return product;
    }

    /**
     * 获取商品列表
     *
     * @return
     */
    @RequestMapping(value = "findAllProduct", method = RequestMethod.GET)
    @ResponseBody
    public List<DepartmentProductRelationPojo> findAllProduct() {
        List<DepartmentProductRelationPojo> relations = null;
        try {
            relations = deparmentApiService.queryProductPriceByAreaId(getAreaId().toString());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return relations;
    }

    /**
     * 获取商品列表
     *
     * @return
     */
    @RequestMapping(value = "queryCardServiceByProductId", method = RequestMethod.GET)
    @ResponseBody
    public Object queryCardServiceByProductId(@RequestParam Integer productId) {
        Map<String, Object> paramMap = new HashedMap();
        Map<String, Object> rtnMap = new HashedMap();
        Long areaId = this.getAreaId();
        paramMap.put("areaId",areaId);
        paramMap.put("productType",productId);
        List<SaleProductService> list = saleProductService.queryList(paramMap,"id","asc");
        rtnMap.put("cardServiceInfo",list.size()==0?"":IntroUtil.getTreeIntro(list));

        DepartmentProductRelationPojo departmentProductRelationPojo = null;
        try {
            departmentProductRelationPojo = deparmentApiService.queryProductPriceByAreaId(areaId.toString(), productId);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        rtnMap.put("cardInfo", departmentProductRelationPojo);
        return rtnMap;
    }


    /**
     * 获取商品列表
     *
     * @return
     */
    @RequestMapping(value = "queryCardInfoByProductId", method = RequestMethod.GET)
    @ResponseBody
    public Object queryCardInfoByProductId(@RequestParam Integer productId, @RequestParam String userKey, @RequestParam(required = false) String more) {
        Map<String, Object> resultMap = new HashedMap();
        Long areaId = this.getAreaId();
        Boolean isJoin = false;
        if (more != null) {
            isJoin = true;
        }
        DepartmentProductRelationPojo departmentProductRelationPojo = null;
        try {
            departmentProductRelationPojo = deparmentApiService.queryProductPriceByAreaId(areaId.toString(), productId);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashedMap();
//        productId,areaId,isJoin
        map.put("productId", productId);
        map.put("areaId", areaId);
        map.put("isJoin", isJoin);

        List<Map<String, Object>> cardInfos = expertProductServiceExService.getCardServiceByProductId(map);
        if (cardInfos == null || cardInfos.size() == 0) {
            map.put("areaId", 0);
            cardInfos = expertProductServiceExService.getCardServiceByProductId(map);
        }
        resultMap.put("cardServiceInfo", cardInfos);
        if (more == null) {
            resultMap.put("cardInfo", departmentProductRelationPojo);
        }
        return resultMap;
    }

}
