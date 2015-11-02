package cn.thinkjoy.gk.util;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.UploadConst;
import cn.thinkjoy.gk.protocol.ERRORCODE;

import java.io.File;
import java.util.Properties;


public class CheckUtil {

	private static final Properties properties = UploadPropertiesUtil.getInstance();

	/**
	 * 校验文件
	 */
	public static void checkFile(Integer fileSize,String fileType) throws Exception {
		if(fileSize<=0){
			throw new BizException(ERRORCODE.UPLOAD_ERROR_401.getCode(),ERRORCODE.UPLOAD_ERROR_401.getMessage());
		}else if (!checkFileSize(fileSize)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_400.getCode(),ERRORCODE.UPLOAD_ERROR_400.getMessage());
		} else if (!checkFileType(fileType)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_402.getCode(),ERRORCODE.UPLOAD_ERROR_402.getMessage());
		}
	}
	
	/**
	 * 校验文件
	 */
	public static void checkImageFile(Integer fileSize,String imageType) throws Exception {
		if(fileSize<=0){
			throw new BizException(ERRORCODE.UPLOAD_ERROR_401.getCode(),ERRORCODE.UPLOAD_ERROR_401.getMessage());
		}else if (!checkImageSize(fileSize)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_400.getCode(),ERRORCODE.UPLOAD_ERROR_400.getMessage());
		} else if (!checkImageType(imageType)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_402.getCode(),ERRORCODE.UPLOAD_ERROR_402.getMessage());
		}
	}

	/**
	 * 校验文件
	 */
	public static void checkVideoFile(Integer fileSize,String videoType) throws Exception {
		if(fileSize<=0){
			throw new BizException(ERRORCODE.UPLOAD_ERROR_401.getCode(),ERRORCODE.UPLOAD_ERROR_401.getMessage());
		}else if (!checkVideoSize(fileSize)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_400.getCode(),ERRORCODE.UPLOAD_ERROR_400.getMessage());
		} else if (!checkVideoType(videoType)) {
			throw new BizException(ERRORCODE.UPLOAD_ERROR_402.getCode(),ERRORCODE.UPLOAD_ERROR_402.getMessage());
		}
	}

	/**
	 * 检查图片类型
	 */
	private static boolean checkImageType(String imageType) {
		boolean flag = false;
		if (!"".equals(imageType)) {

			for (String s : UploadConst.IMAGE_TYPES) {
				if (s.equals(imageType.toLowerCase())) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 检查图片类型
	 */
	private static boolean checkVideoType(String videoType) {
		boolean flag = false;
		if (!"".equals(videoType)) {

			for (String s : UploadConst.VIDEO_TYPES) {
				if (s.equals(videoType.toLowerCase())) {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 检查文件类型
	 */
	private static boolean checkFileType(String type) {
		boolean flag = false;
		if (!"".equals(type)) {
			for (String s : UploadConst.FILE_TYPES) {
				if (s.equals(type)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 验证文件大小
	 */
	private static boolean checkFileSize(int fileSize) throws Exception {
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
	private static boolean checkImageSize(int imageSize) throws Exception {
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

	/**
	 * 验证图片大小
	 */
	private static boolean checkVideoSize(int videoSize) throws Exception {
		boolean flag = true;
		try{
			int imageUploadSize = ConvertUtil.stringToInteger(properties.get("VIDEO_UPLOAD_SIZE").toString());
			if (videoSize > imageUploadSize) {
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	/**
	 * 检查文件是否存在
	 */
	public static boolean checkFileExists(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查视频类型
	 * @param inputFile
	 * @return ffmpeg 能解析返回0，不能解析返回1
	 */
	private static int checkContentType(String inputFile) {
		String type = inputFile.substring(inputFile.lastIndexOf(".") + 1,
				inputFile.length())
				.toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

}
