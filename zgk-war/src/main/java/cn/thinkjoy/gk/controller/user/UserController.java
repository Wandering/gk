package cn.thinkjoy.gk.controller.user;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUserVipExService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 16/2/18.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value="/user")
public class UserController extends ZGKBaseController{

    @Autowired
    private IUserVipExService userVipExService;

    private HSSFCellStyle style;

    /**
     *
     * @param areaCode
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getVipCardInfo",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getVipCardInfo(
            @RequestParam(value="areaCode",required=false)String areaCode) throws IOException {
        if(StringUtils.isEmpty(areaCode))
        {
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), "请输入省市区代码!");
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("areaCode", areaCode);
        List<Map<String, String>> list = userVipExService.getVipInfoListByArea(paramMap);
        HSSFWorkbook wb = createSheet();
        HSSFSheet sheet = wb.getSheet("VIP卡号信息");
        for (int i = 0; i < list.size(); i++) {
            setSheetValue(list, sheet, i);
        }
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(new Date());
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("vipCard"+ time + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
        return null;
    }

    private void setSheetValue(List<Map<String, String>> list, HSSFSheet sheet, int i) {
        Map<String, String> map = list.get(i);
        HSSFRow row = sheet.createRow(i +1);
        HSSFCell cellAccount = row.createCell(0);
        cellAccount.setCellStyle(style);
        cellAccount.setCellValue(map.get("account"));
        HSSFCell cellUserName = row.createCell(1);
        cellUserName.setCellStyle(style);
        cellUserName.setCellValue(map.get("userName"));
        HSSFCell cellCardNumber = row.createCell(2);
        cellCardNumber.setCellStyle(style);
        cellCardNumber.setCellValue(map.get("cardNumber"));
        HSSFCell cellProvinceName = row.createCell(3);
        cellProvinceName.setCellStyle(style);
        cellProvinceName.setCellValue(map.get("provinceName"));
        HSSFCell cellCityName = row.createCell(4);
        cellCityName.setCellStyle(style);
        cellCityName.setCellValue(map.get("cityName"));
        HSSFCell cellCountyName = row.createCell(5);
        cellCountyName.setCellStyle(style);
        cellCountyName.setCellValue(map.get("countyName"));
    }

    private HSSFWorkbook createSheet() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("VIP卡号信息");
        style = createFontStyle(wb);
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell1 = row0.createCell(1);
        HSSFCell cell2 = row0.createCell(2);
        HSSFCell cell3 = row0.createCell(3);
        HSSFCell cell4 = row0.createCell(4);
        HSSFCell cell5 = row0.createCell(5);
        cell0.setCellStyle(style);
        cell0.setCellValue("用户帐号");
        cell1.setCellStyle(style);
        cell1.setCellValue("用户名称");
        cell2.setCellStyle(style);
        cell2.setCellValue("卡号");
        cell3.setCellStyle(style);
        cell3.setCellValue("省份");
        cell4.setCellStyle(style);
        cell4.setCellValue("城市");
        cell5.setCellStyle(style);
        cell5.setCellValue("区域");
        sheet.setColumnWidth(0, 256 * 25);
        sheet.setColumnWidth(1, 256 * 25);
        sheet.setColumnWidth(2, 256 * 25);
        sheet.setColumnWidth(3, 256 * 25);
        sheet.setColumnWidth(4, 256 * 25);
        sheet.setColumnWidth(5, 256 * 25);
        return wb;
    }

    private static HSSFCellStyle createFontStyle(HSSFWorkbook wb) {
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.RED.index);
        font.setFontHeightInPoints((short)18);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        return style;
    }

}
