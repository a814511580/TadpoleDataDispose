package com.pear.cdn.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要提前提取的数据来源接口
 * */
public abstract class PrefetchDataResourceInteface {
	
	/**
	 * 获取常用的静态数据
	 * @note 例如简介的图片，官网的背景图等。
	 * */
	protected abstract List<String> getCommonStaticUse(); 

	/**
	 * 获取电影的资源，截图和电影数据
	 * */
	protected abstract List<String> getVideo(long startTime,long endTime); 	
	
	/**
	 * 获取图片资源
	 * */
	protected abstract List<String> getImage(long startTime,long endTime);
		
	/**
	 * 获取朋友圈数据
	 * */
	protected abstract List<String> getFriends(long startTime,long endTime);
	
	/**
	 * 获取小说数据
	 * */
	protected abstract List<String> getFiction(long startTime,long endTime); 
	
	
	/**
	 * 获取所有数据
	 * @param startTime 开始时间范围作为起始点 
	 * @param endTime 开始时间范围作为终点
	 * */
	public List<String> getAll(long startTime,long endTime)
	{
		List<String> allPaths=new ArrayList<>();
		allPaths.addAll(new ArrayList<String>(getCommonStaticUse()));
		allPaths.addAll(new ArrayList<String>(getVideo(startTime,endTime)));
		allPaths.addAll(new ArrayList<String>(getImage(startTime,endTime)));
		allPaths.addAll(new ArrayList<String>(getFriends(startTime,endTime)));
		allPaths.addAll(new ArrayList<String>(getFiction(startTime,endTime)));
		return allPaths;
	}
	
}
