package com.movie.database.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private Long total;
    private List<T> records;
    private Long pages;
    private Long size;
    private Long current;

    public PageResult() {
    }

    public PageResult(Long total, List<T> records, Long pages, Long size, Long current) {
        this.total = total;
        this.records = records;
        this.pages = pages;
        this.size = size;
        this.current = current;
    }
}
