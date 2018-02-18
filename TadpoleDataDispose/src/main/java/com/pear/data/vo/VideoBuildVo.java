package com.pear.data.vo;


/**
 * 视频生产的属性
 * */
public class VideoBuildVo {

	/**视频资源路径*/
	String path;
	
	/**视频标题*/
	String title;
	
	/**开始时间*/
	String startTime;
		
	/**结束时间*/
	String endTime;
		
	public VideoBuildVo(String path, String title, String startTime, String endTime) {
		super();
		this.path = path;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}
