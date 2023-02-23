package ru.clevertec.clevertecTaskRest.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Entity
@Validated
@Table(name = "product", schema = "shop")
public class Product extends BaseEntity{
    private String name;
    private String manufacturer;
    private LocalDateTime expirationDate;
    private Integer weight;
    private Double cost;
    @PositiveOrZero(message = "PRODUCT IS OUT OF STOCK")
    private Long count;

    public Product() {
    }

    public Product(Builder builder) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getCount() {
        return count;
    }

    public Product setCount(Long count) {
        this.count = count;
        return this;
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
        public Product build(){
            return new Product(this);
        }
    }
}
