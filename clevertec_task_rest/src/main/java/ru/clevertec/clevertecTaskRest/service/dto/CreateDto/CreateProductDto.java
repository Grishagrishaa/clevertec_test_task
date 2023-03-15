package ru.clevertec.clevertecTaskRest.service.dto.CreateDto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateProductDto {
    private final String name;
    private final String manufacturer;
    private final LocalDateTime expirationDate;
    private final Integer weight;
    private final Double cost;
    private final Long count;

    public CreateProductDto(String name, String manufacturer, LocalDateTime expirationDate, Integer weight, Double cost, Long count) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.cost = cost;
        this.count = count;
    }

    public CreateProductDto(Builder builder) {
        this.name = builder.name;
        this.manufacturer = builder.manufacturer;
        this.expirationDate = builder.expirationDate;
        this.weight = builder.weight;
        this.cost = builder.cost;
        this.count = builder.count;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Integer getWeight() {
        return weight;
    }

    public Double getCost() {
        return cost;
    }

    public Long getCount() {
        return count;
    }

    public static class Builder{
        private String name;
        private String manufacturer;
        private LocalDateTime expirationDate;
        private Integer weight;
        private Double cost;
        private Long count;

        private Builder() {
        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder setExpirationDate(LocalDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder setWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public Builder setCost(Double cost) {
            this.cost = cost;
            return this;
        }

        public Builder setCount(Long count) {
            this.count = count;
            return this;
        }

        public static Builder create(){
            return new Builder();
        }
        public CreateProductDto build(){
            return new CreateProductDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateProductDto that = (CreateProductDto) o;
        return Objects.equals(name, that.name) && Objects.equals(manufacturer, that.manufacturer) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(weight, that.weight) && Objects.equals(cost, that.cost) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, expirationDate, weight, cost, count);
    }

    @Override
    public String toString() {
        return "ReadProductDto{" +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", expirationDate=" + expirationDate +
                ", weight=" + weight +
                ", cost=" + cost +
                ", count=" + count +
                '}';
    }
}

