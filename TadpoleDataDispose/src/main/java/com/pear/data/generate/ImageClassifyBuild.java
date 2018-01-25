package com.pear.data.generate;

import java.io.File;
import java.util.List;

import com.pear.data.vo.ImageClassifyVo;

/**
 * 图片类型的数据生产
 * */
public class ImageClassifyBuild { 
	
	/**
	 * 获取指定路径下的所有分类图片数据
	 * @throws IllegalAccessException 
	 * */
	List<ImageClassifyVo> getAllClassify(String path) throws IllegalAccessException
	{
		String[] calssifys=new File(path).list();
		for(String calssify:calssifys) 
		{
			if(!new File(calssify).isDirectory())
				throw new IllegalAccessException("为啥这个不是文件夹:"+calssify);
			
		}
		return null;
	}	
	
	/**
	 * 生产所有图片数据。
	 * @param datas 现有数据库
	 * */
	void buildClassify(List<ImageClassifyVo> datas)
	{
		
	}
	
	/**
	 * 提取数据生产一天的数据出来。
	 * @param date 生产那一天的数据
	 * @param datas 现有数据库
	 * */
	void buildClassifyByDate(long date,List<ImageClassifyVo> datas)
	{
		
	}
	
}
