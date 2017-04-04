/**
 * @(#)IpUtils.java 1.0 2014-4-30
 * @Copyright:  Copyright 2000 - 2014 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2014-4-30
 * Author:      wangjiju 600486
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.common;

import javax.servlet.http.HttpServletRequest;

public class IpUtils
{
    public static String getIpAddr (HttpServletRequest request){
        
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
       } 
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
             ip = request.getHeader("WL-Proxy-Client-IP"); 
         } 
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("HTTP_CLIENT_IP");
         }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
         }
         if("0:0:0:0:0:0:0:1".equals(ip)){
         ip="127.0.0.1"; 
         }
        return ip; 
    }
}

