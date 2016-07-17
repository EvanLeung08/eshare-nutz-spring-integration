package com.evanshare.entity;

public class PagerModel {
    
    private Integer totalCount;
    private Integer numPerPage;
    private Integer pageNumShown;
    private Integer currentPage;
    private Integer pageSize;
    
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public Integer getNumPerPage() {
        return numPerPage;
    }
    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }
    public Integer getPageNumShown() {
        return pageNumShown;
    }
    public void setPageNumShown(Integer pageNumShown) {
        this.pageNumShown = pageNumShown;
    }
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    
}
