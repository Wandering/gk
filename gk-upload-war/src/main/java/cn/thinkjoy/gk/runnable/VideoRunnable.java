package cn.thinkjoy.gk.runnable;

import cn.thinkjoy.gk.util.VideoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoRunnable implements Runnable {

	private static final Logger LOGGER= LoggerFactory.getLogger(VideoRunnable.class);

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
			LOGGER.info("调用创建视频转码");
			VideoUtil.process(filePath + "/" + fileName, filePath + "/" + systemFileName + ".mp4");
		} catch (Exception e) {
//			new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
			e.printStackTrace();
		} finally{
			System.gc();
		}
	}
}
