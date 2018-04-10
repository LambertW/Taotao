package com.lambertwu.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lambertwu.common.pojo.SearchItem;
import com.lambertwu.common.pojo.SearchResult;

@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;

	public SearchResult search(SolrQuery query) throws Exception {
		// 根据query对象进行查询
		QueryResponse response = solrServer.query(query);
		// 获取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 获取查询结果总记录数
		long numFound = solrDocumentList.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		List<SearchItem> searchList = new ArrayList<>();
		// 把查询结果封装到SearchItem对象
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			String image = (String) solrDocument.get("item_image");
			if(StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			item.setImage(image);
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_image"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);

			searchList.add(item);
		}
		// 把结果添加到SearchResult
		result.setItemList(searchList);
		
		return result;
	}

}
