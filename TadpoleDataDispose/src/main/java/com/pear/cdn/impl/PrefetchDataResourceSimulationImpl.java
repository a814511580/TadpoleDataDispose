package com.pear.cdn.impl;

import java.util.ArrayList;
import java.util.List;

import com.pear.cdn.interfaces.PrefetchDataResourceInteface;

/**
 * 模拟需要缓冲的数据
 * */
public class PrefetchDataResourceSimulationImpl extends PrefetchDataResourceInteface{
 
	protected  List<String> getVideo(long startTime,long endTime)
	{
		return new ArrayList<String>() 
		{{
			add("/data/video/女神/2018-01-27/貪欲な美肌胸女.mp4");
			add("/data/video/女神/2018-01-27/1.jpg");
			add("/data/video/自拍/2018-01-27/大学90后大学生，奶子真白叫声真给力,国语对白.mp4");
			add("/data/video/女神/2018-01-27/1.jpg");
		}};
	} 	
	
	protected  List<String> getImage(long startTime,long endTime)
	{
		return new ArrayList<String>() 
		{{
			add("/data/picture/2018-01-27/图片/床戏/1.jpg");
		}};
	} 	
	
	protected  List<String> getFriends(long startTime,long endTime)
	{
		return new ArrayList<String>() 
		{{
			add("/data/friends/。。年轻活好，体力好···/1.jpg");
		}};
	} 	
	
	protected  List<String> getFiction(long startTime,long endTime)
	{
		return new ArrayList<String>() 
		{{
			add("/data/fiction/2018-01-27/小说/1.jpg");
		}};
	} 	
	
}
