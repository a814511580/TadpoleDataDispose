package com.pear.data.generate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.pear.common.utils.FileUtils;
import com.pear.common.utils.ThumbnailatorUtils; 
import com.pear.data.vo.ImageClassifyVo;
import com.pear.data.vo.ImageListVo;
 
import net.sf.json.JSONObject;

/**
 * 图片类型的数据生产
 */
public class ImageClassifyBuild {

	private static final Logger log=Logger.getLogger(ImageClassifyBuild.class);
	
	/**
	 * 获取指定路径下的所有分类图片数据
	 * 
	 * @throws IllegalAccessException
	 */
	List<ImageClassifyVo> getAllClassify(String path) throws IllegalAccessException {
		List<ImageClassifyVo> allClassifylist = new ArrayList<>();
		File[] calssifys = new File(path).listFiles();
		for (File calssify : calssifys) {
			if (!calssify.isDirectory())
				throw new IllegalAccessException("为啥这个不是文件夹:" + calssify);

			List<ImageListVo> imageLists = new ArrayList<>();
			File[] allOnceData = calssify.listFiles();
			for (File onceData : allOnceData) {
				String title = onceData.getName();
				if(title.indexOf("_")!=-1)
					title=title.substring(title.indexOf("_")+1); 
				if(title.indexOf("【")!=-1)
					title=title.substring(0,title.lastIndexOf("【"));
				if(title.indexOf("[")!=-1)
					title=title.substring(0,title.lastIndexOf("["));
				
				File[] onceImages = onceData.listFiles();
				List<String> paths = new ArrayList<>();
				for (File onceImage : onceImages)
					paths.add(onceImage.getPath());
				
				/**把图片也排序一下。按照名称来*/
				Collections.sort(paths, new Comparator<String>() {  			  
		            @Override  
		            public int compare(String o1, String o2) {  
		            	
		            	/**这里是补回之前抓数据的坑。*/
		            	String o1Name=new File(o1).getName();
		            	String o2Name=new File(o2).getName();
		            	
		            	String o1Number=o1Name.substring(o1Name.indexOf("_")+1).replace(".jpg", "");
		            	String o2Number=o2Name.substring(o2Name.indexOf("_")+1).replace(".jpg", "");
		            	
		                return Integer.parseInt(o1Number) - Integer.parseInt(o2Number);  
		            }
		        }); 
				imageLists.add(new ImageListVo(title, paths));
			}
			allClassifylist.add(new ImageClassifyVo(calssify.getName(), imageLists));
		}
		return allClassifylist;
	}

	/**
	 * 生产所有图片数据。
	 * 
	 * @param datas
	 *            现有数据库
	 * @param startDate
	 *            开始生成时间
	 * @param outputPath
	 *            结果输出路径
	 * @throws IOException 
	 */
	void buildClassify(List<ImageClassifyVo> imageClassifyVos,long startDate, String outputPath) throws IOException {
		log.debug("开始生产图片");
		for(ImageClassifyVo imageClassifyVo:imageClassifyVos) 
		{
			System.out.println("imageClassifyVo:"+imageClassifyVo.getClassify());
			buildClassifyByDate(startDate, imageClassifyVo, outputPath);
		}
		log.debug("生产小说图片");
	}

	/**
	 * 提取数据生产一天的数据出来。
	 * 
	 * @param date
	 *            生产那一天的数据
	 * @param datas
	 *            现有数据库
	 * @throws IOException 
	 */
	void buildClassifyByDate(long date, ImageClassifyVo imageClassifyVo, String outputPath) throws IOException {
		for(ImageListVo images:imageClassifyVo.getImages()) 
		{
			System.out.println("images:"+images.getTitle());
			String currentPath=outputPath
					+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(new Date(date))
					+File.separator+"图片"+File.separator+imageClassifyVo.getClassify()+File.separator;
			new File(currentPath).mkdirs();
			
			List<String> paths=images.getPaths();
			for(int i=0;i<paths.size();i++)
			{
				String outImagePath= currentPath+(i+1)+".jpg";
				ThumbnailatorUtils.thumb(paths.get(i), outImagePath); 
			}
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("标题", images.getTitle());
			jsonObject.put("图片集合",paths.size());
			
			FileUtils.writeText2File(currentPath+"数据.txt", jsonObject.toString());
			
			date+=1000*60*60*24;
		}
	}
	
	
	
	public static void main(String[] args) throws IOException, IllegalAccessException
	{
		String loadPath="F:\\自己项目\\雪梨电影\\数据\\数据已准备\\图片\\已挑选"; 
		long startDate=System.currentTimeMillis();
		String outputPath="F:\\自己项目\\雪梨电影\\数据\\数据已准备\\图片\\生产";
		ImageClassifyBuild build=new ImageClassifyBuild();  
		List<ImageClassifyVo> datas=build.getAllClassify(loadPath);
		build.buildClassify(datas,
				startDate,
				outputPath);  
	}


}
