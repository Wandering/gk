package cn.thinkjoy.gk.runnable;

import cn.thinkjoy.gk.util.VideoUtil;

public class VideoRunnable implements Runnable {

	// 文件路径
	private String filePath;
	// 文件名称
	private String fileName;
	// 文件名称不带后缀
	private String systemFileName;

	public VideoRunnable(String filePath, String fileName, String systemFileName) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
		this.systemFileName = systemFileName;
	}

	public void run() {
		try {
			//logger.info("调用创建缩略图和水印方法FileUploadUtil.createThumbnailAndWatermark");
			VideoUtil.process(filePath + fileName, filePath + systemFileName + ".mp4");
		} catch (Exception e) {
//			new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
			e.printStackTrace();
		}finally{
			System.gc();
		}
	}
}
