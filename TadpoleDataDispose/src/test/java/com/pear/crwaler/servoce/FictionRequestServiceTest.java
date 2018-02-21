package com.pear.crwaler.servoce;

import java.util.List;

import com.pear.common.utils.net.DownloadImage;
import com.pear.crwaler.maomi.servoce.FictionRequestService;
import com.pear.crwaler.maomi.servoce.ImageRequestService;
import com.pear.crwaler.vo.SmallcatFriendTypeVo;
import com.pear.data.vo.FictionVo;
import com.pear.data.vo.ImageListVo;

public class FictionRequestServiceTest extends FictionRequestService{

	public static void fictionType() 
	{
		new FictionRequestService().getFictionType();
	}
	
	public static void resourceType() 
	{
		new ImageRequestService().getResourceType();
	} 
	
	public static void list() throws InterruptedException 
	{
		List<FictionVo> list=getList("7",47);
		System.out.println(list); 
	} 
	
	static void disposeByType() throws InterruptedException
	{
		String savePath="F:\\数据抓取\\猫咪\\小说"; 
		/*
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("人妻交换","8"),40);
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("都市激情","7"));
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("家庭乱伦","13"));
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("校园春色","12")); 
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("性爱技巧","14"));
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("另类小说","34")); 
		new SmallCatByFictionCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("武侠经典","33")); 
		*/
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		//fictionType();
		list();
		//disposeByType();
		System.out.println("done");
	}
	
	
}
