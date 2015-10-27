package cn.thinkjoy.gk.util;

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
		commend.add("-i");
		commend.add(inputFile);
		commend.add("-ab");
		commend.add("128");
		commend.add("-acodec");
		commend.add("libmp3lame");
		commend.add("-ac");
		commend.add("1");
		commend.add("-ar");
		commend.add("22050");
		commend.add("-r");
		commend.add("29.97");
		commend.add("-qscale");
		commend.add("6");
		commend.add("-y");
		commend.add(outputFile);
		StringBuffer test=new StringBuffer();
		for(int i=0;i<commend.size();i++)
			test.append(commend.get(i)+" ");
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

}
