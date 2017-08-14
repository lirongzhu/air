package com.wing.utils.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OperationLogFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(OperationLogFilter.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String uri = ((HttpServletRequest) request).getRequestURI();
        String query = ((HttpServletRequest) request).getQueryString();
        query = (query == null ? "" : query);
        
        logger.info(uri + "?" + query);
		
        chain.doFilter(request, response); 
	}

	@Override
	public void destroy() {
	}

}
