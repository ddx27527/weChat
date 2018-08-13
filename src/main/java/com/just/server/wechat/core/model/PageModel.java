package com.just.server.wechat.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.domain.PageRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:18
 * Description: No Description
 */
public class PageModel extends PageRequest {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    private int page = DEFAULT_PAGE;
    private int size = DEFAULT_SIZE;

    private String orderBy;  // 排序的字段

    private String orderSort; // asc 或 desc

    public PageModel() {
        super(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    public PageModel(int page, int size) {
        super(page, size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    @Override
    public long getOffset() {
        return (this.page-1) * this.size;
    }

    @Override
    public int getPageSize() {
        return this.size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PageModel)) {
            return false;
        }

        PageModel pageModel = (PageModel) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(page, pageModel.page).append(size, pageModel.size).append(orderBy, pageModel.orderBy).append(orderSort, pageModel.orderSort).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(page).append(size).append(orderBy).append(orderSort).toHashCode();
    }
}
