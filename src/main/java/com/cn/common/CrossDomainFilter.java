package com.cn.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域支持过滤器，在HTTP响应中增加一些头信息
 * @author songzhili
 * 2016年7月1日上午9:04:29
 */
public class CrossDomainFilter implements Filter {
	
	
		private String allowOrigin;
		private String allowMethods;
		private String allowCredentials;
		private String allowHeaders;
		private String exposeHeaders;
		private String maxAge;

	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        allowOrigin = filterConfig.getInitParameter("allowOrigin");
	        allowMethods = filterConfig.getInitParameter("allowMethods");
	        allowCredentials = filterConfig.getInitParameter("allowCredentials");
	        allowHeaders = filterConfig.getInitParameter("allowHeaders");
	        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
	        maxAge = filterConfig.getInitParameter("maxAge");
	    }

	    /**
	     * 跨域支持配置
	     * @param servletRequest
	     * @param servletResponse
	     * @param filterChain
	     * @throws IOException
	     * @throws ServletException
	     */
	    @Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		request.setCharacterEncoding("UTF-8");
		/*
		 * ());
		 * System.out.println("------------------------------->"+request.getHeader
		 * ("TestKey"));
		 */
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		if(!Utils.isEmpty(allowOrigin)){
			List<String> allowOriginList = Arrays
					.asList(allowOrigin.split(","));
			if (allowOriginList != null && !allowOriginList.isEmpty()) {
				String currentOrigin = request.getHeader("Origin");
				if (allowOriginList.contains(currentOrigin)) {
					response.setHeader("Access-Control-Allow-Origin",
							currentOrigin); // 允许来自currentOrigin域的客户端访问
				}
			}
		}
		if(!Utils.isEmpty(allowMethods)){
			response.setHeader("Access-Control-Allow-Methods", allowMethods); // 允许访问allowMethods请求
		}
		if(!Utils.isEmpty(allowCredentials)){
			response.setHeader("Access-Control-Allow-Credentials",
					allowCredentials);
		}
		if(!Utils.isEmpty(allowHeaders)){
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		}
		if(!Utils.isEmpty(exposeHeaders)){
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		if(!Utils.isEmpty(maxAge)){
			response.setHeader("Access-Control-Max-Age", maxAge); // 请求的结果将缓存maxAge秒
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,TestKey,x-requested-with");
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
