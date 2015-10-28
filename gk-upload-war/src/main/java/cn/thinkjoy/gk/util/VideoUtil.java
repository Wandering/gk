package cn.thinkjoy.gk.util;

import java.io.InputStream;
import java.util.List;

public class VideoUtil {

	/**
	 *  ffmepg: 能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public static boolean process(String inputFile,String outputFile) {
		if (!CheckUtil.checkFileExists(inputFile)) {
			System.out.println(inputFile + " is not file");
			return false;
		}
		List<String> commend = new java.util.ArrayList<String>();
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
		commend.add("ffmpeg");
		commend.add("-y");
		commend.add("-i");
		commend.add(inputFile);
		commend.add("-ab");
		commend.add("128");
		commend.add("-acodec");
		commend.add("libmp3lame");  //音频
		commend.add("-ac");
		commend.add("1");
		commend.add("-ar");
		commend.add("22050");
		commend.add("-r");
		commend.add("29.97");
		commend.add("-qscale");
		commend.add("4");
		commend.add(outputFile);
		StringBuffer test=new StringBuffer();
		for(int i=0;i<commend.size();i++) {
			test.append(commend.get(i) + " ");
		}
		System.out.println(test);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
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
		List<String> commend = new java.util.ArrayList<String>();
		//视频截图
		commend.add("ffmpeg");
		commend.add("-i");
		commend.add(inputFile);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("8");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add(UploadPropertiesUtil.getInstance().getProperty("VIDEO_SCREENSHOTS"));
		commend.add(outputFile);
		StringBuffer test=new StringBuffer();
		for(int i=0;i<commend.size();i++) {
			test.append(commend.get(i) + " ");
		}
		System.out.println(test);
		try {
			ProcessBuilder builder = new ProcessBuilder();

			builder.command(commend);

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
