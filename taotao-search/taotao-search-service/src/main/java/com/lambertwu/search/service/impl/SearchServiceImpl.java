package com.lambertwu.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambertwu.common.pojo.SearchResult;
import com.lambertwu.search.dao.SearchDao;
import com.lambertwu.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		if (page < 1)
			page = 1;
		if (rows < 1)
			rows = 10;
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 设置默认搜索域
		query.set("df", "item_title");
		// 设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		
		SearchResult search = searchDao.search(query);
		long recordCount = search.getRecordCount();
		long pages = recordCount / rows;
		if(recordCount % rows > 0) {
			pages++;
		}
		search.setTotalPages(pages);
		return search;
	}

}
