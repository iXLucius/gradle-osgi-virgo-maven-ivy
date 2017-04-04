package org.lucius.framework.model.page;

/**
 * 
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-3-24 下午4:55:42
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: Initialize
 */
public class PageIndex {

    /* 开始页码索引 */
    private int startPageIndex;
    /* 结束页码索引 */
    private int endPageIndex;
    /* 每页显示的页码数量 */
    private int perPageCount = 11;

    public PageIndex() {

    }

    public PageIndex(int startPageIndex, int endPageIndex, int perPageCount) {
        this.startPageIndex = startPageIndex;
        this.endPageIndex = endPageIndex;
        this.perPageCount = perPageCount;
    }

    public int getStartPageIndex() {
        return startPageIndex;
    }

    public void setStartPageIndex(int startPageIndex) {
        this.startPageIndex = startPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    public int getPerPageCount() {
        return perPageCount;
    }

    public void setPerPageCount(int perPageCount) {
        this.perPageCount = perPageCount;
    }
}
