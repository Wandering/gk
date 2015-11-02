package cn.thinkjoy.gk.runnable;


import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.param.FileUploadParam;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageRunnable implements Runnable {

	private static final Logger LOGGER= LoggerFactory.getLogger(VideoRunnable.class);

	// 系统文件名称
	private String systemFileNames;
	// 源文件路径
	private String filePaths;

	private FileUploadParam fileUploadParam;

	public ImageRunnable(String filePaths, String systemFileNames, FileUploadParam fileUploadParam) {
		super();
		this.systemFileNames = systemFileNames;
		this.filePaths = filePaths;
		this.fileUploadParam = fileUploadParam;
	}

	public void run() {
		try {
			LOGGER.info("调用创建缩略图和水印");
			UploadUtil.createCondenseAndWatermark(filePaths, systemFileNames, fileUploadParam);
		} catch (Exception e) {
			new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
			e.printStackTrace();
		}finally{
			System.gc();
		}
	}
}
