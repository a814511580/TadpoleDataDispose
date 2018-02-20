package com.pear.crwaler.maomi.servoce;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pear.crwaler.maomi.utils.RequestUtils;
import com.pear.crwaler.vo.FriendVo;
import com.pear.data.vo.FictionVo;
import com.pear.data.vo.ImageListVo;
import com.zhy.http.okhttp.callback.StringCallback;

import maomi.Test;
import okhttp3.Call;

/**
 * 猫咪的小说抓取
 * */
public class FictionRequestService {
	/** 请求地址前缀 */
	public static final String UrlPrefix = "http://api.mehuapi.com:8089";
	//public static final String UrlPrefix = "https://123.huhul1l.com";
	  
	private static String TYPE_LIST_API = UrlPrefix+"/api/cats/getChildList";   
	
	private static String NOVELS_LIST_API = UrlPrefix+"/api/novels/listAll";
	
	/**
	 * 获取小说的分类
	 */
	public void getFictionType() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("parentId", "2");
		new Test().getPostData(TYPE_LIST_API, jsonObj);
	} 

	/**
	 * 获取指定圈子的数据
	 * @throws InterruptedException 
	 */
	public static List<FictionVo> getList(String circle,Integer page) throws InterruptedException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("mc_id", circle);
		jsonObj.put("page", page);
		jsonObj.put("perPage", 20);
		Object lock=new Object();
		final List<FictionVo> fictionList=new ArrayList<FictionVo>();
		synchronized (lock) { 
			RequestUtils.getPostData(NOVELS_LIST_API, jsonObj,new StringCallback() {
				
				@Override
				public void onResponse(String jsonStr, int arg1) {
					//System.out.println(jsonStr); 
					 
					JSONArray arrays=new JSONObject(jsonStr).getJSONArray("list");
					for(int i=0;i<arrays.length();i++)
					{
						JSONObject jsonObject=arrays.getJSONObject(i);
						String title=jsonObject.getString("mn_title");
						String content_url=jsonObject.getString("mn_content");
						//尼玛，别偷懒啊，别用ip，用域名好不好。
						content_url=content_url.substring(content_url.indexOf("uploads")); 
						content_url="http://103.244.148.101:8090/"+content_url;
						fictionList.add(new FictionVo(title, content_url));
					} 
					unlock();
				}
				
				@Override
				public void onError(Call arg0, Exception arg1, int arg2) {
					unlock();	
				}
				
				void unlock()
				{
					synchronized (lock) {
						lock.notifyAll();
					}
				}
			});
			lock.wait();
		}
		return fictionList;
	}
	 
}
