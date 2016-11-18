package cn.thinkjoy.gk.controller.bussiness;

import cn.thinkjoy.gk.common.ErrorCode;
import cn.thinkjoy.gk.common.ExceptionUtil;
import cn.thinkjoy.gk.common.ExpertUserContext;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.ExpertInfo;
import cn.thinkjoy.gk.pojo.ExpertCustomerDTO;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;
import cn.thinkjoy.gk.service.ICustomerService;
import cn.thinkjoy.gk.service.IExpertInfoService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by yangyongping on 2016/11/16.
 * 专家后台
 * 用户信息
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert/admin/user")
public class ExpertUserController {
    @Autowired
    IExpertInfoService expertInfoService;

    /**
     * 修改用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateInfo",method = RequestMethod.POST)
    public Boolean updateInfo(ExpertUserDTO expertUserDTO){
        Map<String,Object> map = Maps.newHashMap();
        Long id= ExpertUserContext.getCurrentUser().getId();
        ExpertInfo expertInfo = (ExpertInfo)expertInfoService.fetch(id);
        if (expertInfo==null){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_ERROR);
        }
        expertUserDTO.setId(id);
        expertUserDTO.setAccount(null);
        return expertInfoService.update(dtoToInfo(expertUserDTO))>0;
    }

    /**
     * 查询用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryUserInfo",method = RequestMethod.GET)
    public ExpertUserDTO queryExpertUserInfo(){
        Long id= ExpertUserContext.getCurrentUser().getId();
        ExpertInfo expertInfo = (ExpertInfo)expertInfoService.fetch(id);
        if (expertInfo==null){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_ERROR);
        }
        return infoToDto(expertInfo);
    }



    /**
     * poToVo
     * @param dto
     * @return
     */
    private ExpertInfo dtoToInfo(ExpertUserDTO dto){
        ExpertInfo expertInfo = new ExpertInfo();
        expertInfo.setExpertName(dto.getExpertName());
        expertInfo.setExpertPhone(dto.getAccount());
        expertInfo.setId(dto.getId());
        expertInfo.setQq(dto.getQq());
        expertInfo.setWeixin(dto.getWeixin());
        expertInfo.setExpertPhotoUrl(dto.getImageUrl());
        return expertInfo;
    }

    /**
     * voToPo
     * @param info
     * @return
     */
    private ExpertUserDTO infoToDto(ExpertInfo info){
        ExpertUserDTO dto = new ExpertUserDTO();
        dto.setExpertName(info.getExpertName());
        dto.setAccount(info.getExpertPhone());
        dto.setId(info.getId());
        dto.setQq(info.getQq());
        dto.setWeixin(info.getWeixin());
        dto.setImageUrl(info.getExpertPhotoUrl());
        return dto;
    }
}
