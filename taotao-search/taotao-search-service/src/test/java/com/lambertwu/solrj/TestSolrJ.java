package com.lambertwu.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {

	@Test
	public void testAddDocument() throws Exception{
		// 创建一个SolrServer对象
		// 创建一个HttpSolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycore");
		// 创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		// 向文档添加域，必须有id域，域的名称必须再schema.xml
		document.addField("id", "text001");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 1000);
		// 把文档对象写入索引库
		solrServer.add(document);		
		// 提交
		solrServer.commit();
	}
	
	@Test
	public void delettDocumentById() throws Exception{
		// 创建一个SolrServer对象
		// 创建一个HttpSolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycore");
		solrServer.deleteById("text001");	
		// 提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocumentByQuery() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycore");
		solrServer.deleteByQuery("id:123");
	}
	
	
	@Test
	public void searchDocument() throws Exception {
		// 1. 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycore");
		// 2. 创建一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		// 3. 设置查询条件、过滤条件、分页条件、排序条件、高亮
		//solrQuery.set("q", "*:*");
		solrQuery.setQuery("手机");
		solrQuery.setStart(30);
		solrQuery.setRows(20);
		// 设置默认搜索域
		solrQuery.set("df", "item_keywords");
		// 设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		// 4. 执行查询，得到一个Response对象
		QueryResponse response = solrServer.query(solrQuery);
		// 5. 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 6. 取查询结果总记录数
		System.out.println("查询结果总记录数: " + solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			// 取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if(list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println("==================================");
		}
	}
}
