package com.pear.data.generate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.pear.common.utils.CmdUtils;
import com.pear.common.utils.FileUtils;
import com.pear.common.utils.media.ReadVideoUtils;

import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
 

/**
 * 生产视频
 */
public class VideoBuild {

	/**
	 * 自增下标
	 * */
	private static int INCREASE_INDEX=1;
	
	/**
	 * 开始生产视频
	 * */
	public void build(String path)
	{
		List<String> list=getAllVideoPath(path);
		for(int i=0;i<list.size();i++) 
		{
			String videoPath=list.get(i);
			System.out.println("正在生产第"+(i+1)+"个:"+videoPath);
			disposeOnceVideo(new File(videoPath));
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
	private void disposeOnceVideo(File videoFile) {
		try {
			String name = videoFile.getName();
			String configPath = videoFile.getParent() + File.separator + name + ".txt";
			String configContent = FileUtils.readTxtByFile(configPath, "utf-8");
			
			JSONObject jsonObj=new JSONObject(configContent);
			String startTime = "00:00:00";
			String endTime = "10:00:00";
			if(jsonObj.has("start"))
				startTime=jsonObj.getString("start");
			if(jsonObj.has("end"))
				endTime=jsonObj.getString("end");  
			
			String saveFolder=createdSaveOutputFolder(videoFile);
			String videoSavePath=saveFolder+File.separator+name+".mp4";
			covertVideo(videoFile.getPath(),startTime,endTime,videoSavePath);
			excludeShortcat(videoSavePath,saveFolder);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * 创建保存当前视频的目录
	 * @return 保存的文件夹名称
	 * */
	private String createdSaveOutputFolder(File path)
	{
		String saveParentFolder=path.getParent()+ File.separator + (++INCREASE_INDEX);
		new File(saveParentFolder).mkdirs();
		return saveParentFolder;		
	} 

	
	/**
	 * 转换视频
	 * */
	private boolean covertVideo(String videoPath,String startTime,String endTime,String outPath)
	{
		String commandParam=" -ss "+startTime
				+" -t "+ endTime
				+" -i "+videoPath
				+" -vf scale=800:-1 "
				+outPath;
		String exeCommand="bin/ffmpeg ";
		String response=CmdUtils.execute(exeCommand+commandParam);
		System.out.println("response:"+response);
		return true;
	}
	
	/**
	 * 转换视频
	 * @throws EncoderException 
	 * @throws InputFormatException 
	 * */
	private boolean excludeShortcat(String videoPath,String outFolder) throws InputFormatException, EncoderException
	{
		Long videoDuration=ReadVideoUtils.getDuration(videoPath);
		List<Long> shortcatTimes=getShortcatTime(videoDuration);
		for(int i=0;i<shortcatTimes.size();i++) 
		{
			Long shortcatTime=shortcatTimes.get(i);
			String shortcatTimeStr=new SimpleDateFormat("HH:mm:ss").format(new Date(shortcatTime));
			String savePath=outFolder+File.separator+(i+1)+".jpg";
			String commandParam=" -i "+videoPath
					+" -r 1 "
					+" -ss "+shortcatTimeStr
					+" -t 1 "
					+" -f image2 "
					+savePath; 
			String exeCommand="bin/ffmpeg ";
			String response=CmdUtils.execute(exeCommand+commandParam);
			System.out.println("response:"+response);
		}
		return true;
	}
	
	/**
	 * 获取适合的视频截图时间点集合
	 * @param videoDuration 视频的总毫秒数
	 * */
	private List<Long> getShortcatTime(Long videoDuration)
	{
		List<Long> list=new ArrayList<>();
		/**每个视频至少也要6分钟吧，不然看毛线啊*/
		if(60*6*1000l>videoDuration)
			throw new IllegalArgumentException("视频长度太短了，总秒数只有:"+videoDuration+"，至少需要7分钟。");
		
		Long startTime=3*60*1000l;
		Long endTime=videoDuration-1*60*1000;
		
		/**取中间两张照片，所以需要剩下空间除去三个*/
		Long interval=(endTime-startTime)/3;
		
		list.add(startTime);
		list.add(startTime+interval);
		list.add(startTime+interval*2);
		list.add(endTime);
		return list;
	}
	
}
