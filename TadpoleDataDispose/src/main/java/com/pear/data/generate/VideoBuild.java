package com.pear.data.generate;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pear.common.utils.media.ReadVideoUtils;
import com.pear.common.utils.system.CmdUtils;
import com.pear.common.utils.system.DateTimeUtils;
import com.pear.common.utils.system.FileUtils;
import com.pear.data.vo.VideoBuildVo;

import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;

/**
 * 生产视频
 */
public class VideoBuild {

	private static final String FFMPEG_COMMAND = "bin\\ffmpeg.exe ";

	/**
	 * 自增下标
	 */
	private static int INCREASE_INDEX = 1;

	/**
	 * 开始生产视频
	 */
	public void build(String inPath, String outPath) {
		List<String> list = getAllVideoPath(inPath);
		for (int i = 0; i < list.size(); i++) {
			String videoPath = list.get(i);
			System.out.println("正在生产第" + (i + 1) + "个:" + videoPath);
			disposeOnceVideo(new File(videoPath), outPath);
		}
		System.out.println("生产视频完成");
	}

	/**
	 * 获取所有视频的文件
	 */
	private List<String> getAllVideoPath(String path) {
		List<String> list = new ArrayList<String>();
		FileUtils.getAllFilesByRecursionFolder(list, new File(path));
		list = list.stream().filter((String s) -> !s.endsWith("txt")).collect(Collectors.toList());
		return list;
	}

	/**
	 * 处理一个视频资源
	 */
	private void disposeOnceVideo(File videoFile, final String outPath) {
		try {
			String name = videoFile.getName();
			name = name.substring(0, name.indexOf("."));
			String configPath = videoFile.getParent() + File.separator + name + ".txt";
			String configContent = FileUtils.readTxtByFile(configPath, "utf-8");
			List<VideoBuildVo> allConfig=getAllConfigByOnce(videoFile, configContent);
			for(int i=0;i<allConfig.size();i++) 
			{
				VideoBuildVo videoBuild=allConfig.get(i);
				String outPathTemp= outPath+File.separator + videoFile.getParentFile().getName();
				String saveFolder = createdSaveOutputFolder(outPathTemp);
				String videoSavePath = saveFolder + File.separator + videoBuild.getTitle() + ".mp4";
				covertVideo(videoFile.getPath(),
						videoBuild.getStartTime(),
						videoBuild.getEndTime(),
						videoSavePath);
				excludeShortcat(videoSavePath, saveFolder);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 获取当个配置文件的所有剪辑集合配置
	 */
	private List<VideoBuildVo> getAllConfigByOnce(File videoFile, String configContent) {
		List<VideoBuildVo> list = new ArrayList<>();
		JSONArray jsonArray = new JSONArray(configContent);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArray.get(i); 
			String title = jsonObj.getString("title");
			String startTime = "00:00:00";
			String endTime = "10:00:00";
			if (jsonObj.has("start"))
				startTime = jsonObj.getString("start");
			if (jsonObj.has("end"))
				endTime = jsonObj.getString("end");
			list.add(new VideoBuildVo(videoFile.getPath(), title, startTime, endTime));
		}
		return list;
	}

	/**
	 * 创建保存当前视频的目录
	 * 
	 * @return 保存的文件夹名称
	 */
	private String createdSaveOutputFolder(String path) {
		String saveParentFolder = path + File.separator + (INCREASE_INDEX++);
		new File(saveParentFolder).mkdirs();
		return saveParentFolder;
	}

	/**
	 * 转换视频
	 */
	private boolean covertVideo(String videoPath, String startTime, String endTime, String outPath) {
		String commandParam = " -ss " + startTime + " -t " + endTime + " -i " + videoPath + " -vf scale=800:-1 "
				+ outPath;
		String response = CmdUtils.execute(FFMPEG_COMMAND + commandParam);
		//System.out.println("response:" + response);
		return true;
	}

	/**
	 * 转换视频
	 * 
	 * @throws EncoderException
	 * @throws InputFormatException
	 * @throws ParseException
	 */
	private boolean excludeShortcat(String videoPath, String outFolder)
			throws InputFormatException, EncoderException, ParseException {
		Long videoDuration = ReadVideoUtils.getDuration(videoPath);
		List<Long> shortcatTimes = getShortcatTime(videoDuration);
		for (int i = 0; i < shortcatTimes.size(); i++) {
			Long shortcatTime = shortcatTimes.get(i);
			String shortcatTimeStr = DateTimeUtils.convert2Time(shortcatTime);
			String savePath = outFolder + File.separator + (i + 1) + ".jpg";
			String commandParam = " -i " + videoPath + " -r 1 " + " -ss "
					+ (DateTimeUtils.convert2Ms(shortcatTimeStr) / 1000) + " -t 1 " + " -f image2 " + savePath;
			String response = CmdUtils.execute(FFMPEG_COMMAND + commandParam);
			//System.out.println("response:" + response);
		}
		return true;
	}

	/**
	 * 获取适合的视频截图时间点集合
	 * 
	 * @param videoDuration
	 *            视频的总毫秒数
	 * @throws ParseException
	 */
	private List<Long> getShortcatTime(Long videoDuration) throws ParseException {
		List<Long> list = new ArrayList<>();
		/** 每个视频至少也要5分钟吧，不然看毛线啊 */
		if (50 * 6 * 1000l > (videoDuration))
			throw new IllegalArgumentException("视频长度太短了，总秒数只有:" + videoDuration + "，至少需要7分钟。");

		/** 前面三分钟很多都是广告号好吗 */
		Long startTime = 3 * 60 * 1000l;
		Long endTime = videoDuration - 1 * 60 * 1000;

		/** 取中间两张照片，所以需要剩下空间除去三个 */
		Long interval = (endTime - startTime) / 3;

		list.add(startTime);
		list.add(startTime + interval);
		list.add(startTime + interval * 2);
		list.add(endTime);
		return list;
	}

}
