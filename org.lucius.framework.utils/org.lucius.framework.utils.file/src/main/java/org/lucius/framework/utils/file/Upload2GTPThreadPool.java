/**
 * @(#)Upload2GTPThreadPool.java 1.0 2016-1-14
 * @Copyright:  Copyright 2007 - 2016 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2016-1-14
 * Author:      sunsz
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.file;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Upload2GTPThreadPool {
    
    private static ThreadPoolTaskExecutor poolTaskExecutor = null;
    private static Upload2GTPThreadPool upload2GTPThreadPool = null;
    
    private Upload2GTPThreadPool(){
        
        poolTaskExecutor = new ThreadPoolTaskExecutor();  
        //线程池所使用的缓冲队列  
        poolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);  
        //线程池维护线程的最少数量  
        poolTaskExecutor.setCorePoolSize(5);  
        //线程池维护线程的最大数量  
        poolTaskExecutor.setMaxPoolSize(10);  
      //线程池维护线程所允许的空闲时间  
        poolTaskExecutor.setKeepAliveSeconds(3000);  
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        //线程池维护线程所允许的空闲时间  
        poolTaskExecutor.initialize();  
    }
    
    public static ThreadPoolTaskExecutor getPool(){
        if(upload2GTPThreadPool == null){
            upload2GTPThreadPool = new Upload2GTPThreadPool();
        }
        return poolTaskExecutor;
    }
}

