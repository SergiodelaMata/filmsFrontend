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
        this.pages = new ArrayList<PageItem>();
        numElementsPerPage = 5;
        totalPages = page.getTotalPages();
        actualPage = page.getNumber() + 1;
        int desde, hasta;
        if (totalPages <= numElementsPerPage) {
            desde = 1;
            hasta = totalPages;
        } else {
            if (actualPage <= numElementsPerPage / 2) {
                desde = 1;
                hasta = numElementsPerPage;
            } else if (actualPage >= totalPages - numElementsPerPage / 2) {
                desde = totalPages - numElementsPerPage + 1;
                hasta = numElementsPerPage;
            } else {
                desde = actualPage - numElementsPerPage / 2;
                hasta = numElementsPerPage;
            }
        }
        for (int i = 0; i < hasta; i++) {
            pages.add(new PageItem(desde + i, actualPage == desde + i));
        }
    }
    public String getUrl() {
        return url;
    }
    public int gettotalPages() {
        return totalPages;
    }
    public int getactualPage() {
        return actualPage;
    }
    public List<PageItem> getpages() {
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
