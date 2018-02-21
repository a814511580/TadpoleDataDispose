package com.pear.common.utils.media;

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
	public static Long getDuration(String path) throws InputFormatException, EncoderException {
        File source = new File(path);
        Encoder encoder = new Encoder();
        MultimediaInfo m = encoder.getInfo(source);
        return m.getDuration(); 
    }
	
	/**
	 * 获取电影的时长
	 * @throws EncoderException 
	 * @throws InputFormatException 
	 * */
	public static String getDurationString(String path) throws InputFormatException, EncoderException {
        long duration = getDuration(path); 
        /**大于1小时，暂时时分秒，否则暂时分秒即可*/
        final int onceHourMilli=1000*60*60;
        if(duration>=onceHourMilli)
        	return new SimpleDateFormat("HH:mm:ss").format(new Date(duration));
        else 
        	return new SimpleDateFormat("mm:ss").format(new Date(duration));
    }
	
}
