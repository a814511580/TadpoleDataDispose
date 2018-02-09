package com.pear.cdn.impl;

import java.io.IOException;

import com.pear.cdn.services.PrefetchService;
import com.pear.common.utils.FileUtils;

public class PrefetchDataResourceSimulationImplTest {
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{ 
		PrefetchService.start();
		Thread.sleep(1000*60*60*24);
	}
	
}
