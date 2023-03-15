package ru.clevertec.clevertecTaskRest.service.dto;

import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.dto.ReadDto.ReadProductDto;

import java.util.List;
import java.util.Objects;

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

        public Builder setProductDtos(List<ReadProductDto> readProductDtos) {
            this.products = readProductDtos;
            return this;
        }

        public Builder setTotalSum(Double totalSum) {
            this.totalSum = totalSum;
            return this;
        }

        public Builder setTotalSum(Double totalSum, SaleCard saleCard) {
            this.totalSum = totalSum * ((100.0 - saleCard.getSalePercentage())/100);
            return this;
        }

        public static Builder create(){
            return new Builder();
        }

        public Receipt build(){
            return new Receipt(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(products, receipt.products) && Objects.equals(totalSum, receipt.totalSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, totalSum);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "products=" + products +
                ", totalSum=" + totalSum +
                '}';
    }
}
