package ru.clevertec.clevertecTaskRest.util.builder;

import org.springframework.data.domain.Page;
import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;

import java.util.Collections;
import java.util.List;

public class MyPageBuilder<T> {
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private Boolean first;
    private Integer numberOfElements;
    private Boolean last;
    private List<T> content;

    public MyPageBuilder(Integer number, Integer size, Integer totalPages, Long totalElements, Boolean first, Integer numberOfElements, Boolean last, List<T> content) {
        this.number = 1;
        this.size = 5;
        this.totalPages = 1;
        this.totalElements = 0L;
        this.first = true;
        this.numberOfElements = 0;
        this.last = true;
        this.content = Collections.emptyList();
    }

    public static <T> MyPage<T> fromSpringPage(Page<T> springPage){
        return fromSpringPage(springPage, springPage.getContent());
    }

    public static <T> MyPage<T> fromSpringPage(Page springPage, List<T> content){
        return MyPage.Builder.<T>create()
                .setContent(content)
                .setFirst(springPage.isFirst())
                .setLast(springPage.isLast())
                .setNumber(springPage.getNumber())
                .setNumberOfElements(springPage.getNumberOfElements())
                .setTotalElements(springPage.getTotalElements())
                .setTotalPages(springPage.getTotalPages())
                .setSize(springPage.getSize())
                .build();
    }

    public MyPageBuilder<T> setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public MyPageBuilder<T> setSize(Integer size) {
        this.size = size;
        return this;
    }

    public MyPageBuilder<T> setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public MyPageBuilder<T> setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public MyPageBuilder<T> setFirst(Boolean first) {
        this.first = first;
        return this;
    }

    public MyPageBuilder<T> setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
        return this;
    }

    public MyPageBuilder<T> setLast(Boolean last) {
        this.last = last;
        return this;
    }

    public MyPageBuilder<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }
}
