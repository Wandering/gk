package cn.thinkjoy.gk.runnable;

import cn.thinkjoy.gk.constant.VideoConst;
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
			LOGGER.info("转码开始!");
			VideoUtil.process(filePath + "/" + fileName, filePath + "/" + systemFileName + "."+ VideoConst.TYPE);
			LOGGER.info("转码完成!");
		} catch (Exception e) {
			LOGGER.info("转码错误!");
//			new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
			e.printStackTrace();
		}
	}
}
