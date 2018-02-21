package com.pear.crwaler.servoce;

import java.util.List;

import com.pear.crwaler.maomi.servoce.ImageRequestService;
import com.pear.crwaler.vo.SmallcatFriendTypeVo;
import com.pear.data.vo.ImageListVo;

public class ImageRequestServiceTest extends ImageRequestService{

	public static void imageType() 
	{
		new ImageRequestService().getImageType();
	}
	
	public static void resourceType() 
	{
		new ImageRequestService().getResourceType();
	} 
	
	public static void imageList() throws InterruptedException 
	{
		List<ImageListVo> list=new ImageRequestService().getImageList("9", 2);
		System.out.println(list);
	} 
	  
	static void disposeByType() throws InterruptedException
	{
		String savePath="F:\\数据抓取\\猫咪\\图片";
		new SmallCatByImageCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("清纯唯美","30"),16);
		new SmallCatByImageCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("美腿丝袜","10"),32);
		new SmallCatByImageCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("卡通动漫","11"),33);
		new SmallCatByImageCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("原创自拍","5"),558);
		new SmallCatByImageCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("亚洲色图","6"),420);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		//resourceType();
		//imageType();
		//imageList();
		disposeByType();
		System.out.println("done");
	}
	
	
}
