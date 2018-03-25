package com.lambertwu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambertwu.mapper.TbItemMapper;
import com.lambertwu.pojo.TbItem;
import com.lambertwu.service.ItemService;

/**
 * 商品管理Service
 * @author wgq19
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}

}
