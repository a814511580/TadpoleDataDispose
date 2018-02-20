package com.pear.crwaler.servoce;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.pear.common.utils.media.ThumbnailatorUtils;
import com.pear.common.utils.net.DownloadImage;
import com.pear.common.utils.system.FileUtils;
import com.pear.crwaler.maomi.servoce.RequestService;
import com.pear.crwaler.vo.FriendVo;
import com.pear.crwaler.vo.SmallcatFriendTypeVo;

import net.sf.json.JSONObject;

/**
 * 抓取猫咪朋友圈
 */
public class SmallCatByFriendCrwaler {

	static int allDownloadCount=0;
	
	/**
	 * 处理一个分类信息
	 * @param startPage 开始抓取的页数
	 * @throws InterruptedException
	 */
	protected void disposeByType(String savePath, SmallcatFriendTypeVo typeVo,int startPage) throws InterruptedException {
		int tryCount=5;
		for (int page = startPage; true; page++) {
			System.out.println("page:" + page+",type :"+typeVo.getType());
			List<FriendVo> list = RequestService.getAssignCircle(typeVo.getMcId(), page);
			if (list.isEmpty())
				tryCount--;
			else
				tryCount=5;
			if(0>tryCount)
				break;
			for(FriendVo friend:list) 
			{
				downloadAndSave(savePath+File.separator+typeVo.getType()+File.separator,friend);
			}
			//if(true)
			//	break;
		}
		System.out.println(typeVo.getType() + " 抓取完成");
	}
	
	protected void disposeByType(String savePath, SmallcatFriendTypeVo typeVo) throws InterruptedException
	{
		disposeByType(savePath,typeVo,1);
	}
	
	/**
	 * 下载数据和保存数据
	 * 
	 * @param path
	 *            保存的路径
	 * @param friendVo
	 *            当前这条朋友圈数据
	 */
	void downloadAndSave(String savePath, FriendVo friendVo) {
		try {
			System.out.println("allDownloadCount:"+  (++allDownloadCount)+" ,正在下载:"+friendVo.getTitle());
			savePath+=File.separator+friendVo.getTitle()+"_time_"+System.currentTimeMillis();
			new File(savePath).mkdirs();
			List<String> imageUrls = friendVo.getImages();
			for (int i = 0; i < imageUrls.size(); i++) {
				String saveJpgPath= savePath + File.separator + (i + 1) + ".jpg";
				DownloadImage.download(imageUrls.get(i),saveJpgPath);
				ThumbnailatorUtils.thumb(saveJpgPath, saveJpgPath);
			}
			String objJson = JSONObject.fromObject(friendVo).toString(); 
			FileUtils.writeText2File(savePath + File.separator + "data.txt", objJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
