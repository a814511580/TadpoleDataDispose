package speedycloud;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pear.common.utils.FileUtils;
import com.pear.data.vo.ImageListVo;

/**
 * 文件管理器
 * */
public class FileManager {
	
	/**
	 * speedy的执行api
	 * */
	SpeedyCloudS3 s3api;  
    
	public FileManager(String host, String access_key, String secret_key) {
		super();
		s3api = new SpeedyCloudS3(
				host,
				access_key,
				secret_key);
	} 

	public void createdFolder(String bucket,String path)
	{
		List<String> list=new ArrayList<>();
		FileUtils.fillRelativeFolderPathList(list, new File(path), new File(path));
		for(String folder:list) 
		{
	        String response = s3api.putObjectFromString(
	        		bucket,
	        		folder+"/folder_create_assist.txt",
	        		"folder_create_assist");
	        System.out.println("response:"+response);
	        if(!"200".equals(response)) 
	        {
	        	System.out.println("创建文件夹出错");
	        	break;
	        }
        }
        System.out.println("执行完成");
	}  
	
}
