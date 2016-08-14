package com.pursuit.controller.mytest;

import java.util.List;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pursuit.controller.ItemController;
import com.pursuit.fastdfs.util.FastDFSClient;
import com.pursuit.mapper.TbItemMapper;
import com.pursuit.pojo.TbItem;
import com.pursuit.pojo.TbItemExample;


public class MyTest {

	public static void main(String[] args) {
		ApplicationContext context  = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		System.out.println(context);
		ItemController item = (ItemController) context.getBean("itemController");
		System.out.println(item);
		TbItem items= item.getItemById(new Long(562379));
		System.out.println(items.getPrice());
	}
	public void testPageHelper(){
		ApplicationContext context  = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		TbItemMapper bean = context.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		PageHelper pageHelper = new PageHelper();
		pageHelper.startPage(1, 30);
		List<TbItem> selectByExample = bean.selectByExample(example);
		//4、取分页后结果
		PageInfo pageInfo = new PageInfo(selectByExample);

		System.out.println(selectByExample.size());
		System.out.println(pageInfo.getTotal());
	}
	
	
//	使用方法：
//	1、把FastDFS提供的jar包添加到工程中
//	2、初始化全局配置。加载一个配置文件。
//	3、创建一个TrackerClient对象。
//	4、创建一个TrackerServer对象。
//	5、声明一个StorageServer对象，null。
//	6、获得StorageClient对象。
//	7、直接调用StorageClient对象方法上传文件即可。
	@Test
	public void testUpload() throws Exception {
		// 1、把FastDFS提供的jar包添加到工程中
		// 2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init("D:\\si-tech\\workspace_b2c\\b2c-manager\\b2c-manager-web\\src\\main\\resources\\properties\\client.properties");
		// 3、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		// 4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
		// 6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 7、直接调用StorageClient对象方法上传文件即可。
		String[] strings = storageClient.upload_file("d:\\test.png", "png", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFastDfsClient() throws Exception {
//		FastDFSClient client = new FastDFSClient("D:\\si-tech\\workspace_b2c\\b2c-manager\\b2c-manager-web\\src\\main\\resources\\properties\\client.properties");
		FastDFSClient client = new FastDFSClient("classpath:properties/client.properties");
		String uploadFile = client.upload_file("d:\\test.png");
		System.out.println(uploadFile);
	}


	
}
