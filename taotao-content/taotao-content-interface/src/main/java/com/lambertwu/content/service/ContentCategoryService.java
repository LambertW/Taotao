package com.lambertwu.content.service;

import java.util.List;

import com.lambertwu.common.pojo.EasyUITreeNode;
import com.lambertwu.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(long parentId);
	
	TaotaoResult addContentCategory(long parentId, String name);
	
	TaotaoResult updateContentCategory(long id, String name);
	
	TaotaoResult deleteContentCategory(long id);
}
