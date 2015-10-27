package cn.thinkjoy.gk.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.Const;
import cn.thinkjoy.gk.param.FileUploadParam;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;


public class UploadUtil {
	
	private static final Properties properties = UploadPropertiesUtil.getInstance();
	
	/**
	 * 创建缩略图和水印
	 */
	public static void createCondenseAndWatermark(String filePath,String fileName,FileUploadParam fileUploadParam) throws Exception{
		try{
			String name = fileUploadParam.getName();
			// 生成移动端大图
			updateSize(new File(filePath+fileName),new File(filePath+name+fileName), Const.widths.get(name), Const.heights.get(name),fileUploadParam.getIs_watermark(),fileUploadParam.isKeep());
		}catch(Exception e){
			new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
			throw e;
		}
	}

	
	/**
	 * 文件上传方法
	 */
	public static int fileUpload(File destFile, MultipartFile uploadFile) throws Exception {
		int status = 1;
		try {
			
			createFolder(destFile.getParent());

			FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), destFile);

//			uploadFile.transferTo(destFile);

			status = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return status;
	}
	
	/**
	 * 
	 */
	public static void updateSize(File filePath,File toFilePath,int new_w, int new_h,boolean is_watermark,boolean keepAspectRatio) throws Exception {
		try{
			Builder<File> b =Thumbnails.of(filePath);
			b.size(new_w, new_h);
			b.keepAspectRatio(keepAspectRatio);
			if(is_watermark){
				b.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(properties.get("WATERMARK_IMAGE").toString())), Float.valueOf(properties.get("WATERMARK_IMAGE_DISAPHANEITY").toString()));
			}
			b.toFile(toFilePath);
		}catch(Exception e){
			throw e;
		}
	}
	
//	public static void main(String[] args) throws Exception {
//		updateSize(new File("d:/locate.png"), new File("d:/locate.png"), 30, 29, false, false);
//	}

	/**
	 * 创建目录
	 */
	public static void createFolder(String path) throws Exception{
		try{
			File filePath = new File(path);
			// 如果文件夹不存在创建文件夹
			if (!filePath.exists()) {
				if (!filePath.mkdirs()) {
					//logger.info("创建失败!");
				} else {
					//logger.info("创建成功!");
				}
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 获取图片宽度和高度
	 */
	public static Map<String,Integer> getImageWidthAndHeight(File file) throws IOException{
		BufferedImage bi = null;
		Map<String,Integer> map = null;
		try {
			bi = ImageIO.read(file);
			map = new HashMap<String,Integer>();
			map.put("width", bi.getWidth());
			map.put("height", bi.getHeight());
		} catch (IOException e) {
			throw e;
		}finally{
			bi.flush();
		}
		return map;
	}
	
	/**
	 * 校验文件
	 */
	public static void checkFile(Integer fileSize,String fileType) throws Exception {
		if(fileSize<=0){
			throw new BizException(ERRORCODE.UPLOAD_ERROR_401.getCode(),ERRORCODE.UPLOAD_ERROR_401.getMessage());
		}else if (!ckeckFileSize(fileSize)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_400.getCode(),ERRORCODE.UPLOAD_ERROR_400.getMessage());
		} else if (!checkType(fileType)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_402.getCode(),ERRORCODE.UPLOAD_ERROR_402.getMessage());
		}
	}
	
	/**
	 * 校验文件
	 */
	public static void checkImageFile(Integer fileSize,String imageType) throws Exception {
		if(fileSize<=0){
			throw new BizException(ERRORCODE.UPLOAD_ERROR_401.getCode(),ERRORCODE.UPLOAD_ERROR_401.getMessage());
		}else if (!ckeckImageSize(fileSize)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_400.getCode(),ERRORCODE.UPLOAD_ERROR_400.getMessage());
		} else if (!checkImageType(imageType)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_402.getCode(),ERRORCODE.UPLOAD_ERROR_402.getMessage());
		}
	}

	/**
	 * 检查图片类型
	 */
	public static boolean checkImageType(String imageType) {
		boolean flag = false;
		if (!"".equals(imageType)) {

			for (String s : Const.IMAGE_TYPES) {
				if (s.equals(imageType.toLowerCase())) {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 检查文件类型
	 */
	private static boolean checkType(String type) {
		boolean flag = false;
		if (!"".equals(type)) {
			for (String s : Const.FILE_TYPES) {
				if (s.equals(type)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 获取项目HHTP绝对路径
	 */
	public static String getDoMainBasePath(String uploadFileType,String datePath) {
		return "/" + uploadFileType+ "/" + datePath + "/";
	}

	/**
	 * 删除文件方法
	 */
	public static boolean delFile(String path) throws Exception {
		boolean status = false;
		try {
			File file = new File(path);
			if (file.exists()) {
				if (file.delete()) {
					status = true;
					//logger.info("文件:"+path+" 删除成功!");
				} else {
					//logger.info("文件:"+path+" 删除失败!");
				}
			}
			
		} catch (Exception e) {
			new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
			e.printStackTrace();
			throw e;
		}
		return status;
	}
	
	/**
	 * 验证文件大小
	 */
	private static boolean ckeckFileSize(int fileSize) throws Exception {
		boolean flag = true;
		try{
			int fileUploadSize = ConvertUtil.stringToInteger(properties.get("FILE_UPLOAD_SIZE").toString());
			if (fileSize > fileUploadSize) {
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * 验证图片大小
	 */
	private static boolean ckeckImageSize(int imageSize) throws Exception {
		boolean flag = true;
		try{
			int imageUploadSize = ConvertUtil.stringToInteger(properties.get("IMAGE_UPLOAD_SIZE").toString());
			if (imageSize > imageUploadSize) {
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
}
