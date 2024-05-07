package com.example.productservice.model;

public class ResponsePage<T> {
    private final int totalPages;
    private final long totalElements;
    private final int currentPage;
    private final T content;

    private ResponsePage(int totalPages, long totalElements, int currentPage, T content) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.content = content;
    }

    public static <T> ResponsePage<T> of(int totalPages, long totalElements, int currentPage, T content) {
        return new ResponsePage(totalPages, totalElements, currentPage, content);
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public T getContent() {
        return this.content;
    }
}

