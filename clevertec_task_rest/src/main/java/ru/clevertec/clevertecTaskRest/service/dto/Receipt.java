package ru.clevertec.clevertecTaskRest.service.dto;

import java.util.List;

public class Receipt {
    private final List<ReadProductDto> products;
    private final Double totalSum;

    public Receipt(List<ReadProductDto> products, Double totalSum) {
        this.products = products;
        this.totalSum = totalSum;
    }

    public Receipt(Builder builder) {
        this.products = builder.products;
        this.totalSum = builder.totalSum;
    }

    public List<ReadProductDto> getProducts() {
        return products;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public static class Builder{
        private List<ReadProductDto> products;
        private Double totalSum;

        private Builder() {
        }

        public Builder setProducts(List<ReadProductDto> readProductDtos) {
            this.products = readProductDtos;
            return this;
        }

        public Builder setTotalSum(Double totalSum) {
            this.totalSum = totalSum;
            return this;
        }

        public static Builder create(){
            return new Builder();
        }

        public Receipt build(){
            return new Receipt(this);
        }
    }
}
