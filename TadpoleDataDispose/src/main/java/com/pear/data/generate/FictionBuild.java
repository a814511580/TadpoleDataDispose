package com.pear.data.generate;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.pear.common.utils.FileUtils;
import com.pear.common.utils.media.ThumbnailatorUtils;
import com.pear.data.vo.FictionVo;
import com.pear.data.vo.ImageListVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 小说的生产
 * */
public class FictionBuild {

	private static final Logger log=Logger.getLogger(FictionBuild.class);

	/**小说的文本路径*/
	String fictionPath;
	
	/**图片的路径*/
	String imagePaht;
	
	/**生成时间*/
	long startDate;
	
	/**结果输出路径*/
	String outputPath;  
	
	public FictionBuild() {
		super();
	}

	public FictionBuild(String fictionPath, String imagePaht, long startDate, String outputPath) {
		super();
		this.fictionPath = fictionPath;
		this.imagePaht = imagePaht;
		this.startDate = startDate;
		this.outputPath = outputPath;
	}

	/**
	 * 获取指定路径下的所有小说数据
	 * @throws IOException 
	 * */
	public List<FictionVo> getFictionByPath(String path) throws IOException
	{
		List<String> allFile=FileUtils.getAllFilesByFolder(new File(path));
		List<FictionVo> list=new ArrayList<>();
		for(String subFileStr:allFile)
		{
			File subFile=new File(subFileStr);
			String title=subFile.getName().replace(".txt", "");
			String content=FileUtils.readTxtByFile(subFileStr, "utf-8");
			list.add(new FictionVo(title, content));
		}
		Collections.sort(list, new Comparator<FictionVo>() {  			  
	            @Override  
	            public int compare(FictionVo o1, FictionVo o2) {  
	                int i = o1.getContent().length() - o2.getContent().length();  
	                return i;  
	            }  
	        });  
		return list;
	}
	
	/**
	 * 获取指定路径下的所有图片数据
	 * */
	public List<ImageListVo> getImageListByPath(String path)
	{
		List<ImageListVo> list=new ArrayList<>();
		File[] files=new File(path).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		for(File file:files)
		{
			String title=file.getName();
			List<File> subFiles=new ArrayList<>(Arrays.asList(file.listFiles()));
			List<String> subPaths=new ArrayList<>();
			for(File subFile:subFiles)
				subPaths.add(subFile.getPath());
			list.add(new ImageListVo(title, subPaths));
			/**把图片也排序一下。按照名称来*/
			Collections.sort(subPaths, new Comparator<String>() {  			  
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
		}
		Collections.sort(list, new Comparator<ImageListVo>() {  			  
	            @Override  
	            public int compare(ImageListVo o1, ImageListVo o2) {  
	                return o1.getPaths().size() - o2.getPaths().size();  
	            }
	        }); 
		return list;
	}

	/**
	 * 根据插入图片插入数量来切换内容体
	 * @param content 小说的内容
	 * @param imageCount 配改小说的图片张数
	 * */
	public List<String> splitContentBestByImageCount(String content,int imageCount)
	{ 
		int AROUND_SIZE=50;
		int avgSize=content.length()/imageCount;/**头和尾部默认用掉两条了*/;
		System.out.println(
				"conent size:"+content.length()
				+",imageCount:"+imageCount
				+",avgSize:"+avgSize);
		List<String> list=new ArrayList<>();
		int lastIndex=0;
		
		/**前后默认添加图片，所以起点=1，末点跳出来*/
		for(int i=1;i<imageCount;i++)
		{
			int currentIndex=i*avgSize-1;
			int maxPrevIndex=currentIndex-AROUND_SIZE;
			int maxBackIndex=currentIndex+AROUND_SIZE;
			
			int symbolIndex=content.indexOf("\n", maxPrevIndex);
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=content.indexOf("。", maxPrevIndex); 
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=content.indexOf("，", maxPrevIndex);
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=content.indexOf(".", maxPrevIndex);
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=content.indexOf(",", maxPrevIndex);
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=content.indexOf("」", maxPrevIndex);
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=content.indexOf("？", maxPrevIndex);
			
			/**都没找到就强暴点*/
			if(-1==symbolIndex||symbolIndex>maxBackIndex)
				symbolIndex=currentIndex;
			
			System.out.println("symbolIndex:"+symbolIndex);
			list.add("    "+content.substring(lastIndex,symbolIndex)); 
			lastIndex=symbolIndex;
		}
		list.add("    "+content.substring(lastIndex)); 
		return list;
	}
	
	/**
	 * 根据小说和配图，生产一套视图出来。
	 * @param 小说的数据
	 * @param 配图的数据
	 * @param publicDate 发布时间
	 * @param outputPath 输出路径
	 * @throws IOException 
	 * */
	public void createdOnce(FictionVo fiction,
			ImageListVo imageList,
			long publicDate,
			String outputPath) throws IOException 
	{
		outputPath=outputPath
				+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(new Date(publicDate))
				+File.separator+"小说"+File.separator;
		new File(outputPath).mkdirs();
		
		/**前后扣掉了两张*/
		int contentCenterImageCount
			=imageList.getPaths().size()-/**2*/1 /**不要问我1和2是啥。我也没理清楚，能跑就好*/;
		List<String> sections
			=splitContentBestByImageCount(fiction.getContent(),contentCenterImageCount);
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("标题", fiction.getTitle());
		
		JSONArray contentArray=buildSections(sections,imageList.getPaths(),outputPath);
		jsonObject.put("段落和图文",contentArray);
		
		FileUtils.writeText2File(outputPath+"数据.txt", jsonObject.toString());
	}
	
	
	/**
	 * 生产段落数据
	 * @throws IOException 
	 * */
	private JSONArray buildSections(
			List<String> sections,
			List<String> imagePaths,
			String outputPath) throws IOException 
	{
		JSONArray contentArray=new JSONArray();  
		/**imageIndex 写进json一一对应的下标数值*/
		for(int i=0,imageIndex=0;i<sections.size();i++)
		{			
			if(i==0)
			{
				imageIndex++;
				ThumbnailatorUtils.thumb(imagePaths.get(0), outputPath+imageIndex+".jpg"); 
				JSONObject startImageObject=new JSONObject();
				startImageObject.put("图片", String.valueOf(1));
				contentArray.add(startImageObject);
			}
			
			JSONObject sectionObject=new JSONObject();
			sectionObject.put("段落", sections.get(i));
			
			/**这里默认先取了第一张，所以取图片路径，默认都要+1*/
			String sourceImagePath=imagePaths.get(i+1);
			
			/**这里是书写进json的路径。*/
			int outImageIndex=++imageIndex;
			String outImagePath= outputPath+(outImageIndex)+".jpg";
			ThumbnailatorUtils.thumb(sourceImagePath, outImagePath); 
			sectionObject.put("图片", String.valueOf(outImageIndex)); 
			contentArray.add(sectionObject);
		}
		return contentArray;
	}
	
	/**
	 * 开始生产小说
	 * */
	void build() throws IOException 
	{ 
		log.debug("开始生产小说");
		List<FictionVo> fiction=getFictionByPath(fictionPath); 
		List<ImageListVo> imageList
			=new FictionBuild().getImageListByPath(imagePaht);
		if(fiction.size()!=imageList.size()) 
			throw new IllegalStateException(
					"小说和图片的大小不一致"
							+",fiction.size():"+fiction.size()
							+",imageList.size():"+imageList.size()
					);
		
		long publicDate=startDate;
		for(int i=0;i<fiction.size();i++) 
		{
			createdOnce(fiction.get(i),imageList.get(i),publicDate,outputPath);
			publicDate+=1000*60*60*24;
		}
		log.debug("生产小说完成");
	}
	
	
	
	public static void main(String[] args) throws IOException
	{
		String fictionPath="F:\\自己项目\\雪梨电影\\数据\\数据已准备\\小说文本";
		String imagePath="F:\\自己项目\\雪梨电影\\数据\\数据已准备\\小说图片";
		long startDate=System.currentTimeMillis();
		String outputPath="F:\\自己项目\\雪梨电影\\数据\\数据已准备\\小说生产";
		FictionBuild fiction=new FictionBuild(fictionPath,imagePath,startDate,outputPath); 
		fiction.build();  
		
		
	}

}

