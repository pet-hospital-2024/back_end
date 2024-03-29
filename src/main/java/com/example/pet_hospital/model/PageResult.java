package com.example.pet_hospital.model;

import com.github.pagehelper.Page;

import java.util.List;

public class PageResult<T> {
    private List<T> data; // 当前页的数据
    private long totalRecords; // 总记录数
    private int totalPages; // 总页数
    private int currentPage; // 当前页码
    private int pageSize; // 每页显示的记录数

    // 构造方法
    public PageResult(List<T> data, long totalRecords, int totalPages, int currentPage, int pageSize) {
        this.data = data;
        this.totalRecords = totalRecords;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        System.out.println("PageResult构造方法");
        System.out.println("data: " + data);
    }

    // Getter和Setter方法
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
