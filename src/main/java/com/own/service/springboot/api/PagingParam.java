package com.own.service.springboot.api;

/**
 * Created by Spring on 2017/7/4.
 */
public class PagingParam {
    private int page = 1;
    private int pageSize = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PagingApiResponse.PagingApiResponseBuilder pagingApiResponseBuilder(){
        return PagingApiResponse.PagingApiResponseBuilder.pagingApiResponseBuilder()
                .withPageParam(this);
    }

    public static PagingParam pagingParam(int page, int pageSize){
        PagingParam p=new PagingParam();
        p.setPage(page);
        p.setPageSize(pageSize);
        return p;
    }
}
