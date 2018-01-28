package com.pear.cdn.services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.pear.cdn.impl.PrefetchDataResourceSimulationImpl;
import com.pear.cdn.interfaces.PrefetchDataResourceInteface;
import com.pear.common.utils.net.HttpRequest; 

import net.sf.json.JSONObject;

/**
 * CDN数据提前提取缓存
 * */
public class PrefetchService {

	/**日记*/
	private static Logger log = Logger.getLogger(PrefetchService.class);
	
	/**模拟假数据的查询实现。*/
	private PrefetchDataResourceInteface dataResource
		=new PrefetchDataResourceSimulationImpl(); 
	
	/**
	 * 异步启动该线程。
	 * */
	public static void start() 
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new PrefetchService().loopDisposePrefetch();
			}
		}).start();
	}
	
	
	/**
	 * 循环处理缓冲
	 * */
	private void loopDisposePrefetch() 
	{
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				long startTime=System.currentTimeMillis();  
				long hourFor48Milli=1000*60*60*24*2;
				long endTime=startTime+hourFor48Milli; 
				List<String> paths=dataResource.getAll(startTime, endTime);
				System.out.println(paths.toString());
				prefetch(paths);
			}
		};
		
		long hourFor12Milli=1000*60*60*12; 
        Timer timer = new Timer();  
        /**立即开始，然后每12小时循环调用一次*/
        timer.schedule(task, 0, hourFor12Milli);  
	} 

	/**
	 * 提取所有文件
	 */
	private static boolean prefetch(List<String> paths) {
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


}
