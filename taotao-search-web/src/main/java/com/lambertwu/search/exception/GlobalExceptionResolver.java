package com.lambertwu.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * @author wgq19
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) {
		// 控制台打印异常
		ex.printStackTrace();
		// 向日志文件中写入异常
		logger.error("系统发生异常", ex);
		// 发邮件, jmail
		// 发短信
		// 展示错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "出现异常，请稍后重试。");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
