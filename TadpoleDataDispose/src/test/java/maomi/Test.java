package maomi;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
 
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import maomi.utils.AesEncryptionUtil;
import maomi.utils.EncryptUtils;
import okhttp3.Call;
import okhttp3.Request;

public class Test {

  public PostFormBuilder getParams(String dataParam)
  {
	TreeMap<String, String> params = new TreeMap(); 
	params.put("data", dataParam);
    /*
	params.put("_device_id", AppUtils.getAndroidID(AppUtils.getAppContext()));
    params.put("_app_version", AppUtils.getAppVersionName(AppUtils.getAppContext()));
    params.put("_device_type", AppUtils.getModel());
    params.put("_sdk_version", AppUtils.getSDKVersion());
    params.put("_device_version", AppUtils.getOSVersion());
    */
    Object localObject = new StringBuilder("");
    Iterator localIterator = params.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      ((StringBuilder)localObject).append("&").append((String)localEntry.getKey()).append("=").append(EncodeUtils.urlEncode((String)localEntry.getValue(), "UTF-8"));
    }
    localObject = ((StringBuilder)localObject).toString().substring(1);
    localObject = EncryptUtils.encryptMD5ToString((String)localObject + "maomi_pass_xyz");
    params.put("sig", localObject);
    return this;
  }
  
	public void getPostData(String url, JSONObject paramObj) {
		try {
			String param = AesEncryptionUtil.encrypt(
					paramObj.toString(), 
					"625202f9149maomi", 
					"5efd3f6060emaomi");
			getParams(param)
			.url(url).build().execute(new StringCallback() {
				public void onAfter(int paramAnonymousInt) {
					super.onAfter(paramAnonymousInt);
				}

				public void onBefore(Request paramAnonymousRequest, int paramAnonymousInt) {
					super.onBefore(paramAnonymousRequest, paramAnonymousInt);
				}

				public void onError(Call paramAnonymousCall, Exception paramAnonymousException, int paramAnonymousInt) {
					System.out.println("AAAAA");
					System.out.println(paramAnonymousException.toString());
				}

				public void onResponse(String paramAnonymousString, int paramAnonymousInt) {
					try {
						JSONObject jsonObj = new JSONObject(AesEncryptionUtil.decrypt(paramAnonymousString,
								"625202f9149maomi", "5efd3f6060emaomi"));
						System.out.println("jsonObj:" + jsonObj.toString());
						int code = paramAnonymousInt = jsonObj.getInt("code");
						System.out.println("code:" + code);
					} catch (JSONException exp) {
						exp.printStackTrace();
						return;
					}
				}
			});
			return;
		} catch (JSONException localJSONException) {
		}
	} 
	
	void getList()
	{
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("page", 1);
		jsonObj.put("perPage", 10);
		jsonObj.put("uId", /** UserSPManager.getCurrentUser().getMu_id() */
					"");
		jsonObj.put("isNew", "0");	
		String url = "http://api.mehuapi.com:8089/api/community/listArticles";
		new Test().getPostData(url,jsonObj);
	}
	
	void login()
	{
		JSONObject jsonObj = new JSONObject();  
		jsonObj.put("email", "1306820198@qq.com");
		jsonObj.put("password", "llikeluojen1412");
		//String url = "http://api.mehuapi.com:8089/api/users/login";
		String url = "https://123.huhul1l.com/api/users/login"; 
		new Test().getPostData(url,jsonObj);
	}
	
	 class DnsGet
	 { 
	 }
	  
	
	public static void main(String[] args) {
		// "api.mehuapi.com", "api.huhuapi.com", "aapi.huhusb.com", "666.hulili123.com",
		// "888.huhu0o0.com", "api.yeseseapi.com", "888.yssapi.com"

		//new Test().getList();
		new Test().login();
		
		/*
		String testValue="smgui";
		String encodeStr=AesEncryptionUtil.encrypt(testValue, "625202f9149maomi", "5efd3f6060emaomi");
		String decodeStr=AesEncryptionUtil.decrypt(encodeStr,
				"625202f9149maomi", "5efd3f6060emaomi");
		
		System.out.println(testValue);
		System.out.println(encodeStr);
		System.out.println(decodeStr);
		*/
	}

}
