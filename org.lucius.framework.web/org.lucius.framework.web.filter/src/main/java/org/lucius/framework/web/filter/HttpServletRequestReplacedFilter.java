package org.lucius.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(StringUtils.equalsIgnoreCase("application/json", request.getContentType())){
            if(request instanceof HttpServletRequest) {
                requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            }
            if(null == requestWrapper) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //Do nothing
    }

}

