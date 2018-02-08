package speedycloud;

public class FileManagerTest { 
	
	static FileManager fileManager=new FileManager(
			"http://cos.speedycloud.org",
			"16C0763318C3F68EF958183672F70465",
			"4ca971687f254bfca37d9e5584da9499c9f3be4c6f77fbbb47d5030102e44e2c");
	
	public static void createdFolder() throws Exception
	{
		fileManager.createdFolder("tadpole", "F:\\Test--III\\test25\\YellowThird\\app\\src"); 
	}   
	
	public static void setAllKeyPermissionPublic() throws Exception
	{
		fileManager.setAllKeyPermissionPublic("tadpole");
	}  
	
	/**
	 * 原因
	 * speedcloud 
	 * 1. 不支持单纯建立文件夹，所以这里需要模拟先创建文件夹，才能用Dragondisk文件夹管理器管理权限
	 * 2. Dragondisk无法统一管理权限，所以需要这里用代码来管理权限
	 * 用法步骤
	 * 1. 先调用createdFolder() 模拟建立后文件夹
	 * 2. 用Dragondisk上传完毕文件
	 * 3. 调用setAllKeyPermissionPublic设置权限为公共的
	 * */
	public static void main(String[] args) throws Exception
	{
		//createdFolder();
		setAllKeyPermissionPublic();
	}
	

	
}
