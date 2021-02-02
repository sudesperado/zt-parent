package com.zhitong.mytestserver.model;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    //总记录数
    private int totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currentPage;
    //列表数据
    private List<T> data;

    public PageResult(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.data = new ArrayList<>();
    }

    /**
     * 分页
     *
     * @param currentPage       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currentPage   当前页数
     */
    public PageResult(List<T> data, int totalCount, int pageSize, int currentPage) {
        this.data = data;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public PageResult(IPage<T> page) {
        this.data = page.getRecords();
        this.totalCount =  (int)page.getTotal();
        this.pageSize = (int)page.getPages();
        this.currentPage = (int)page.getCurrent();
        this.totalPage = (int)page.getPages();
    }

    /**
     * 分页
     *
     * @param allList       列表总数据
     * @param pageSize   每页记录数
     * @param currentPage   当前页数
     */
    public PageResult(List<T> allList, int pageSize, int currentPage) {
        this.totalCount = allList.size();
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        int fromIndex = (currentPage - 1)*pageSize;
        int toIndex = currentPage*pageSize > totalCount ?  totalCount : currentPage*pageSize;
        if(fromIndex >= totalCount)
        {
            this.data = new ArrayList<>();
        }
        else{
            this.data = allList.subList(fromIndex, toIndex);
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
