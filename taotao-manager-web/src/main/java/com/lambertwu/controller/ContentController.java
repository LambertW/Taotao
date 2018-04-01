package com.lambertwu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.content.service.ContentService;

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
}
