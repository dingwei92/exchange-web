package com.topsci.qh.webmanagement.Resources;

/**
 * Created by lzw.
 * 16-6-24
 */
public class PageInfo {
    private int currentPage=0;
    private int totalPage=0;
    private int totalResult=0;
    private int pageSize=Constants.PUBLIC_PAGE_SIZE;
    private int showPage =1;
    private String url;

    public int getShowPage() {
        return showPage;
    }

    public void setShowPage(int showPage) {
        this.showPage = showPage;
    }

    public void checkShowPage()
    {
        if(this.totalPage == 0 && this.currentPage==0) {
            this.showPage = 0;
        }
        else
        {
            this.showPage = this.currentPage + 1;
        }
    }

    public void checkCurrentPage()
    {
        if(this.currentPage > this.totalPage - 1 && this.totalPage!=0)
        {
            this.currentPage = this.totalPage - 1;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage < 0)
        {
            this.currentPage = 0;
        }
        else if(currentPage > totalPage-1 && totalPage != 0)
        {
            this.currentPage = totalPage-1;
        }
        else {
            this.currentPage = currentPage;
        }

        if(this.totalPage == 0 && this.currentPage==0) {
            this.showPage = 0;
        }
        else
        {
            this.showPage = this.currentPage + 1;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
        if(totalResult == 0)
        {
            this.totalPage = 0;
            this.showPage = 0;
        }
        {
            if (pageSize != 0) {
                int real = totalResult / pageSize;
                if (totalResult % pageSize == 0) {
                    this.totalPage = real;
                } else {
                    this.totalPage = real + 1;
                }
            }
            checkCurrentPage();
            checkShowPage();
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
