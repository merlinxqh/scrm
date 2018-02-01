package com.hiveview.common.api;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 2017/11/3.
 * 分页
 */
public class PageDto<T> implements Serializable{

    private int pageIndex;

    /**
     * 每页显示记录数，即一页要显示多少条记录
     * **/
    private int pageSize;
    /**
     * 总记录数
     * **/
    private int totalCount;
    /**
     * 页码数量，总页数
     * **/
    private int pageCount;
    /**
     * 数据集合
     */
    protected List<T> records;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
