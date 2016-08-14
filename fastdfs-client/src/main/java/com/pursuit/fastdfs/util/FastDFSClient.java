package com.pursuit.fastdfs.util;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

//使用方法：
//1、把FastDFS提供的jar包添加到工程中
//2、初始化全局配置。加载一个配置文件。
//3、创建一个TrackerClient对象。
//4、创建一个TrackerServer对象。
//5、声明一个StorageServer对象，null。
//6、获得StorageClient对象。
//7、直接调用StorageClient对象方法上传文件即可。
public class FastDFSClient {
	
	private TrackerClient trackerClient;
	private TrackerServer trackerServer;
	private StorageClient storageClient;
	private StorageServer storageServer;
	
	
	/**
	 * FastDFSClient 文件构造器
	 * @param config
	 * @throws MyException
	 */
	public FastDFSClient(String config) throws MyException{
		if(config==null){
			throw new MyException("请填写tracker_server配置文件路径");
		}
		if(config.contains("classpath:")){
			config =config.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		try {
			ClientGlobal.init(config);
		} catch (Exception e) {
			throw new MyException("加载配置文件出错");
		}
		trackerClient = new TrackerClient();
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			throw new MyException("创建 trackerServer出错");
		}
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	public String upload_file(String file_name) throws MyException{
		String  ext_name = file_name.substring(file_name.lastIndexOf(".")+1);
		return upload_file(file_name, ext_name);
	}
	
	public String upload_file(String file_name,String ext_name) throws MyException{
		String  return_flag = "";
		try {
			String[] upload_file = storageClient.upload_file(file_name, ext_name, null);
			for (int i = 0; i < upload_file.length; i++) {
				return_flag+="/"+upload_file[i];
			}
		} catch (Exception e) {
			throw new MyException("创建 trackerServer出错");
		}
		return return_flag;
	}
	
	public String upload_file(byte[] bytes,String ext_name) throws MyException{
		String  return_flag = "";
		try {
			String[] upload_file = storageClient.upload_file(bytes, ext_name, null);
			for (int i = 0; i < upload_file.length; i++) {
				return_flag+="/"+upload_file[i];
			}
		} catch (Exception e) {
			throw new MyException("创建 trackerServer出错");
		}
		return return_flag;
	}
	
}
