package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.domain.ExpertResources;
import cn.thinkjoy.gk.pojo.MeunDto;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import java.util.List;

/**
 * Created by yangguorong on 16/10/17.
 *
 * 模型转换工具类
 */
public class ConvertUtil {

    /**
     * Resources --> MeunDto
     *
     * @param resources
     * @return
     */
    public static MeunDto convert2MeunDto(ExpertResources resources){
        MeunDto meunDto = new MeunDto();
        meunDto.setMeunId(Integer.valueOf(resources.getId().toString()));
        meunDto.setMeunName(resources.getResName());
        meunDto.setMeunUrl(resources.getResUrl());
        meunDto.setIconUrl(resources.getIconUrl());
        return meunDto;
    }

    /**
     * 重新组装资源菜单
     *
     * @param resources
     * @return
     */
    public static List<MeunDto> convertMeuns(List<ExpertResources> resources){

        List<MeunDto> parentMeuns = Lists.newArrayList();

        // 组装一级菜单
        for(ExpertResources resource : resources){
            if(resource.getParentId() == 0){
                List<MeunDto> sonMeuns = Lists.newArrayList();
                MeunDto meunDto = convert2MeunDto(resource);
                meunDto.setSonMeuns(sonMeuns);
                parentMeuns.add(meunDto);
            }
        }

        // 组装二级菜单
        for(ExpertResources resource : resources){
            for(MeunDto parentMeun : parentMeuns){

                if(resource.getParentId() == 0){
                    continue;
                }

                if(resource.getParentId() == parentMeun.getMeunId()){
                    parentMeun.getSonMeuns().add(convert2MeunDto(resource));
                }
            }
        }

        return parentMeuns;
    }

}
