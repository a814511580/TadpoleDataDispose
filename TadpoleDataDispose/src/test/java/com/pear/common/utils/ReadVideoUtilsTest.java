package com.pear.common.utils;

import java.io.IOException;

import com.pear.common.utils.media.ReadVideoUtils;

import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;

public class ReadVideoUtilsTest {

	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException, InputFormatException, EncoderException 
	{
		String value=ReadVideoUtils.getDurationString("C:\\VoiseUpdate\\user1\\data\\vedio\\data\\test.mp4");
		System.out.println(value);
	}
	
	
	
	
}
