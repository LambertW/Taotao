package com.lambertwu.content.service;

import java.util.List;

import com.lambertwu.common.pojo.EasyUITreeNode;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.pojo.TbContentCategory;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(long parentId);
	
	TaotaoResult addContentCategory(long parentId, String name);
}
