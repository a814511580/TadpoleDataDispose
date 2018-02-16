package com.pear.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.pear.data.generate.FictionBuild;

/**
 * cmd的命令行工具
 */
public class CmdUtils {
	
	private static final Logger log = Logger.getLogger(FictionBuild.class);

	/**
	 * 调用CMD执行命令
	 */
	public static String execute(String command) {
		try {
			StringBuffer sb = new StringBuffer();
			Process pro = Runtime.getRuntime().exec("cmd /c " + command);
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String msg = null;
			while ((msg = br.readLine()) != null) {
				sb.append(msg);
			}
			return sb.toString();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		return "";
	}
	
}
