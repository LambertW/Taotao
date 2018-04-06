package com.lambertwu.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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
	
}
