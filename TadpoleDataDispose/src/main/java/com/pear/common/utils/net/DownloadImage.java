package com.pear.common.utils.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 图片下载
 */
public class DownloadImage {

	public static void download(String urlString, String savePath) {
		try {
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(10*1000);
			con.setRequestProperty("User-Agent", "NING/1.0");
			// 输入流
			InputStream is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			OutputStream os = new FileOutputStream(savePath);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			System.out.println("下载出错:"+urlString);
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		download("https://www.1f866.com/uploads/images/2018-01-04/3146953/39e04fc25e674b679730a7d7baaa1e75.jpg",
				"C:\\Users\\su\\Desktop\\1_li1325169021.jpg");
	}

}