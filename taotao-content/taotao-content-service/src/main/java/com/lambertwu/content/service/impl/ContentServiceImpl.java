package com.lambertwu.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lambertwu.common.pojo.EasyUIDataGridResult;
import com.lambertwu.common.pojo.TaotaoResult;
import com.lambertwu.common.utils.JsonUtils;
import com.lambertwu.content.service.ContentService;
import com.lambertwu.jedis.JedisClient;
import com.lambertwu.mapper.TbContentMapper;
import com.lambertwu.pojo.TbContent;
import com.lambertwu.pojo.TbContentExample;
import com.lambertwu.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;

	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);

		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	@Override
	public TaotaoResult addContent(TbContent content) {

		content.setCreated(new Date());
		content.setUpdated(new Date());

		contentMapper.insert(content);

		// 删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult editContent(TbContent content) {
		content.setUpdated(new Date());

		contentMapper.updateByPrimaryKey(content);

		// 删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContents(List<Long> ids) {
		for (Long id : ids) {
			contentMapper.deleteByPrimaryKey(id);
		}

		// 删除对应的缓存信息
		for (Long id : ids) {
			jedisClient.hdel(INDEX_CONTENT, id.toString());
		}

		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentByCid(long cid) {
		// 查询缓存
		// 添加缓存 不能影响正常业务逻辑
		try {
			String json = jedisClient.hget(INDEX_CONTENT, cid + "");
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 缓存没有命中，查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 查询条件
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);

		// 结果添加到缓存
		try {
			jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
