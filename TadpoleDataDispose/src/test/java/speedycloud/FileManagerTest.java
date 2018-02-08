package speedycloud;

public class FileManagerTest { 
	
	public static void createdFolder()
	{
		FileManager fileManager=new FileManager(
				"http://cos.speedycloud.org",
				"16C0763318C3F68EF958183672F70465",
				"4ca971687f254bfca37d9e5584da9499c9f3be4c6f77fbbb47d5030102e44e2c");
		fileManager.createdFolder("tadpole", "F:\\xiaomi\\app数据点\\data");
	}  
	
	public static void main(String[] args)
	{
		createdFolder();
	}
	
}
