package com.lambertwu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.pojo.TbItem;
import com.lambertwu.service.ItemService;

/**
 * 商品管理Controller
 * 
 * @author wgq19
 *
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page, int rows) {
		return itemService.getItemList(page, rows);
	}
	
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc) {
		TaotaoResult result = itemService.addItem(item, desc);
		return result;
	}
	
}
