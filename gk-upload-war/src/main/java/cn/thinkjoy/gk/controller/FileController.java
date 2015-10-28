package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.Const;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.param.FileUploadParam;
import cn.thinkjoy.gk.protocol.DateStyle;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.runnable.UploadRunnable;
import cn.thinkjoy.gk.util.CheckUtil;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/upload")
public class FileController extends BaseController{

	private static final Logger LOGGER= LoggerFactory.getLogger(FileController.class);

	/**
	 * 后台上传文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/file",method = RequestMethod.POST)
	@ResponseBody
	public List<FileUploadVO> file(@RequestParam(value = "files", required = false) MultipartFile[] fileUploads,
						 @RequestParam(value = "params", required = false)String params) throws Exception {

		long stratTime = System.currentTimeMillis();

		List<FileUploadVO> fileUploadVOs = new ArrayList<FileUploadVO>();

		if(null!=fileUploads&&fileUploads.length>0){

			String date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
			for(MultipartFile fileUpload:fileUploads){

				FileUploadVO fileUploadVO = new FileUploadVO();

				String fileUploadFileName = fileUpload.getOriginalFilename();
				// 文件类型
				String uploadFileType = fileUploadFileName.substring(fileUploadFileName.lastIndexOf(".")+1,fileUploadFileName.length());

				int fileSize = (int) fileUpload.getSize();

				// 验证文件
				CheckUtil.checkFile(fileSize, uploadFileType);

				String filePath = Const.FILE_UPLOAD_DIR+date+"/"+uploadFileType;

				String systemFileName= UUID.randomUUID().toString()+ "." + uploadFileType;
				// 本机文件地址
				UploadUtil.fileUpload(new File(filePath,systemFileName), fileUpload);

//				fileUpload.transferTo(new File(filePath+systemFileName));

				fileUploadVO.setName(systemFileName);

				fileUploadVO.setSize(fileSize);

				fileUploadVO.setName(fileUploadFileName);
				fileUploadVO.setSystem_name(systemFileName);
				fileUploadVO.setType(uploadFileType);
//				fileUploadVO.setPath(DoMainConst.P_URL+doMainBasePath);

				fileUploadVOs.add(fileUploadVO);

			}
		}else{
			throw new BizException(ERRORCODE.UPLOAD_ERROR_405.getCode(),ERRORCODE.UPLOAD_ERROR_405.getMessage());
		}

		long resultTime = System.currentTimeMillis() - stratTime;
		LOGGER.info("执行时间:" + DateUtil.DateToString(new Date(resultTime), DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
		return fileUploadVOs;
	}
}
