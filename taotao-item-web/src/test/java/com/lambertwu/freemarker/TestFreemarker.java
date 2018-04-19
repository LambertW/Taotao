package com.lambertwu.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.Subject;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreemarker {
	
	@Test
	public void testFreemarker() throws Exception {
		
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("D:\\Developer\\Repos\\Taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		configuration.setDefaultEncoding("utf-8");
		
		Template template = configuration.getTemplate("hello.ftl");
		Map data = new HashMap<>();
		data.put("hello", "hello freemarker");
		
		Writer out = new FileWriter(new File("D:\\Developer\\JAVA\\helloFreemarker.txt"));
		template.process(data, out);
		
		out.close();
	}
	
	@Test
	public void testFreemarkerGenerateStudent() throws Exception {
		
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("D:\\Developer\\Repos\\Taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		configuration.setLocale(Locale.CHINA);
		configuration.setDefaultEncoding("UTF-8");
		
		Template template = configuration.getTemplate("student.ftl");
		Map data = new HashMap<>();
		data.put("hello", "hello freemarker");

		List<Student> students = new ArrayList<>();
		students.add(new Student(1, "小明", 18, "上海43e"));
		students.add(new Student(2, "小明1", 14, "上海43e"));
		students.add(new Student(3, "小明2", 15, "上海43e"));
		students.add(new Student(4, "小明3", 16, "上海43e"));
		students.add(new Student(5, "小明4", 17, "上海43e"));
		students.add(new Student(6, "小明5", 18, "上海43e"));
		students.add(new Student(7, "小明6", 8, "上海43e"));
		
		data.put("students", students);
		// 日期类型输出
		data.put("date", new Date());
		Writer out = new FileWriter(new File("D:\\Developer\\JAVA\\student.html"));
		template.process(data, out);
		
		out.close();
	}

}
