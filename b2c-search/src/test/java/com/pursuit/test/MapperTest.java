package com.pursuit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapperTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/application-*");
//		Object bean = context.getBean("itemService",ItemService.class);
		Object bean = context.getBean("dataSource");
		System.out.println(bean);
	}
}
