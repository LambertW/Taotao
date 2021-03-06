package com.lambertwu.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambertwu.common.pojo.EasyUITreeNode;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.content.service.ContentCategoryService;
import com.lambertwu.mapper.TbContentCategoryMapper;
import com.lambertwu.pojo.TbContentCategory;
import com.lambertwu.pojo.TbContentCategoryExample;
import com.lambertwu.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分類管理
 * @author wgq19
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		
		return resultList;
	}

	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		
		contentCategoryMapper.insert(contentCategory);
		
		// 判断父节点的状态
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		if(category == null) {
			return new TaotaoResult(404, "无效id", null);
		}
		
		category.setName(name);
		
		contentCategoryMapper.updateByPrimaryKey(category);
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContentCategory(long id) {
		
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		if(category == null) {
			return new TaotaoResult(404, "无效id", null);
		}
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		contentCategoryMapper.deleteByPrimaryKey(id);
		if(!category.getIsParent()) {
			deleteContentCategoryByParentId(category.getId());
		}
		
		// 更新父节点的IsParent属性
		example.clear();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(category.getParentId());
		int leafNum = contentCategoryMapper.countByExample(example);
		if(leafNum <= 0) {
			TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
			parentCategory.setIsParent(false);
			parentCategory.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		
		return TaotaoResult.ok();
	}

	private void deleteContentCategoryByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list == null || list.isEmpty()) {
			return;
		}
		
		for (TbContentCategory tbContentCategory : list) {
			deleteContentCategory(tbContentCategory.getId());
		}
	}
}
