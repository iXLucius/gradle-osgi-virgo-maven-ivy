/**
 * @(#)PageContext.java 1.0 2015-3-20
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-3-20
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.model.page;

import java.util.Locale;

public class PageContext {
    
    /**
     * 页数
     */
    private static ThreadLocal<Integer> pageNo = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return Constant.DEFAULT_INT_PAGENUM;
        };
    };
    
    /**
     * 一页展示几条数据
     */
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return Constant.DEFAULT_INT_PAGESIZE;
        };
    };
    
    /**
     * 每页所显示的页码数量
     */
    private static ThreadLocal<Integer> pageIndexSize = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return Constant.DEFAULT_INT_PAGE_INDEX_SIZE;
        };
    };
    
    /**
     * 语种
     */
    private static ThreadLocal<Locale> _locale = new ThreadLocal<Locale>(){
        protected Locale initialValue() {
            return Locale.getDefault();
        };
    };
    
    public static Locale getLocale(){
        return _locale.get();
    }
    
    public static void setLocale(Locale locale){
        _locale.set(locale);
    }
    
    /**
     * 升降序标识
     */
    private static ThreadLocal<String> orderBy = new ThreadLocal<String>();
    
    public static Integer getPageNo() {
        return pageNo.get();
    }

    public static void setPageNo(Integer _pageNo) {
        pageNo.set(_pageNo);
    }

    public static Integer getPageSize() {
        return pageSize.get();
    }

    public static void setPageSize(Integer _pageSize) {
    	if(_pageSize != null && _pageSize < 0)
    		_pageSize = Integer.MAX_VALUE;
        pageSize.set(_pageSize);
    }

    public static Integer getPageIndexSize() {
        return pageIndexSize.get();
    }
    
    public static void setPageIndexSize(Integer _pageIndexSize) {
        pageIndexSize.set(_pageIndexSize);
    }
    
    public static String getOrderBy() {
        return orderBy.get();
    }

    public static void setOrderBy(String _orderBy) {
        orderBy.set(_orderBy);
    }
    
    /**
     * 释放资源
     */
    public static void remove() {
        pageSize.remove();        
        pageNo.remove();
        orderBy.remove();
        pageIndexSize.remove();
        _locale.remove();
    }
    
}

