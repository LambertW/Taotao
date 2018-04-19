package com.lambertwu.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.lambertwu.item.pojo.Item;
import com.lambertwu.pojo.TbItem;
import com.lambertwu.pojo.TbItemDesc;
import com.lambertwu.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private ItemService itemService;

	@Autowired
	private FreeMarkerConfig freeMarkerConfig;

	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;

	@Override
	public void onMessage(Message message) {

		try {

			TextMessage textMessage = (TextMessage) message;
			String strId = textMessage.getText();
			Long itemId = Long.parseLong(strId);
			
			Thread.sleep(1000);
			
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);

			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);

			Writer out = new FileWriter(new File(HTML_OUT_PATH + strId + ".html"));
			template.process(data, out);
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
