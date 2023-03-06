package ru.clevertec.clevertecTaskRest.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Columns;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Validated
@Table(name = "product", schema = "shop")
public class Product extends BaseEntity{
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "INVALID PRODUCT NAME")
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
        setId(builder.id);
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
        private Long id;
        private String name;
        private String manufacturer;
        private LocalDateTime expirationDate;
        private Integer weight;
        private Double cost;
        private Long count;


        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
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


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", expirationDate=" + expirationDate +
                ", weight=" + weight +
                ", cost=" + cost +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(manufacturer, product.manufacturer) && Objects.equals(expirationDate, product.expirationDate) && Objects.equals(weight, product.weight) && Objects.equals(cost, product.cost) && Objects.equals(count, product.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, expirationDate, weight, cost, count);
    }
}
