package com.lambertwu.search.mapper;

import java.util.List;

import com.lambertwu.common.pojo.SearchItem;

public interface SearchItemMapper {

	List<SearchItem> getItemList();
	
	SearchItem getItemById(long itemId);
}
