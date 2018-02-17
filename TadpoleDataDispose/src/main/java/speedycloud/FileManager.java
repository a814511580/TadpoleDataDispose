package speedycloud;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.dom4j.Dom4jXPath;

import com.pear.common.utils.system.FileUtils;
import com.pear.data.vo.ImageListVo;

/**
 * 文件管理器
 */
public class FileManager {

	/**
	 * speedy的执行api
	 */
	SpeedyCloudS3 s3api;

	public FileManager(String host, String access_key, String secret_key) {
		super();
		s3api = new SpeedyCloudS3(host, access_key, secret_key);
	}

	/**
	 * 根据本地的文件夹路径给远端也创建一模一样的文件夹路径
	 * @note 因为远端不能单纯创建文件夹，所以这里需要通过接口模拟创建
	 * */
	public void createdFolder(String bucket, String path) {
		List<String> list = new ArrayList<>();
		FileUtils.fillRelativeFolderPathList(list, new File(path), new File(path));
		for (String folder : list) {
			String response = s3api.putObjectFromString(bucket, folder + "/folder_create_assist.txt",
					"folder_create_assist");
			System.out.println("response:" + response);
			if (!"200".equals(response)) {
				System.out.println("创建文件夹出错");
				break;
			}
		}
		System.out.println("执行完成");
	}

	/**
	 * 获取桶里面的所有key值
	 * */
	public List<String> getAllKey(String bucket) throws Exception {
		List<String> list=new ArrayList<>();
		String bucketList = s3api.list(bucket);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new ByteArrayInputStream(bucketList.getBytes()));
		Element root = doc.getRootElement();
		List contents=root.elements("Contents"); 
		for (Object contentObj : contents) {
			if (contentObj instanceof Element) {
				Element key =  ((Element) contentObj).element("Key");  
				String value=key.getText();
				list.add(value);
			} 
		}
		System.out.println("所有的key数量是:"+list.size());
		return list;
	}
	
	/**
	 * 设置所有的key为公共权限，公共读取和写入
	 * */
	public boolean setAllKeyPermissionPublic(String bucket) throws Exception 
	{ 
		final String permission="public-read-write";
		String setbucketacl = s3api.updateBucketAcl(bucket,permission);
		if(!setbucketacl.isEmpty()
				&&
				!"200".equals(setbucketacl)) {
			System.out.println("修改桶权限出错:"+bucket+",setbucketacl"+setbucketacl);
			return false;
    	}
	    List<String> allKeys=getAllKey(bucket);
	    System.out.println("所有待修改权限的数量是:"+allKeys.size());
	    double count=0;
	    for(String key:allKeys) 
	    {
	    	String setkeyacl = s3api.updateKeyAcl(bucket, key, permission);
	    	if(	!setbucketacl.isEmpty()  
	    			&& !"200".equals(setkeyacl)) {
	    		System.out.println("警告：修改"+key+"权限出错"+",setbucketacl"+setkeyacl);
	    		return false;
	    	}
	    	System.out.println("修改完毕 "+count++  +",进度" +(count/(double)allKeys.size()));
	    }
	    return true;
	}

}
