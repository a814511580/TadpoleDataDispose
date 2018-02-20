package com.pear.crwaler.maomi.servoce;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pear.crwaler.maomi.utils.RequestUtils;
import com.pear.crwaler.vo.FriendVo;
import com.pear.data.vo.ImageListVo;
import com.zhy.http.okhttp.callback.StringCallback;

import maomi.Test;
import okhttp3.Call;

/**
 * 猫咪的图片抓取
 * */
public class ImageRequestService {
	/** 请求地址前缀 */
	public static final String UrlPrefix = "http://api.mehuapi.com:8089";
 
	private static String TYPE_LIST_API = UrlPrefix+"/api/cats/getChildList";  
	
	private static String RESOURCE_DATA_TYPE = UrlPrefix+"/api/resource/newCatsList";
	
	private static String PHOTOS_LIST_API = UrlPrefix+"/api/photos/listAll";
	
	/**
	 * 获取推荐的圈子
	 */
	public void getImageType() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("parentId", "1");
		new Test().getPostData(TYPE_LIST_API, jsonObj);
	}
	
	/**
	 * 获取所有资源类别
	 */
	public void getResourceType() {
		JSONObject jsonObj = new JSONObject();
		new Test().getPostData(RESOURCE_DATA_TYPE, jsonObj);
	}	

	/**
	 * 获取指定圈子的数据
	 * @throws InterruptedException 
	 */
	public static List<ImageListVo> getImageList(String circle,Integer page) throws InterruptedException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("mc_id", circle);
		jsonObj.put("page", page);
		jsonObj.put("perPage", 10);
		Object lock=new Object();
		final List<ImageListVo> imageList=new ArrayList<ImageListVo>();
		synchronized (lock) { 
			RequestUtils.getPostData(PHOTOS_LIST_API, jsonObj,new StringCallback() {
				
				@Override
				public void onResponse(String jsonStr, int arg1) {
					//System.out.println(jsonStr); 
					JSONArray arrays=new JSONObject(jsonStr).getJSONArray("list");
					for(int i=0;i<arrays.length();i++)
					{
						JSONObject jsonObject=arrays.getJSONObject(i);
						String title=jsonObject.getString("mp_title");
						List<Object> maImages=jsonObject.getJSONArray("mp_content").toList();
						List<String> images=new ArrayList<>();
						for(Object maImage:maImages)
							images.add((String)maImage);
						imageList.add(new ImageListVo(title, images));
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
		return imageList;
	}
	 
}
