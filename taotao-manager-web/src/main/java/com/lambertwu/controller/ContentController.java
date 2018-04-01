package com.lambertwu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.content.service.ContentService;
import com.lambertwu.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		EasyUIDataGridResult list = contentService.getContentList(categoryId, page, rows);
		return list;
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		TaotaoResult result = contentService.addContent(content);
		return result;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult editContent(TbContent content) {
		TaotaoResult result = contentService.editContent(content);
		return result;
	}

	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		String[] spiltIds = ids.split(",");
		List<Long> formatIds = new ArrayList<>();
		for (String id : spiltIds) {
			formatIds.add(Long.parseLong(id));
		}
		TaotaoResult result = contentService.deleteContents(formatIds);
		return result;
	}
}
