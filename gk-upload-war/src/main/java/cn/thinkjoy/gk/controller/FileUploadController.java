package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.constant.SpringMVCConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zuohao on 15/10/23.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/")
public class FileUploadController {
    private static final Logger LOGGER= LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(@RequestParam(value = "files",required = false)MultipartFile[] files,@RequestParam(value = "params",required = false)String params){
        for (MultipartFile file:files){
            //文件名称
            String fileOriginalName=file.getOriginalFilename();
            //文件类型
            String fileType=fileOriginalName.substring(fileOriginalName.lastIndexOf(".")+1,fileOriginalName.length());
            //文件大小
            long fileSize=file.getSize();
            //文件认证
            if(true){

            }
            //文件保存路径
            //保存文件

        }
        return null;
    }
}
