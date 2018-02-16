package com.pear.common.utils.media;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片工具类
 * */
public class ThumbnailatorUtils {
	 
    /** 
     * 指定大小进行缩放  
     * 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变 
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变 
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height 
     *  
     * @param source 
     *            输入源 
     * @param output 
     *            输出源 
     * @param width 
     *            宽 
     * @param height 
     *            高 
     * @throws IOException 
     */  
    public static void thumb(String source, String output) throws IOException {   
        Thumbnails.of(source).size(720, 1280).toFile(output);   
    }  
  
}
