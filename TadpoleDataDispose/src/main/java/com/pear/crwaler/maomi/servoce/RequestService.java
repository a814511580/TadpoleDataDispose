package com.pear.crwaler.maomi.servoce;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pear.crwaler.maomi.utils.RequestUtils;
import com.pear.crwaler.vo.FriendVo;
import com.zhy.http.okhttp.callback.StringCallback;

import maomi.Test;
import okhttp3.Call;

/**
 * 服务器的查询接口
 */
public class RequestService {

	
	//api.mehuapi.com
	
	//aapi.huhusb.com
	/** 请求地址前缀 */
	public static final String UrlPrefix = "http://api.mehuapi.com:8089";
	/**
	 * 
	 * DOMAINLIST = new ArrayList(Arrays.asList(new String[] { 
	 * "api.mehuapi.com", 
	 * "api.huhuapi.com",
	 *  "aapi.huhusb.com", 
	 *  "666.hulili123.com",
	 *   "888.huhu0o0.com",
	 *    "api.yeseseapi.com", 
	 *    "888.yssapi.com" }));
    
	 * */

	/** 登录 */
	public static final String Login = UrlPrefix + "/api/users/login";

	/** 推荐圈子 */
	public static final String RecommendCommunity = UrlPrefix + "/api/community/recommendCommunity";

	/** 指定圈子 */
	public static final String AttentionCommunity = UrlPrefix + "/api/community/listArticles";

	/** 获取指定内容的评论 */
	public static final String ListComments = UrlPrefix +"/api/Community/listComments";

	/**
	 * 登录用户
	 */
	public void login() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("email", "1306820198@qq.com");
		jsonObj.put("password", "llikeluojen");
		String url = "http://api.mehuapi.com:8089/api/users/login";
		new Test().getPostData(url, jsonObj);
	}

	public void getList() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("page", 1);
		jsonObj.put("perPage", 10);
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
				"");
		jsonObj.put("isNew", "0");
		String url = "http://api.mehuapi.com:8089/api/community/listArticles";
		new Test().getPostData(url, jsonObj);
	}

	/**
	 * 获取推荐的圈子
	 */
	public void getRecommendCommunity() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
				"");
		new Test().getPostData(RecommendCommunity, jsonObj);
	}

	/**
	 * 获取指定圈子的数据
	 * @throws InterruptedException 
	 */
	public static List<FriendVo> getAssignCircle(String circle,Integer page) throws InterruptedException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
				"");
		jsonObj.put("mcId", circle);
		jsonObj.put("page", page);
		jsonObj.put("perPage", 10);
		jsonObj.put("uId", "");
		jsonObj.put("isHot", "0");
		jsonObj.put("isGood", "0");
		jsonObj.put("isNew", "1");
		Object lock=new Object();
		final List<FriendVo> friendList=new ArrayList<FriendVo>();
		synchronized (lock) { 
			RequestUtils.getPostData(AttentionCommunity, jsonObj,new StringCallback() {
				
				@Override
				public void onResponse(String jsonStr, int arg1) {
					JSONArray arrays=new JSONObject(jsonStr).getJSONArray("list");
					for(int i=0;i<arrays.length();i++)
					{
						JSONObject jsonObject=arrays.getJSONObject(i);
						String title=jsonObject.getString("ma_title");
						List<Object> maImages=jsonObject.getJSONArray("ma_images").toList();
						List<String> images=new ArrayList<>();
						for(Object maImage:maImages)
							images.add((String)maImage);
						String maId=jsonObject.getString("ma_id");
						List<String> comments=new ArrayList<>();
						try {
							comments = getListCommentsData(maId);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						boolean isGood=jsonObject.getString("ma_isgood").equals("1");
						boolean isHot=jsonObject.getString("ma_ishot").equals("1");
						friendList.add(new FriendVo(title, images, comments,isGood,isHot));
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
		return friendList;
	}

	/**
	 * 获取评论数据
	 * @throws InterruptedException 
	 */
	public static List<String> getListCommentsData(String maId) throws InterruptedException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
				"");
		jsonObj.put("maId", maId);
		jsonObj.put("authorId", "");
		jsonObj.put("page", 1);
		jsonObj.put("perPage", 10);
		jsonObj.put("type", "0");
		
		Object lock=new Object();
		final List<String> commentList=new ArrayList<String>();
		synchronized (lock) { 
			RequestUtils.getPostData(ListComments, jsonObj,new StringCallback() {
				
				@Override
				public void onResponse(String jsonStr, int arg1) {
					JSONArray arrays=new JSONObject(jsonStr).getJSONArray("list");
					for(int i=0;i<arrays.length();i++)
					{
						JSONObject jsonObject=arrays.getJSONObject(i);
						String comment=jsonObject.getString("mc_text");
						commentList.add(comment);
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
		return commentList; 
	}

}
