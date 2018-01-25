package com.pear.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 文件工具
 * */
public class FileUtils {  
	
	/**
	 * 获取文件下的所有文件路径
	 * @param file 待查找的文件路径
	 * @param findSub 是否递归把子节点也找出来
	 * */
	public static List<String> getAllFilesByFolder(File folder)
	{		
		List<String> list=new ArrayList<String>();
		File files[] = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File subFile = files[i];
			if (!subFile.isFile()) 
				continue; 
			list.add(subFile.getPath());
		}
		return list;
	}
	

	/**
	 * 从指定的文件中读取文本数据
	 * @param file 待读取的文件
	 * @param encode 读取的编码
	 * @throws IOException 
	 */
	public static String readTxtByFile(String file,String encode)
			throws IOException {  
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encode);// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		StringBuffer content = new StringBuffer();
		String lineTxt; 
		while ((lineTxt = bufferedReader.readLine()) != null) {
			content.append(lineTxt + ("\n"));
		}
		read.close();
		return content.toString(); 
	} 
	

	/**
	 * 把文本写出到文件去
	 * @param path 写出的文件路径
	 * @param content 写出的内容
	 * */
	public static void writeText2File(String path, String content) throws IOException { 
		FileWriter fileWriter = new FileWriter(path);
		fileWriter.write(content);
		fileWriter.flush();
		fileWriter.close();
	}
	
}
