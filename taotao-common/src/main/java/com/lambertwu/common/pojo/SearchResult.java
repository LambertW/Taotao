package com.lambertwu.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 148834794128699319L;
	
	private long TotalPages;
	private long recordCount;
	private List<SearchItem> itemList;
	
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getTotalPages() {
		return TotalPages;
	}
	public void setTotalPages(long totalPages) {
		TotalPages = totalPages;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
}
