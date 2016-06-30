package com.evanshare.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.NutFilter;

/**
 * 对资源文件进行过滤
 * @author lyh
 *
 */
public class resourceFilter extends NutFilter {
	protected Set<String> prefixs = new HashSet<String>();
	@Override
	public void init(FilterConfig conf) throws ServletException {
		super.init(conf);
        prefixs.add(conf.getServletContext().getContextPath() + "/druid/");
        prefixs.add(conf.getServletContext().getContextPath() + "/rs/");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		 if (req instanceof HttpServletRequest) {
	            String uri = ((HttpServletRequest) req).getRequestURI();
	            for (String prefix : prefixs) {
	                if (uri.startsWith(prefix)) {
	                    chain.doFilter(req, resp);
	                    return;
	                }
	            }
	        }
	        super.doFilter(req, resp, chain);
	}
	
	
	

}
