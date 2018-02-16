package com.pear.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtilsTest extends FileUtils{

	
	static void getAllFilesByRecursionFolder() 
	{
		List<String> list=new ArrayList<>();
		File file=new File("C:\\Users\\su\\Desktop\\测试视频\\输入");
		//fillRelativeFolderPathList(list,file,file);
		getAllFilesByRecursionFolder(list,file);
		for(String path:list)
			System.out.println(path);
	}
	
	public static void main(String[] args) throws IOException 
	{
		//String path="E:\\更多常用项目\\雪梨\\小说\\小说文本";
		//List<String> list=getAllFilesByFolder(new File(path));
		//System.out.println(list);
		
		String path="E:\\更多常用项目\\雪梨\\小说\\小说文本\\爱穿短裙的新邻居.txt";
		String content=FileUtils.readTxtByFile(path,"utf-8");
		System.out.println(content); 
 
	}
	 
}
