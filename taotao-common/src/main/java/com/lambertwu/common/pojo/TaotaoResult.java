package com.lambertwu.common.pojo;

import java.awt.List;
import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应结构
 * 
 * @author wgq19
 *
 */
public class TaotaoResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5754166977603277068L;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 业务状态
	 */
	private Integer status;

	/**
	 * 响应消息
	 */
	private String msg;

	/**
	 * 响应数据
	 */
	private Object data;

	public TaotaoResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	/**
	 * Constructor
	 * 
	 * @param status 状态码
	 * @param msg 消息
	 * @param data 数据
	 */
	public TaotaoResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public static TaotaoResult ok(Object data) {
		return new TaotaoResult(data);
	}

	public static TaotaoResult ok() {
		return new TaotaoResult(null);
	}

	public static TaotaoResult build(Integer status, String msg, Object data) {
		return new TaotaoResult(status, msg, data);
	}

	public static TaotaoResult build(Integer status, String msg) {
		return new TaotaoResult(status, msg, null);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 将json结果集转化为taotaoresult对象
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	public static TaotaoResult formaToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, TaotaoResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 没有object对象的转换
	 * @param json
	 * @return
	 */
	public static TaotaoResult format(String json) {
		try {
			return MAPPER.readValue(json, TaotaoResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Object是集合转化
	 * 
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	public static TaotaoResult formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if(data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory().constructCollectionLikeType(List.class, clazz));
			}
			
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
}
