

package com.blog.controller.common;

public class QueryBlog {
    private String keyword;         //搜索输入的关键字
    private Integer pageNum = 1;    //起始页，默认设为1
    private Integer pageSize = 10;  //每页数据量，默认10

    // getter and setter....


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "QueryBlog{" +
                "keyword='" + keyword + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
