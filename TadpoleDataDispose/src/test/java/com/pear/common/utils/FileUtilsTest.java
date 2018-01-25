package com.pear.common.utils;

import java.io.IOException;

public class FileUtilsTest {

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
