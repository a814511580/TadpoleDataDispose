package com.pear.crwaler.vo;


/**
 * 猫咪朋友圈类别属性
 * */
public class SmallcatFriendTypeVo {

	/**什么类别*/
	String type;
	
	/**圈子对应的ID*/
	String mcId;   
	

	public SmallcatFriendTypeVo(String type, String mcId) {
		super();
		this.type = type;
		this.mcId = mcId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMcId() {
		return mcId;
	}

	public void setMcId(String mcId) {
		this.mcId = mcId;
	} 
	
}
