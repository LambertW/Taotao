package com.lambertwu.content.service;

import java.util.List;

import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.pojo.TbContent;

public interface ContentService {

	EasyUIDataGridResult getContentList(long categoryId, int page, int rows);
	
	TaotaoResult addContent(TbContent content);
	
	TaotaoResult editContent(TbContent content);
	
	TaotaoResult deleteContents(List<Long> ids);
	
	List<TbContent> getContentByCid(long cid);
}
