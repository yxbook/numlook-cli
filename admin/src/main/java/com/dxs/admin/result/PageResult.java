package com.dxs.admin.result;

import java.util.List;

/**
 * Created by Mr.Dxs on 2018/7/23.
 */
public class PageResult<T> {

    private List<T> list;

    private Integer totalCount;

    public PageResult() {
    }

    public PageResult(List<T> list, Integer totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
