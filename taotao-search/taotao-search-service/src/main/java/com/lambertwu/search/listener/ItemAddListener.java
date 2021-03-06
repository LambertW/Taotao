package com.lambertwu.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambertwu.common.pojo.SearchItem;
import com.lambertwu.search.mapper.SearchItemMapper;

/**
 * 商品添加监听
 * 
 * @author wgq19
 *
 */
public class ItemAddListener implements MessageListener {

	@Autowired
	private SearchItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try {

			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			// 等待事务提交
			Thread.sleep(1000);
			SearchItem searchItem = itemMapper.getItemById(itemId);
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());

			solrServer.add(document);
			solrServer.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
