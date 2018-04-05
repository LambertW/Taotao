package com.lambertwu.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestJsonUtils {

	@Test
	public void testJsonUtils() {
		List<String> list = new ArrayList<>();
		list.add("test1");
		list.add("test2");
		
		String test = JsonUtils.objectToJson(list);
		JsonUtils.jsonToList(test, String.class);
		
	}
}
