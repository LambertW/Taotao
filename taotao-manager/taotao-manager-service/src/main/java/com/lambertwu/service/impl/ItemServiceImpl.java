package com.lambertwu.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.common.utils.IDUtils;
import com.lambertwu.common.utils.JsonUtils;
import com.lambertwu.jedis.JedisClient;
import com.lambertwu.mapper.TbItemDescMapper;
import com.lambertwu.mapper.TbItemMapper;
import com.lambertwu.pojo.TbItem;
import com.lambertwu.pojo.TbItemDesc;
import com.lambertwu.pojo.TbItemExample;
import com.lambertwu.service.ItemService;

/**
 * 商品管理Service
 * 
 * @author wgq19
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name = "itemAddTopic")
	private Destination destination;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${ITEM_EXPIRE}")
	private Integer ITEM_EXPIRE;
	
	@Override
	public TbItem getItemById(long itemId) {
		String key = ITEM_INFO + ":" + itemId + ":BASE";
		try {
			String json = jedisClient.get(key);
			if(StringUtils.isNoneBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		try {
			jedisClient.set(key, JsonUtils.objectToJson(item));
			// 设置过期时间
			jedisClient.expire(key, ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);

		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态， 1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());

		itemMapper.insert(item);

		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDesc.setCreated(new Date());

		itemDescMapper.insert(itemDesc);

		// 发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 发送商品ID
				TextMessage message = session.createTextMessage(itemId + "");
				return message;
			}
		});

		return TaotaoResult.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		String key = ITEM_INFO + ":" + itemId + ":DESC";
		try {
			String json = jedisClient.get(key);
			if(StringUtils.isNoneBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
			// 设置过期时间
			jedisClient.expire(key, ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return itemDesc;
	}

}
