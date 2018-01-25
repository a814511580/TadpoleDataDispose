package com.pear.data.generate;

import java.io.IOException;
import java.util.List;

import com.pear.common.utils.FileUtils;
import com.pear.data.config.SystemConfig;
import com.pear.data.vo.FictionVo;
import com.pear.data.vo.ImageListVo;

public class FictionBuildTest {
	
	void splitContentBestByImageCount() throws IOException 
	{
		String path="E:\\更多常用项目\\雪梨\\小说\\小说文本\\爱穿短裙的新邻居.txt";
		String content =FileUtils.readTxtByFile(path, SystemConfig.ENCODE);
		
		List<String> splitContents
			=new FictionBuild().splitContentBestByImageCount(content,10); 
		System.out.println("splitContents size:"+splitContents.size());
		for(String subContent:splitContents) 
		{
			System.out.println("\n\n\n\n\n\n");
			System.out.println("size:"+subContent.length()
			+"*************************************");
			System.out.println(subContent);
		} 
	}	
	
	void getFictionByPath() throws IOException
	{
		String path="E:\\更多常用项目\\雪梨\\小说\\小说文本";
		List<FictionVo> fictions
			=new FictionBuild().getFictionByPath(path);
		for(FictionVo fiction:fictions) 
		{
			System.out.println(fiction.getContent().length());
		}
	}
	
	void getImageListByPath() throws IOException
	{
		String path="E:\\更多常用项目\\雪梨\\小说\\小说图片";
		List<ImageListVo> images
			=new FictionBuild().getImageListByPath(path);
		for(ImageListVo image:images) 
		{
			System.out.println(image.getPaths().size());
		}
	}
	
	public void createdOnce() throws IOException 
	{
		FictionBuild build=new FictionBuild();
		List<FictionVo> fiction=build.getFictionByPath("E:\\更多常用项目\\雪梨\\小说\\小说文本"); 
		List<ImageListVo> imageList
			=new FictionBuild().getImageListByPath("E:\\更多常用项目\\雪梨\\小说\\小说图片");
		
		long publicDate=System.currentTimeMillis();
		String outputPath="E:\\更多常用项目\\雪梨\\小说\\小说整合";
		build.createdOnce(fiction.get(0),imageList.get(0),publicDate,outputPath);
	}
	
	
	void numberAvgTest() 
	{
		int number=3499;
		int count=9;
		
		int avg=number/count;
		for(int i=1;i<count;i++)
		{
			System.out.println(avg*i);
		}
		
	}
	
	public static void main(String[] args) throws IOException
	{
		FictionBuildTest build=new FictionBuildTest();
		
		//build.splitContentBestByImageCount();
		//build.getFictionByPath();
		//build.getImageListByPath();

		build.createdOnce();
		//build.numberAvgTest() ;
	}
	
}
