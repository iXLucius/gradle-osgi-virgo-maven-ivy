package org.lucius.framework.model.page;

/**
 * 
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-3-24 下午4:56:02
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: Initialize
 */
public interface Paginable {
    /**
     * 总记录数
     * 
     * @return
     */
    int getTotalCount();

    /**
     * 总页数
     * 
     * @return
     */
    int getTotalPage();

    /**
     * 每页记录数
     * 
     * @return
     */
    int getPageSize();

    /**
     * 当前页号
     * 
     * @return
     */
    int getPageNo();

    /**
     * 是否第一页
     * 
     * @return
     */
    boolean getFirstPage();

    /**
     * 是否最后一页
     * 
     * @return
     */
    boolean getLastPage();

    /**
     * 返回下页的页号
     */
    int getNextPage();

    /**
     * 返回上页的页号
     */
    int getPrePage();
}
