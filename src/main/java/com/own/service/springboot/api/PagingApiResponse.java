package com.own.service.springboot.api;

import java.util.List;

/**
 * Created by Spring on 2017/7/4.
 */
public class PagingApiResponse {
    private int page=0;
    private int pageSize=10;
    private int total=-1;
    private boolean hasMore=false;
    private List data;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public static class PagingApiResponseBuilder{
        private int page;
        private int pageSize;
        private int total;
        private boolean hasMore;
        private List data;

        private PagingApiResponseBuilder(){}

        public static PagingApiResponseBuilder pagingApiResponseBuilder(){
            return new PagingApiResponseBuilder();
        }

        public PagingApiResponseBuilder withPage(int page){
            this.page=page;
            return this;
        }

        public PagingApiResponseBuilder withPageSize(int pageSize){
            this.pageSize=pageSize;
            return this;
        }

        public PagingApiResponseBuilder withTotal(int total) {
            this.total = total;
            return this;
        }

        public PagingApiResponseBuilder withHasMore(boolean hasMore) {
            this.hasMore = hasMore;
            return this;
        }

        public PagingApiResponseBuilder withData(List data) {
            this.data = data;
            return this;
        }

        public PagingApiResponseBuilder withPageParam(PagingParam pageParam) {
            this.page = pageParam.getPage();
            this.pageSize = pageParam.getPageSize();
            return this;
        }

        public PagingApiResponseBuilder but(){
            return pagingApiResponseBuilder()
                    .withPage(page)
                    .withPageSize(pageSize)
                    .withTotal(total)
                    .withHasMore(hasMore)
                    .withData(data);
        }

        public PagingApiResponse build(){
            PagingApiResponse response=new PagingApiResponse();
            response.setPage(page);
            response.setPageSize(pageSize);
            response.setTotal(total);
            response.setHasMore(hasMore);
            response.setData(data);
            return response;
        }
    }
}
