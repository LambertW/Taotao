package com.lambertwu.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.content.service.ContentService;
import com.lambertwu.mapper.TbContentMapper;
import com.lambertwu.pojo.TbContent;
import com.lambertwu.pojo.TbContentExample;
import com.lambertwu.pojo.TbItem;
import com.lambertwu.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}
	
	
}
