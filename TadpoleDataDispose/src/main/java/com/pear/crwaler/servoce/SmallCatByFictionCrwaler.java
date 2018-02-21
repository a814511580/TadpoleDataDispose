package com.pear.crwaler.servoce;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.pear.common.utils.media.ThumbnailatorUtils;
import com.pear.common.utils.net.DownloadImage;
import com.pear.common.utils.system.FileUtils;
import com.pear.crwaler.maomi.servoce.FictionRequestService;
import com.pear.crwaler.maomi.servoce.ImageRequestService;
import com.pear.crwaler.maomi.servoce.RequestService;
import com.pear.crwaler.vo.FriendVo;
import com.pear.crwaler.vo.SmallcatFriendTypeVo;
import com.pear.data.vo.FictionVo;
import com.pear.data.vo.ImageListVo;

import net.sf.json.JSONObject;

/**
 * 抓取猫咪小说
 */
public class SmallCatByFictionCrwaler {

	static int allDownloadCount=0;
	
	/**
	 * 处理一个分类信息
	 * 
	 * @throws InterruptedException
	 */
	protected void disposeByType(String savePath, SmallcatFriendTypeVo typeVo,int startPage) throws InterruptedException {
		for (int page = startPage; true; page++) {
			System.out.println("page:" + page+",type :"+typeVo.getType());
			List<FictionVo> list = FictionRequestService.getList(typeVo.getMcId(), page);
			if (list.isEmpty())
				break;
			for(FictionVo vo:list) 
			{
				//还在抓朋友圈，这里缓一下吧
				Thread.sleep(2000);
				downloadAndSave(savePath+File.separator+typeVo.getType()+File.separator,vo);
			}
			//if(true)
			//	break;
		}
		System.out.println(typeVo.getType() + " 抓取完成");
	}
	
	protected void disposeByType(String savePath, SmallcatFriendTypeVo typeVo) throws InterruptedException 
	{
		disposeByType(savePath, typeVo,1);
	}

	/**
	 * 下载数据和保存数据
	 * 
	 * @param path
	 *            保存的路径
	 * @param friendVo
	 *            当前这条朋友圈数据
	 */
	void downloadAndSave(String savePath, FictionVo fictionVo) {
		try {
			System.out.println("allDownloadCount:"+  (++allDownloadCount)+" ,正在下载:"+fictionVo.getTitle());
			//savePath+=File.separator+fictionVo.getTitle();
			new File(savePath).mkdirs();
			String saveFictionPath= savePath + File.separator + fictionVo.getTitle() + ".txt";
			DownloadImage.download(fictionVo.getContent(),saveFictionPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
