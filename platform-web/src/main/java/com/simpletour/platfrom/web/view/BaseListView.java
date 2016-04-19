package com.simpletour.platfrom.web.view;

import com.alibaba.fastjson.JSONObject;
import com.simpletour.commons.data.domain.DomainPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/5.
 * Remark:
 */
public class BaseListView<T extends BaseElementView> {

    /**
     * 元素的总个数
     */
    protected long domainTotalCount;

    /**
     * 元素的总页数
     */
    protected long pageCount;

    /**
     * 一页的大小，显示多少条
     */
    protected long pageSize;

    /**
     * 第几页
     */
    protected long pageIndex;
    /**
     * 查询对象query
     */
    protected Object searchObj;

    /**
     * 元素集合
     */
    protected List<T> subViews=new ArrayList<>();


    public BaseListView() {
    }

    public BaseListView(DomainPage page){
        if (page != null){
            this.domainTotalCount = page.getDomainTotalCount();
            this.pageCount = page.getPageCount();
            this.pageIndex = page.getPageIndex();
            this.pageSize = page.getPageSize();
        }

    }
    public BaseListView(DomainPage page,Object searchObj) {
        if (page != null && searchObj != null){
            this.domainTotalCount = page.getDomainTotalCount();
            this.pageCount = page.getPageCount();
            this.pageIndex = page.getPageIndex();
            this.pageSize = page.getPageSize();
            this.searchObj = JSONObject.toJSON(searchObj);
        }
    }

    public long getDomainTotalCount() {
        return domainTotalCount;
    }

    public void setDomainTotalCount(long domainTotalCount) {
        this.domainTotalCount = domainTotalCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Object getSearchObj() {
        return searchObj;
    }

    public void setSearchObj(Object searchObj) {
        this.searchObj = searchObj;
    }

    public List<T> getSubViews() {
        return subViews;
    }

    public void setSubViews(List<T> subViews) {
        this.subViews = subViews;
    }
}
