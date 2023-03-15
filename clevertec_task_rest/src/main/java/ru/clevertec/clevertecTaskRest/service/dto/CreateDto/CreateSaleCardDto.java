package ru.clevertec.clevertecTaskRest.service.dto.CreateDto;


import java.time.LocalDateTime;
import java.util.Objects;

public class CreateSaleCardDto {
    private final Long year;
    private final Integer salePercentage;

    public CreateSaleCardDto(Long year, Integer salePercentage) {
        this.year = year;
        this.salePercentage = salePercentage;
    }

    public CreateSaleCardDto(Builder builder) {
        this.year = builder.year;
        this.salePercentage = builder.salePercentage;
    }

    public Integer getSalePercentage() {
        return salePercentage;
    }

    public Long getYear() {
        return year;
    }

    public static class Builder{
        private Long year;
        private Integer salePercentage;

        private Builder() {
        }

        public Builder setYear(Long number) {
            this.year = number;
            return this;
        }

        public Builder setSalePercentage(Integer salePercentage) {
            this.salePercentage = salePercentage;
            return this;
        }

        public static Builder create(){
            return new Builder();
        }

        public CreateSaleCardDto build(){
            return new CreateSaleCardDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateSaleCardDto that = (CreateSaleCardDto) o;
        return Objects.equals(year, that.year) && Objects.equals(salePercentage, that.salePercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash( year, salePercentage);
    }

    @Override
    public String toString() {
        return "ReadSaleCardDto{" +
                ", year=" + year +
                ", salePercentage=" + salePercentage +
                '}';
    }
}
