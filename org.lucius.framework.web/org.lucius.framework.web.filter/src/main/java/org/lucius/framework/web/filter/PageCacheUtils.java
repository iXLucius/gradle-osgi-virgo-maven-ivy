/**
 * @(#)PageCacheUtils.java 1.0 2015-8-28
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-8-28
 * Author:      zhonglp
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.web.filter;

import javax.servlet.http.HttpServletResponse;

/**
 * 页面缓存工具类
 * 
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-8-28 上午10:22:08
 * Author:      zhonglp
 * Version:     1.0.0.0
 * Description: Initialize
 */
public class PageCacheUtils {
    
    /**
     * 禁止浏览器缓存页面（该方法为了防止页面在回退前进时出现局部刷新而导致的语种混乱问题）
     * @param response 响应
     */
    public static void setNoCache(HttpServletResponse response){
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache");
    }
}

