package com.lambertwu.service;

import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.pojo.TbItem;
import com.lambertwu.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItemById(long itemId);
	
	EasyUIDataGridResult getItemList(int page, int rows);
	
	TaotaoResult addItem(TbItem item, String desc);
	
	TbItemDesc getItemDescById(long itemId);
}
