package org.lucius.framework.model.page;

import java.util.List;

/**
 * 
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-3-24 下午4:55:31
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: Initialize
 */


@SuppressWarnings("serial")
public class Pagination<T> extends SimplePage implements java.io.Serializable,Paginable {

    /**
     * 当前页的数据
     */
    private List<T> list;
    
    public Pagination() {
        
    }

    /**
     * 构造器
     * @param pageNo 页码
     * @param pageSize 每页几条数据
     * @param totalCount 总共几条数据
     */
    public Pagination(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

    /**
     * 构造器
     * 
     * @param pageNo 页码
     * @param pageSize 每页几条数据
     * @param totalCount 总共几条数据           
     * @param pageIndexSize 显示页数
     *            
     */
    public Pagination(int pageNo, int pageSize, int totalCount, int pageIndexSize) {
        super(pageNo, pageSize, totalCount, pageIndexSize);
    }
	
	
    /**
     * 构造器
     * 
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页几条数据
     * @param totalCount
     *            总共几条数据
     * @param list
     *            分页内容
     */
    public Pagination(int pageNo, int pageSize, int totalCount, List<T> list) {
        super(pageNo, pageSize, totalCount);
        this.list = list;
    }

    /**
     * 第一条数据位置
     * 
     * @return
     */
    public int getFirstResult() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 获得分页内容
     * 
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置分页内容
     * 
     * @param list list
     */
    public void setList(List<T> list) {
        this.list = list;
    }
    
}
