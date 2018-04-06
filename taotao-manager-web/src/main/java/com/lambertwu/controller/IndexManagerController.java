package com.lambertwu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.search.service.SearchItemService;

@Controller
public class IndexManagerController {

	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/create")
	@ResponseBody
	public TaotaoResult importIndex() {
		TaotaoResult importItemsToIndex = searchItemService.importItemsToIndex();
		return importItemsToIndex;
	}
}
