package com.purusit.test.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpUtilTest {
	/**
	* 第一步：把HttpClient使用的jar包添加到工程中。
	* 第二步：创建一个HttpClient的测试类
	* 第三步：创建测试方法。
	* 第四步：创建一个HttpClient对象
	* 第五步：创建一个HttpGet对象，需要制定一个请求的url
	* 第六步：执行请求。
	* 第七步：接收返回结果。HttpEntity对象。
	* 第八步：取响应的内容。
	* 第九步：关闭HttpGet、HttpClient。
	*/
	
	/**
	 * // 第四步：创建一个HttpClient对象
	 * // 第五步：创建一个HttpGet对象，需要制定一个请求的url
	 * // 第六步：执行请求
	 * // 第七步：接收返回结果。HttpEntity对象。
	 * // 第八步：取响应的内容。
	 * // 第九步：关闭response、HttpClient。
	 */
	@Test
	public void testGet(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://10.163.106.148:10120/CRM6/page/nbase/login/crmlogin.html");
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
			
			response.close();
			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testHttpPost() throws Exception {
		
		// 第一步：创建一个httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 第二步：创建一个HttpPost对象。需要指定一个url
		HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
		// 第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
		List<NameValuePair> formList = new ArrayList<>();
		formList.add(new BasicNameValuePair("name", "张三"));
		formList.add(new BasicNameValuePair("pass", "1243"));
		// 第四步：需要把表单包装到Entity对象中。StringEntity
		StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
		post.setEntity(entity);
		// 第五步：执行请求。
		CloseableHttpResponse response = httpClient.execute(post);
		// 第六步：接收返回结果
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity);
		System.out.println(result);
		// 第七步：关闭流。
		response.close();
		httpClient.close();
	}

}
