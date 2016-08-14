package com.purusit.test.http;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTest {
	/**
	*第一步：把freemarker的jar包添加到工程中
	*第二步：freemarker的运行不依赖web容器，可以在java工程中运行。创建一个测试方法进行测试。
	*第三步：创建一个Configration对象
	*第四步：告诉config对象模板文件存放的路径。
	*第五步：设置config的默认字符集。一般是utf-8
	*第六步：从config对象中获得模板对象。需要制定一个模板文件的名字。
	*第七步：创建模板需要的数据集。可以是一个map对象也可以是一个pojo，把模板需要的数据都放入数据集。
	*第八步：创建一个Writer对象，指定生成的文件保存的路径及文件名。
	*第九步：调用模板对象的process方法生成静态文件。需要两个参数数据集和writer对象。
	*第十步：关闭writer对象。
	*/
	@Test
	public void first()  throws Exception {
		Configuration  configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(new File("D:/si-tech/workspace_b2c/b2c-portal/src/main/webapp/WEB-INF/ftl"));
		Template template = configuration.getTemplate("first.ftl");
		Map root  = new HashMap<String, String>();
		root.put("hello", "hello111");
		Writer out = new FileWriter(new File("d:/ftl/first.html"));
		template.process(root, out);
		out.flush();
		out.close();
	}
	@Test
	public void second()  throws Exception {
		Configuration  configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(new File("D:/si-tech/workspace_b2c/b2c-portal/src/main/webapp/WEB-INF/ftl"));
		Template template = configuration.getTemplate("second.ftl");
		Map root  = new HashMap<String, String>();
		root.put("hello", "这个是页面1的内容<br/>");
		//root.put("title", "this is a title");

		Student student0 = new Student(0, "学生0", "爱好0");
		
		List list = new ArrayList<Student>();
		Student student1 = new Student(1, "学生1", "爱好1");
		Student student2 = new Student(2, "学生2", "爱好2");
		Student student3 = new Student(3, "学生3", "爱好3");
		Student student4 = new Student(4, "学生4", "爱好4");
		Student student5 = new Student(5, "学生5", "爱好5");
		Student student6 = new Student(6, "学生6", "爱好6");
		list.add(student1);
		list.add(student2);
		list.add(student3);
		list.add(student4);
		list.add(student5);
		list.add(student6);
		
		student0.setChild(list);
		
		root.put("stu0", student0);
		root.put("curDate", new Date());
		
		Writer out = new FileWriter(new File("d:/ftl/second.html"));
		template.process(root, out);
		out.flush();
		out.close();
	}
	public class Student{
		public int num;
		public String name;
		private String hobby;
		private List child;
		
		public Student(int num, String name, String hobby) {
			super();
			this.num = num;
			this.name = name;
			this.hobby = hobby;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getHobby() {
			return hobby;
		}
		public void setHobby(String hobby) {
			this.hobby = hobby;
		}
		public List getChild() {
			return child;
		}
		public void setChild(List child) {
			this.child = child;
		}
		@Override
		public String toString() {
			return "[num=" + num + ", name=" + name + ", hobby="
					+ hobby + ", child=" + child + "]";
		}
	}
	public static void main(String[] args) {
		System.out.println(new File(FreemarkerTest.class.getResource("/").getPath()));
	}
}
