package ru.clevertec.clevertecTaskRest.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "SaleCard", schema = "shop")
public class SaleCard extends BaseEntity{
    private Long year;
    private Integer salePercentage;

    public SaleCard() {
    }

    public SaleCard(Builder builder) {
        setId(builder.id);
        this.year = builder.year;
        this.salePercentage = builder.salePercentage;
    }

    public Long getYear() {
        return year;
    }

    public Integer getSalePercentage() {
        return salePercentage;
    }

    public SaleCard setYear(Long year) {
        this.year = year;
        return this;
    }

    public SaleCard setSalePercentage(Integer salePercentage) {
        this.salePercentage = salePercentage;
        return this;
    }

    public static class Builder{
        private Long id;
        private Long year;
        private Integer salePercentage;

        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setYear(Long year) {
            this.year = year;
            return this;
        }

        public Builder setSalePercentage(Integer salePercentage) {
            this.salePercentage = salePercentage;
            return this;
        }

        public static Builder create(){
            return new Builder();
        }

        public SaleCard build(){
            return new SaleCard(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleCard saleCard = (SaleCard) o;
        return Objects.equals(year, saleCard.year) && Objects.equals(salePercentage, saleCard.salePercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, salePercentage);
    }

    @Override
    public String toString() {
        return "SaleCard{" +
                "year=" + year +
                ", salePercentage=" + salePercentage +
                '}';
    }
}
