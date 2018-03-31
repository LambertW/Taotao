package com.lambertwu.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页展示
 * @author wgq19
 *
 */
@Controller
public class IndexController {

	
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}
}
