package ru.clevertec.clevertecTaskRest.controllers.pagination;


import java.util.List;
import java.util.Objects;

public class MyPage<T>{
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private Boolean first;
    private Integer numberOfElements;
    private Boolean last;
    private List<T> content;

    public MyPage(Integer number, Integer size,
                  Integer totalPages, Long totalElements,
                  Boolean first, Integer numberOfElements,
                  Boolean last, List<T> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
        this.content = content;
    }

    public MyPage(Builder<T> builder) {
        this.number = builder.number;
        this.size = builder.size;
        this.totalPages = builder.totalPages;
        this.totalElements = builder.totalElements;
        this.first = builder.first;
        this.numberOfElements = builder.numberOfElements;
        this.last = builder.last;
        this.content = builder.content;
    }

    public MyPage() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public static class Builder<T>{
        private Integer number;
        private Integer size;
        private Integer totalPages;
        private Long totalElements;
        private Boolean first;
        private Integer numberOfElements;
        private Boolean last;
        private List<T> content;

        private Builder() {
        }

        public Builder<T> setNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Builder<T> setSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder<T> setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> setTotalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> setFirst(Boolean first) {
            this.first = first;
            return this;
        }

        public Builder<T> setNumberOfElements(Integer numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public Builder<T> setLast(Boolean last) {
            this.last = last;
            return this;
        }

        public Builder<T> setContent(List<T> content) {
            this.content = content;
            return this;
        }

        public static <T> Builder<T> create(){
            return new Builder<>();
        }

        public MyPage<T> build(){
            return new MyPage<>(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPage<?> myPage = (MyPage<?>) o;
        return Objects.equals(number, myPage.number) && Objects.equals(size, myPage.size) && Objects.equals(totalPages, myPage.totalPages) && Objects.equals(totalElements, myPage.totalElements) && Objects.equals(first, myPage.first) && Objects.equals(numberOfElements, myPage.numberOfElements) && Objects.equals(last, myPage.last) && Objects.equals(content, myPage.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, size, totalPages, totalElements, first, numberOfElements, last, content);
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "number=" + number +
                ", size=" + size +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", first=" + first +
                ", numberOfElements=" + numberOfElements +
                ", last=" + last +
                ", content=" + content +
                '}';
    }
}
