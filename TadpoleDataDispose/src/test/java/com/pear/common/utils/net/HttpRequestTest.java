package com.pear.common.utils.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class HttpRequestTest {

	/**
	 * 日记
	 */
	private static Logger log = Logger.getLogger(HttpRequestTest.class);

	/**
	 * 提取所有文件
	 */
	public static boolean prefetch(List<String> paths) {
		try {
			StringBuffer urlParams = new StringBuffer();
			for (String path : paths)
				urlParams.append("&url[]=" + path);
			String response = HttpRequest.sendPost("https://api.cdn77.com/v2.0/data/prefetch",
					"cdn_id=132971&login=a15774991382@outlook.com&passwd=EAn23RTBHZsVtfYrIG164WwD7Uz5bMNJ" 
							+ urlParams);
			response=response.trim();
			System.out.println("response:"+response);
			String status = new JSONObject().fromObject(response).getString("status");
			return "ok".equals(status);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	/*
	 * "cdn_id=xxx&login=name@domain.com&passwd=your_api_password
	 * &url[]=\/images\/hello.jpg&url[]=anotherimage.jpg"
	 * 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		List<String> paths = new ArrayList<String>() {
			{
				add("/data/小 说/1.jpg");
				add("/data/小说/2.jpg");
			}
		};
		boolean result = prefetch(paths);

		System.out.println(result);

		/*
		 * 
		 * String
		 * response=HttpRequest.sendPost("https://api.cdn77.com/v2.0/data/prefetch",
		 * "cdn_id=132971&login=a15774991382@outlook.com&passwd=EAn23RTBHZsVtfYrIG164WwD7Uz5bMNJ&url[]=/images/hello.jpg"
		 * );
		 * 
		 * String
		 * response=HttpRequest.sendGet("https://api.cdn77.com/v2.0/storage/list",
		 * "login=a15774991382@outlook.com&passwd=EAn23RTBHZsVtfYrIG164WwD7Uz5bMNJ" );
		 * System.out.println(response);
		 */

	}

	/**
	 * { "status":"ok", "description":"Request was successful.", "storages":[ {
	 * "id":"user_a0bkz1q3", "zone_name":"mir_just_test",
	 * "storage_location_id":"push-24.cdn77.com", "used_space":"0 B",
	 * "cdn_resources":[
	 * 
	 * ], "credentials":{ "protocol":"FTP, SFTP", "host":"push-24.cdn77.com",
	 * "user":"user_a0bkz1q3", "pass":"3MblKUJp67WbTw0y06rz" } }, {
	 * "id":"user_w04zjza5", "zone_name":"tadpole",
	 * "storage_location_id":"push-19.cdn77.com", "used_space":"4.12 GB",
	 * "cdn_resources":[ 132971 ], "credentials":{ "protocol":"FTP, SFTP",
	 * "host":"push-19.cdn77.com", "user":"user_w04zjza5",
	 * "pass":"4zjVw28KI1UhgmwvgH6s" } } ] }
	 */

}
