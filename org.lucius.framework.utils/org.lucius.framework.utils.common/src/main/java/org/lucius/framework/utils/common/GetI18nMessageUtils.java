/**
 * @(#)GetI18nMessageUtils.java 1.0 2013-9-10
 * @Copyright:  Copyright 2000 - 2013 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2013-9-10
 * Author:      xupeng 13208
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class GetI18nMessageUtils
{
    /**
     * 通过key获取message
     * @param request
     * @param key 
     * @return
     */
	public static String getMessage(HttpServletRequest request, String key){
        WebApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        return applicationContext.getMessage(key, null, locale);
    }
}

