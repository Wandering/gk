package cn.thinkjoy.gk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class VideoUtil {

	private static final Logger LOGGER= LoggerFactory.getLogger(VideoUtil.class);

	/**
	 *  ffmepg: 能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public static boolean process(String inputFile,String outputFile) {
		LOGGER.info("视频源地址:"+inputFile);
		LOGGER.info("视频转码地址:"+outputFile);
		if (!CheckUtil.checkFileExists(inputFile)) {
			System.out.println(inputFile + " is not file");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		//低精度
//		commend.add("ffmpeg");
//		commend.add("-i");
//		commend.add(inputFile);
//		commend.add("-ab");
//		commend.add("128");
//		commend.add("-acodec");
//		commend.add("libmp3lame");
//		commend.add("-ac");
//		commend.add("1");
//		commend.add("-ar");
//		commend.add("22050");
//		commend.add("-r");
//		commend.add("29.97");
//		commend.add("-b");
//		commend.add("512");
//		commend.add("-y");
//		commend.add(outputFile);
		//高精度
		commands.add("ffmpeg");
		commands.add("-y");
		commands.add("-i");
		commands.add(inputFile);
		commands.add("-ab");
		commands.add("128");
		commands.add("-acodec");
		commands.add("libmp3lame");  //音频
		commands.add("-ac");
		commands.add("2");
		commands.add("-ar");
		commands.add("22050");
		commands.add("-r");
		commands.add("29.97");
		commands.add("-qscale");
		commands.add("4");
		commands.add(outputFile);
		StringBuffer command=new StringBuffer();
		for(int i=0;i<commands.size();i++) {
			command.append(commands.get(i) + " ");
		}
		LOGGER.info(command.toString());
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			LOGGER.info("转码开始!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *  ffmepg: 能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public static boolean processImage(String inputFile,String outputFile) {
		if (!CheckUtil.checkFileExists(inputFile)) {
			System.out.println(inputFile + " is not file");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		//视频截图
		commands.add("ffmpeg");
		commands.add("-i");
		commands.add("-y");
		commands.add(inputFile);
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("8");
		commands.add("-t");
		commands.add("0.001");
		commands.add("-s");
		commands.add(UploadPropertiesUtil.getInstance().getProperty("VIDEO_SCREENSHOTS"));
		commands.add(outputFile);
		StringBuffer command =new StringBuffer();
		for(int i=0;i<commands.size();i++) {
			command.append(commands.get(i) + " ");
		}
		LOGGER.info(command.toString());
		try {
			ProcessBuilder builder = new ProcessBuilder();

			builder.command(commands);

			builder.redirectErrorStream(true);

			System.out.println("视频截图开始...");

			// builder.start();

			Process process = builder.start();

			InputStream in =process.getInputStream();

			byte[] re = new byte[1024];

			System.out.print("正在进行截图，请稍候");

			while (in.read(re) != -1) {

				System.out.print(".");

			}

			System.out.println("");

			in.close();

			System.out.println("视频截图完成...");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
