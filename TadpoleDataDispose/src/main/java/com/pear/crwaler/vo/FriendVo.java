package com.pear.crwaler.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 朋友圈的数据
 * */
public class FriendVo implements Serializable{
 
	private static final long serialVersionUID = 1L;

	/**标题*/
	String title;
	
	/**图片链接*/
	List<String> images;
	
	/**所有评论*/
	List<String> comments; 
	
	/**是否精华*/
	boolean isGood;
	
	/**是否热帖*/
	boolean isHot; 
	
	public FriendVo(String title, List<String> images, List<String> comments, boolean isGood, boolean isHot) {
		super();
		this.title = title;
		this.images = images;
		this.comments = comments;
		this.isGood = isGood;
		this.isHot = isHot;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public boolean isGood() {
		return isGood;
	}

	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FriendVo [title=" + title + ", images=" + images + ", comments=" + comments + ", isGood=" + isGood
				+ ", isHot=" + isHot + "]";
	}
	
	

}
