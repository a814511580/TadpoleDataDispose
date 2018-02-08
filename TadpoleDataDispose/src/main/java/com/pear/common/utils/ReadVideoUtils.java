package com.pear.common.utils;

import java.io.File;
import java.text.SimpleDateFormat; 
import java.util.Date;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;

/**
 * 读取视频的工具
 * */
public class ReadVideoUtils { 
	
	/**
	 * 获取电影的时长
	 * @throws EncoderException 
	 * @throws InputFormatException 
	 * */
	public static String getDuration(String path) throws InputFormatException, EncoderException {
        File source = new File(path);
        Encoder encoder = new Encoder();
        MultimediaInfo m = encoder.getInfo(source);
        long duration = m.getDuration();
        
        /**大于1小时，暂时时分秒，否则暂时分秒即可*/
        final int onceHourMilli=1000*60*60;
        if(duration>=onceHourMilli)
        	return new SimpleDateFormat("HH:mm:ss").format(new Date(duration));
        else 
        	return new SimpleDateFormat("mm:ss").format(new Date(duration));
    }
	
}
