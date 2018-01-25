package com.pear.data.vo;

import java.util.List;

/**
 * 图片集合
 * */
public class ImageListVo {

	String title;
	
	List<String> paths;

	public ImageListVo(String title, List<String> paths) {
		super();
		this.title = title;
		this.paths = paths;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	
}
