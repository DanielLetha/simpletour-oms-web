package com.simpletour.company.web.controller.support;

import com.simpletour.common.core.domain.DomainPage;

/**
 * Brief : 用于处理分页的工具类
 * Author: Hawk
 * Date  : 2015/11/26
 */
public class PageHelper {

    private static final Long LIMIT = 3L;

    // 起始页位置
    private Long start;

    // 结束页位置
    private Long end;

    // 只显示多少页
    private Long limit;

    public PageHelper(DomainPage domainPage) {
        long index = domainPage.getPageIndex();
        long count = domainPage.getPageCount();
        start = index - LIMIT;
        if (start <= 0L) {
            start = 1L;
        }
        end = index + LIMIT;
        if (end >= count) {
            end = count;
            if (end == 0) {
                end = start;
            }
        }
        this.limit = LIMIT;

    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
