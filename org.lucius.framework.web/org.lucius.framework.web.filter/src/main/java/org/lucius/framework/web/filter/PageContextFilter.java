package org.lucius.framework.web.filter;
/**
 * @(#)PageContextFilter.java 1.0 2015-3-24
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-3-24
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 **/
import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.lucius.framework.model.page.PageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

/**
 * 分页基本参数解析过滤器
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-3-24 下午7:58:39
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: Initialize
 */
public class PageContextFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(PageContextFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)res;
        
        //处理.properties文件响应头
        handlePropertiesResource(request, response);
        
        //解析语种信息
        resolveLanguage(request, response);
        //解析分页信息
        resolvePageInfo(request);
        
        //处理部分页面局部刷新导致的语种混乱问题
        handlePageCache(request, response);
        
        chain.doFilter(req, res);
        //显式的释放资源，并非必须的操作，线程结束后会自动清理线程变量，但该操作可以加速内存的回收
        PageContext.remove();
    }

    /**
     * 处理部分页面局部刷新导致的语种混乱问题
     * 
     * @param request 请求
     * @param response 响应
     */
    private void handlePageCache(HttpServletRequest request,
            HttpServletResponse response) {
        String uri = request.getRequestURI().toString();
        if ((uri.indexOf("/navigation") != -1 || uri.indexOf("/metadata") != -1) && uri.indexOf("/resources/") == -1){
            PageCacheUtils.setNoCache(response);
        }
    }
    
    /**
     * 处理.properties文件响应头
     * 
     * @param request 请求
     * @param response 响应
     */
    private void handlePropertiesResource(HttpServletRequest request,
            HttpServletResponse response){
        String uri = request.getRequestURI().toString();
        if(uri.contains(".properties")){
            response.setContentType("text/plain; charset=utf-8");
        }
    }

    /**
     *  解析分页信息
     *  
     * @param request Request请求
     */
    private void resolvePageInfo(HttpServletRequest request) {
        //从request中获取是否需要分页操作表示
        String flag = request.getParameter("_PAGINATION_FLAG_");
        //如果设置类需要分页表示，那么自动解析请求中的分页参数
        if(!StringUtils.isBlank(flag)&&"_PAGINATION_REQUIRED_".equalsIgnoreCase(flag)){
            String pageNo = request.getParameter("pageNo");
            if(NumberUtils.isDigits(pageNo)){
                PageContext.setPageNo(NumberUtils.toInt(pageNo));
            }
            String pageSize = request.getParameter("pageSize");
            if(NumberUtils.isDigits(pageSize)){
                PageContext.setPageSize(NumberUtils.toInt(pageSize));
            }
            String pageIndexSize = request.getParameter("pageIndexSize");
            if(NumberUtils.isDigits(pageIndexSize)){
                PageContext.setPageIndexSize(NumberUtils.toInt(pageIndexSize));
            }
            String orderBy = request.getParameter("orderBy");
            PageContext.setOrderBy(orderBy);
            logger.debug("pageNo = {} , pageSize = {} , pageIndexSize = {} ,orderBy = {}",new Object[]{pageNo,pageSize,pageIndexSize,orderBy});
        }
    }

    /**
     * 解析语种信息
     * 
     * @param request Request请求
     * @param response Response响应
     */
    private void resolveLanguage(HttpServletRequest request,
            HttpServletResponse response) {
        boolean writeToCookie = true;
        Locale locale = new Locale("en","US");//默认英文语种;
        String lang = null;
        String language = request.getParameter("selectedLanguage");
        if(StringUtils.isBlank(lang = checkLanguage(language))){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0 ){
                for (int i = 0; i < cookies.length; i++){
                    Cookie cookie = cookies[i];
                   if("language".equals(cookie.getName( ))){
                       String value = cookie.getValue();
                       language = value;
                       break;
                   }
                }
             } 
            if(StringUtils.isBlank(lang = checkLanguage(language))){
                language = (String) request.getSession().getAttribute("language");
                
                if(StringUtils.isBlank(lang = checkLanguage(language))){
                    language = request.getLocale().toString();
                    
                    if(StringUtils.isBlank(lang = checkLanguage(language))){
                        lang = "en";
                    }
                }
            }else{
                writeToCookie = false;
            }
        }
       if("zh".equals(lang)){
           locale = new Locale("zh","CN");
       }
       
       PageContext.setLocale(locale);
       WebUtils.setSessionAttribute(request,SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
       HttpSession session = request.getSession();
       if(session != null){
           session.setAttribute("language", locale.toString());
       }
       
       request.setAttribute("currentLanguage", StringUtils.upperCase(locale.toString())); 
       if(writeToCookie){
            CookieLocaleResolver cookieLocale = new CookieLocaleResolver();
            cookieLocale.setCookieName("language");
            cookieLocale.setCookieMaxAge(3600 * 180 * 24);
            cookieLocale.setDefaultLocale(new Locale("en","US"));
            cookieLocale.setLocale(request, response, locale);
       }
    }
    
    //检查语种是否有效
    private String checkLanguage(String language){
        if(StringUtils.isBlank(language)){
            return null;
        }
        language = language.toLowerCase();
        if("en".equals(language)){
            return "en";
        }
        if("zh".equals(language)){
            return "zh";
        }
        String[] lp = language.split("_");
        if(lp!=null && StringUtils.isNotBlank(lp[0])){
            String lang = StringUtils.lowerCase(lp[0]);
            if("en".equals(lang)){
                return "en";
            }
            if("zh".equals(lang)){
                return "zh";
            }
        }
        return null;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}