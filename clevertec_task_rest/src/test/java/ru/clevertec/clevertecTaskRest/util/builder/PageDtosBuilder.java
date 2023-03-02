package ru.clevertec.clevertecTaskRest.util.builder;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ru.clevertec.clevertecTaskRest.controllers.pagination.PageDtos;

import java.util.Collections;
import java.util.List;

@Setter
@NoArgsConstructor
public class PageDtosBuilder<T> {
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private Boolean first;
    private Integer numberOfElements;
    private Boolean last;
    private List<T> content;

    public PageDtosBuilder(Integer number, Integer size, Integer totalPages, Long totalElements, Boolean first, Integer numberOfElements, Boolean last, List<T> content) {
        this.number = 1;
        this.size = 5;
        this.totalPages = 1;
        this.totalElements = 0L;
        this.first = true;
        this.numberOfElements = 0;
        this.last = true;
        this.content = Collections.emptyList();
    }

    public static <T> PageDtos<T> fromSpringPage(Page<T> springPage){
        return fromSpringPage(springPage, springPage.getContent());
    }

    public static <T> PageDtos<T> fromSpringPage(Page springPage, List<T> content){
        return PageDtos.Builder.<T>create()
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
}
