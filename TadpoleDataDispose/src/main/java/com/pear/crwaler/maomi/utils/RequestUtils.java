package com.pear.crwaler.maomi.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.pear.crwaler.maomi.utils.encrypt.AesEncryptionUtil;
import com.pear.crwaler.maomi.utils.encrypt.EncryptUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * 猫咪的请求处理
 * */
public class RequestUtils { 
	  
	/**
	 * 发送请求
	 * @param url 发送的地址
	 * @param paramObj 参数json组
	 * @param callback 处理结果回调
	 * */
	public static void getPostData(final String url,final JSONObject paramObj,final StringCallback callback) {
		try {
			String dataParam = AesEncryptionUtil.encrypt(
					paramObj.toString(), 
					"625202f9149maomi", 
					"5efd3f6060emaomi");
			
			String sig=EncryptUtils.encryptMD5ToString("data=" +dataParam+ "maomi_pass_xyz");
			OkHttpUtils.post()
			.addParams("data", dataParam)
			.addParams("sig", sig)
			.url(url)
			.build().execute(new StringCallback() { 

				public void onError(Call paramAnonymousCall, Exception paramAnonymousException, int paramAnonymousInt) {
					System.out.println("请求出错了");
					System.out.println(paramAnonymousException.toString());
					//报错是吧，服务器正在维护，确认猫咪也没办法进去，这里无限循环执行就行了。
					System.out.println("报错是吧，服务器正在维护，确认猫咪也没办法进去，这里无限循环执行就行了。"); 
					//睡眠10分钟
					try {
						Thread.sleep(60*10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					getPostData(url,paramObj,callback);
					//callback.onError(paramAnonymousCall, paramAnonymousException,paramAnonymousInt); 
				}

				public void onResponse(String response, int paramAnonymousInt) {
					try {
						JSONObject jsonObj = new JSONObject(AesEncryptionUtil.decrypt(response,
								"625202f9149maomi", "5efd3f6060emaomi"));
						//System.out.println("jsonObj:" + jsonObj.toString());
						//int code = paramAnonymousInt = jsonObj.getInt("code");
						//System.out.println("code:" + code);
						String data=jsonObj.getJSONObject("data").toString();
						callback.onResponse(data, paramAnonymousInt); 
					} catch (JSONException exp) {
						exp.printStackTrace();
						return;
					}
				}
			});
			return;
		} catch (JSONException exp) {
			exp.printStackTrace();
		}
	}
	
	
}
