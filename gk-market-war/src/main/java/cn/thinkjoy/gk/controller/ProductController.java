package cn.thinkjoy.gk.controller;

/**
 * Created by clei on 15/5/4.
 */


import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.query.ProductQuery;
import cn.thinkjoy.gk.domain.Product;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IProductExService;
import cn.thinkjoy.gk.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value="/product")
public class ProductController extends BaseController{

    private static final Logger LOGGER= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductExService productExService;

    @Autowired
    private IProductService productService;

    /**
     * 获取商品
     * @return
     */
    @RequestMapping(value = "findProductPage", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> findProductPage(ProductQuery productQuery) {

//        response.setHeader("Access-Control-Allow-Origin","http://test.zhiless.com:8088");
//        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "1800");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//        response.addHeader("Access-Control-Allow-Credentials", "true");

        if(productQuery==null) {
            LOGGER.info("====product /findProductPage PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        List<Product> products = productExService.findProductList(productQuery.getType());

        return products;
    }

    /**
     * 获取商品详情
     * @return
     */
    @RequestMapping(value = "findProduct", method = RequestMethod.GET)
    @ResponseBody
    public Product findProduct(@RequestParam(value="code",required=false) Integer code) {

        if(code==null) {
            LOGGER.info("====product /payType AUTHENTICATION_FAIL ");
            throw new BizException(ERRORCODE.AUTHENTICATION_FAIL.getCode(), ERRORCODE.AUTHENTICATION_FAIL.getMessage());
        }

        response.addHeader("Access-Control-Allow-Origin","*");

        response.addHeader("Access-Control-Allow-Headers","X-Requested-With");

        response.addHeader("Access-Control-Allow-Headers","GET,POST,OPTIONS");

        Product product = (Product)productService.findOne("code",code);

        return product;
    }

}
