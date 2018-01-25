package com.pear.data.vo;

import java.util.List;

/**
 * 图片类型数据
 * */
public class ImageClassifyVo {

	/**
	 * 类别
	 * */
	String classify; 
	
	/**
	 * 当前分类下的图片集合
	 * */
	List<ImageListVo> images;

	public ImageClassifyVo(String classify, List<ImageListVo> images) {
		super();
		this.classify = classify;
		this.images = images;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public List<ImageListVo> getImages() {
		return images;
	}

	public void setImages(List<ImageListVo> images) {
		this.images = images;
	}
	
}
