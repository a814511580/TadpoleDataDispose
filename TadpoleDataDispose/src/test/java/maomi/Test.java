package maomi;

import org.json.JSONException;
import org.json.JSONObject;

import com.pear.crwaler.maomi.utils.encrypt.AesEncryptionUtil;
import com.pear.crwaler.maomi.utils.encrypt.EncryptUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public class Test { 
  
	public void getPostData(String url, JSONObject paramObj) {
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
	

	 class DnsGet
	 { 
	 }
	  
	
	public static void main(String[] args) {
		// "api.mehuapi.com", "api.huhuapi.com", "aapi.huhusb.com", "666.hulili123.com",
		// "888.huhu0o0.com", "api.yeseseapi.com", "888.yssapi.com"

		//new Test().getList();
		//new Test().login();
		
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
