package com.practices.sergiodelamata.filmsFrontend.paginator;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {
    private String url;
    private Page<T> page;
    private int totalPages;
    private int numElementsPerPage;
    private int actualPage;
    private List<PageItem> pages;
    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<>();
        numElementsPerPage = 5;
        totalPages = page.getTotalPages();
        actualPage = page.getNumber() + 1;
        int from, to;
        if (totalPages <= numElementsPerPage) {
            from = 1;
            to = totalPages;
        } else {
            if (actualPage <= numElementsPerPage / 2) {
                from = 1;
                to = numElementsPerPage;
            } else if (actualPage >= totalPages - numElementsPerPage / 2) {
                from = totalPages - numElementsPerPage + 1;
                to = numElementsPerPage;
            } else {
                from = actualPage - numElementsPerPage / 2;
                to = numElementsPerPage;
            }
        }
        for (int i = 0; i < to; i++) {
            pages.add(new PageItem(from + i, actualPage == from + i));
        }
    }
    public String getUrl() {
        return url;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public int getActualPage() {
        return actualPage;
    }
    public List<PageItem> getPages() {
        return pages;
    }
    public boolean isFirst() {
        return page.isFirst();
    }
    public boolean isLast() {
        return page.isLast();
    }
    public boolean isHasNext() {
        return page.hasNext();
    }
    public boolean isHasPrevious() {
        return page.hasPrevious();
    }

}
