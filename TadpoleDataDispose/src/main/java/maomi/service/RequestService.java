package maomi.service;

import org.json.JSONObject;

import maomi.Test;

/**
 * 服务器的查询接口
 */
public class RequestService {

	/** 请求地址前缀 */
	public static final String UrlPrefix = "http://api.mehuapi.com:8089";

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
	 */
	public void getAssignCircle(String circle) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
				"");
		jsonObj.put("mcId", circle);
		jsonObj.put("page", 2);
		jsonObj.put("perPage", 10);
		jsonObj.put("uId", "");
		jsonObj.put("isHot", "0");
		jsonObj.put("isGood", "0");
		jsonObj.put("isNew", "1");
		new Test().getPostData(AttentionCommunity, jsonObj);
	}

	/**
	 * 获取评论数据
	 */
	public void getListCommentsData() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
				"");
		jsonObj.put("maId", "1040861");
		jsonObj.put("authorId", "");
		jsonObj.put("page", 1);
		jsonObj.put("perPage", 10);
		jsonObj.put("type", "0");
	      
		new Test().getPostData(ListComments, jsonObj);
	}

}
