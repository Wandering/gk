package cn.thinkjoy.gk.constant;

import cn.thinkjoy.gk.util.UploadPropertiesUtil;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class Const {
	
	private static Properties properties = UploadPropertiesUtil.getInstance();
	/**
	 * 互斥锁static唯一
	 */
	public static Object LOCK = new Object();
	/**
	 * 支持生成大中小图的类型
	 */
	public static final String[] IMAGE_TYPES = properties.get("IMAGE_TYPES").toString().split(",");
	/**
	 * 允许上传的文件类型
	 */
	public static final String[] FILE_TYPES = properties.get("FILE_TYPES").toString().split(",");
	/**
	 * 图片水印文字字体
	 */
	public static final String[] FONT_NAME = {"TimesRoman","Courier","Arial","黑体"};
	/**
	 * 图片水印文字样式
	 */
	public static final Integer[] FONT_STYLE = {Font.PLAIN,Font.BOLD,Font.ITALIC};
	/**
	 * 图片水印文字初始大小
	 */
	public static final Integer FONT_SIZE = 20;
	/**
	 * 图片水印文字位置X
	 */
	public static final Integer FONT_POSITION_X = 10;
	/**
	 * 图片水印文字位置Y
	 */
	public static final Integer FONT_POSITION_Y = 20;
	/**
	 * 编码集
	 */
	public static final String ENCODING = "UTF-8";
	/**
	 * 内容编码
	 */
	public static final String CONTENT_TYPE = "text/html;charset=UTF-8";
	/**
	 * 水印文本类型
	 */
	public static final String  WATERMARK_TYPE_TEXT = "0";
	/**
	 * 水印图片类型
	 */
	public static final String  WATERMARK_TYPE_IMAGE = "1";
	/**
	 * 文件上传目录
	 */
	public static final String  FILE_UPLOAD_DIR = "/yuleing/upload";
	/**
	 * 头像存放目录
	 */
	public static final String  FILE_IMAGES_DIR = "/yuleing/images";
	/**
	 * 城市图片存放目录
	 */
	public static final String  FILE_CITY_DIR = "/yuleing/area/city";
	/**
	 * 文件宽度
	 */
	public static final Map<String,Integer> widths = new HashMap<String, Integer>();
	/**
	 * 文件高度
	 */
	public static final Map<String,Integer> heights = new HashMap<String, Integer>();
	/**
	 * 初始化文件系统名称
	 */
	public static final String SYSTEM_NAME = "system_name";
	/**
	 * 初始化文件存放目录
	 */
	public static final String UPLOAD_DIR = "upload_dir";
	/**
	 * 初始化文件存放目录
	 */
	public static final String UPLOAD_NAME = "upload_name";
	/**
	 * 初始化时压缩
	 */
	public static final String INIT = "init";
	/**
	 * 不初始化时压缩
	 */
	public static final String NO_INIT = "noinit";

	public static final String IMAGE_UPLOADFILETYPE = "jpg";
	
	static{
		//宽度
		widths.put(PXConst.PX_50_50, 50);
		widths.put(PXConst.PX_100_100, 100);
		widths.put(PXConst.PX_150_150, 150);
		widths.put(PXConst.PX_200_150, 200);
		widths.put(PXConst.PX_600_200, 600);
		widths.put(PXConst.PX_860_460, 860);
		widths.put(PXConst.PX_600_400, 600);
		widths.put(PXConst.PX_300_200, 300);
		
		//高度
		heights.put(PXConst.PX_50_50, 50);
		heights.put(PXConst.PX_100_100, 100);
		heights.put(PXConst.PX_150_150, 150);
		heights.put(PXConst.PX_200_150, 150);
		heights.put(PXConst.PX_600_200, 200);
		heights.put(PXConst.PX_860_460, 460);
		heights.put(PXConst.PX_600_400, 400);
		heights.put(PXConst.PX_300_200, 200);

	}
	
}
