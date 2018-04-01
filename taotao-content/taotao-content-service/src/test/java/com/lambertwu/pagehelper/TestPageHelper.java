package com.lambertwu.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lambertwu.mapper.TbContentCategoryMapper;
import com.lambertwu.mapper.TbContentMapper;
import com.lambertwu.mapper.TbItemMapper;
import com.lambertwu.pojo.TbContent;
import com.lambertwu.pojo.TbContentExample;
import com.lambertwu.pojo.TbContentExample.Criteria;

public class TestPageHelper {

	private ApplicationContext applicationContext;

	@Test
	public void testPageHelper() {
		//PageHelper.startPage(1, 10);

		applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-dao.xml");
		TbContentMapper itemMapper = applicationContext.getBean(TbContentMapper.class);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// criteria.and

		List<TbContent> list = itemMapper.selectByExample(example);
		for (TbContent tbContent : list) {
			System.out.println(tbContent.getContent());
		}
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		System.out.println("总记录数：" + pageInfo.getTotal());
		System.out.println("总页数:" + pageInfo.getPages());
		System.out.println("返回的记录数:" + list.size());
	}
}
