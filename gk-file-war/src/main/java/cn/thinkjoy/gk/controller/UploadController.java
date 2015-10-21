package cn.thinkjoy.gk.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;

import cn.thinkjoy.gk.constant.Const;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.param.FileUploadParam;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.runnable.UploadRunnable;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.DateUtil;
import cn.thinkjoy.gk.util.UploadUtil;
import cn.thinkjoy.gk.vo.FileUploadVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/")
public class UploadController extends BaseController{

	private static final Logger LOGGER= LoggerFactory.getLogger(UploadController.class);

	/**
	 * 后台上传文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value = "files[]", required = false) MultipartFile[] fileUploads,@RequestParam(value = "params", required = false)String params) throws Exception {
//		String cookieValue = CookieUtil.getCookieValue(request.getCookies(), CookieConst.ADMINUSER_COOKIE_NAME);
//		if(StringUtils.isEmpty(cookieValue)){
//			throw new BizException(ERRORCODE.UPLOAD_ERROR_0.getCode(),ERRORCODE.UPLOAD_ERROR_0.getMessage());
//			return null;
//		}
		JSONObject obj = null;
		if(!StringUtils.isEmpty(params)){
			obj = JSON.parseObject(params);
		}
		long stratTime = System.currentTimeMillis();

		if(null!=fileUploads&&fileUploads.length>0){
			List<FileUploadVO> fileUploadVOs = new ArrayList<FileUploadVO>();
			JSONArray initArray = new JSONArray();
			for(MultipartFile fileUpload:fileUploads){

				String fileUploadFileName = fileUpload.getOriginalFilename();
				// 文件类型
				String uploadFileType = fileUploadFileName.substring(fileUploadFileName.lastIndexOf(".")+1,fileUploadFileName.length());

				int fileSize = (int) fileUpload.getSize();

				// 验证文件
				UploadUtil.checkImageFile(fileSize, uploadFileType);

				String filePath = Const.FILE_IMAGES_DIR+obj.getString(Const.UPLOAD_DIR);
				String systemFileName= obj.getString(Const.UPLOAD_NAME)+ "." + Const.IMAGE_UPLOADFILETYPE;
				// 本机文件地址
				UploadUtil.fileUpload(new File(filePath,systemFileName), fileUpload);

				fileUpload.transferTo(new File(filePath+systemFileName));
				if(null!=obj){
					if(obj.containsKey(Const.INIT)){
						initArray = JSONArray.parseArray(obj.getString(Const.INIT));
						FileUploadParam fileUploadParam = null;
						for(int i=0;i<initArray.size();i++){
							fileUploadParam = JSONObject.parseObject(initArray.getString(i), FileUploadParam.class);
							UploadUtil.createCondenseAndWatermark(filePath, systemFileName,fileUploadParam);
						}
						initArray.clear();
					}

					if(obj.containsKey(Const.NO_INIT)){
						initArray = JSONArray.parseArray(obj.getString(Const.NO_INIT));
						FileUploadParam fileUploadParam = null;
						for(int i=0;i<initArray.size();i++){
							fileUploadParam = (FileUploadParam)JSONObject.parseObject(initArray.getString(i), FileUploadParam.class);
							UploadRunnable r = new UploadRunnable(filePath, systemFileName,fileUploadParam);
							Thread t = new Thread(r);
							t.start();
						}
						initArray.clear();
						initArray = null;
					}
				}
			}

			FileUploadVO fileUploadVO = new FileUploadVO();

//			fileUploadVO.setSize(fileSize);
//			fileUploadVO.setName(fileUploadFileName);
//			fileUploadVO.setSystem_name(systemFileName);
//			fileUploadVO.setType(uploadFileType);
//			fileUploadVO.setPath(DoMainConst.P_URL+doMainBasePath);
//			fileUploadVO.setStatus(status);
			fileUploadVOs.add(fileUploadVO);
		}else{
			throw new BizException(ERRORCODE.UPLOAD_ERROR_405.getCode(),ERRORCODE.UPLOAD_ERROR_405.getMessage());
		}

		long resultTime = System.currentTimeMillis() - stratTime;
		LOGGER.info("执行时间:" + DateUtil.DateToString(new Date(resultTime), "yyyy-MM-dd HH:mm:ss"));
		return null;
	}
}
