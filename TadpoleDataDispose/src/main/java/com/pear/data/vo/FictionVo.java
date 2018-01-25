package com.pear.data.vo;



/**
 * 小说
 * */
public class FictionVo {

	/**标题*/
	String title;
	
	/***/
	String content;

	public FictionVo(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	} 
	
}
