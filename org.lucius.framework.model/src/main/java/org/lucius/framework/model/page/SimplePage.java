package org.lucius.framework.model.page;

/**
 * 
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-3-24 下午4:55:52
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: Initialize
 */
public class SimplePage implements Paginable {
    
    public static final int DEF_COUNT = 20;
    
    protected int totalCount;
    protected int pageSize = 20;
    protected int pageNo = 1;
    protected PageIndex pageIndex;
    
    public SimplePage() {
        
    }

    /**
     * 构造器
     * 
     * @param pageNo 页码
     * @param pageSize 每页几条数据
     * @param totalCount 总共几条数据
     */
    public SimplePage(int pageNo, int pageSize, int totalCount) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
        adjustPageNo();
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
    public SimplePage(int pageNo, int pageSize, int totalCount, int pageIndexSize) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
        adjustPageNo();
        setPageIndex(pageIndexSize);
    }
    
    /**
     * 检查页码 checkPageNo
     * 
     * @param pageNo pageNo
     * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
     */
    public static int cpn(Integer pageNo) {
        return (pageNo == null || pageNo < 1) ? 1 : pageNo;
    }

    /**
     * 调整页码，使不超过最大页数
     */
    public void adjustPageNo() {
        if (pageNo == 1) {
            return;
        }
        int tp = getTotalPage();
        if (pageNo > tp) {
            pageNo = tp;
        }
    }

    /**
     * 获得页码
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 每页几条数据
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 总共几条数据
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 总共几页
     */
    public int getTotalPage() {
        int totalPage = totalCount / pageSize;
        if (totalPage == 0 || totalCount % pageSize != 0) {
            totalPage++;
        }
        return totalPage;
    }

    /**
     * 是否第一页
     */
    public boolean getFirstPage() {
        return pageNo <= 1;
    }

    /**
     * 是否最后一页
     */
    public boolean getLastPage() {
        return pageNo >= getTotalPage();
    }

    /**
     * 下一页页码
     */
    public int getNextPage() {
        if (getLastPage()) {
            return pageNo;
        } else {
            return pageNo + 1;
        }
    }

    /**
     * 上一页页码
     */
    public int getPrePage() {
        if (getFirstPage()) {
            return pageNo;
        } else {
            return pageNo - 1;
        }
    }

    /**
     * 获取动态页码
     */
    public PageIndex getPageIndex() {
        return pageIndex;
    }
    
    /**
     * 
     * @param totalCount 總條數
     */
    public void setTotalCount(int totalCount) {
        if (totalCount < 0) {
            this.totalCount = 0;
        } else {
            this.totalCount = totalCount;
        }
    }

    /**
     * if pageSize< 1 then pageSize=DEF_COUNT
     * 
     * @param pageSize pageSize
     */
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            this.pageSize = DEF_COUNT;
        } else {
            this.pageSize = pageSize;
        }
    }

    /**
     * if pageNo < 1 then pageNo=1
     * 
     * @param pageNo pageNo
     */
    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }

    /**
     * 獲取索引頁
     * @param pageIndexSize 每頁顯示頁碼數量
     */
    public void setPageIndex(int pageIndexSize) {
        int totalPages = getTotalPage();
        int startIndex = pageNo - (pageIndexSize - 1) / 2;
        int endIndex = pageNo + (pageIndexSize - 1) / 2;
        if (startIndex < 1) {
            startIndex = 1;
            if (totalPages > pageIndexSize) {
                endIndex = pageIndexSize;
            } else {
                endIndex = totalPages;
            }
        }
        if (endIndex > totalPages) {
            endIndex = totalPages;
            if (totalPages > pageIndexSize) {
                startIndex = endIndex - pageIndexSize + 1;
            } else {
                startIndex = 1;
            }
        }
        this.pageIndex = new PageIndex(startIndex, endIndex, pageIndexSize);
    }
}
