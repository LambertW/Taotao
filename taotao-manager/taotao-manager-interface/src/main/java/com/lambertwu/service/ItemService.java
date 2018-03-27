package com.lambertwu.service;

import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	
	EasyUIDataGridResult getItemList(int page, int rows);
}
