package com.pear.crwaler.servoce;

import java.util.ArrayList;
import java.util.List;

import com.pear.crwaler.maomi.servoce.RequestService;
import com.pear.crwaler.vo.FriendVo;
import com.pear.crwaler.vo.SmallcatFriendTypeVo;

public class SmallCatByFriendCrwalerTest extends SmallCatByFriendCrwaler{

	/**所有有价值的圈子数据*/
	List<SmallcatFriendTypeVo> types=new ArrayList<SmallcatFriendTypeVo>() 
	{{
		//add(new SmallcatFriendTypeVo());
	}}; 
	
	static void disposeByType() throws InterruptedException
	{
		String savePath="F:\\数据抓取\\猫咪\\朋友圈";
		//3491
		new SmallCatByFriendCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("原创自拍","7"),3491);
		new SmallCatByFriendCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("我爱我妻","6"),508);
		new SmallCatByFriendCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("女同志","12"));
		new SmallCatByFriendCrwaler().disposeByType(savePath,new SmallcatFriendTypeVo("色区美图","8")); 
	}
	
	static void testPage() throws InterruptedException
	{
		List<FriendVo> list = RequestService.getAssignCircle("7", 14250);
		System.out.println(list.toString());
	}
	
	public static void main(String[] args) throws InterruptedException {
		disposeByType();
		//testPage();
		System.out.println("done");
	}
	
	
}
