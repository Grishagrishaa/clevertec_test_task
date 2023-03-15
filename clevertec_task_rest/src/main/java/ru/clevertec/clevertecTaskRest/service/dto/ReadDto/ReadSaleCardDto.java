package ru.clevertec.clevertecTaskRest.service.dto.ReadDto;


import java.time.LocalDateTime;
import java.util.Objects;

public class ReadSaleCardDto {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime updateDate;
    private final Long year;
    private final Integer salePercentage;

    public ReadSaleCardDto(Long id, LocalDateTime createdDate, LocalDateTime updateDate, Long year, Integer salePercentage) {
        this.id = id;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.year = year;
        this.salePercentage = salePercentage;
    }

    public ReadSaleCardDto(Builder builder) {
        this.id = builder.id;
        this.createdDate = builder.createdDate;
        this.updateDate = builder.updateDate;
        this.year = builder.year;
        this.salePercentage = builder.salePercentage;
    }

    public Long getId() {
        return id;
    }


    public Integer getSalePercentage() {
        return salePercentage;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public Long getYear() {
        return year;
    }

    public static class Builder{
        private  Long id;
        private LocalDateTime createdDate;
        private LocalDateTime updateDate;
        private Long year;
        private Integer salePercentage;

        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setUpdateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
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

        public ReadSaleCardDto build(){
            return new ReadSaleCardDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadSaleCardDto that = (ReadSaleCardDto) o;
        return Objects.equals(id, that.id) && Objects.equals(year, that.year) && Objects.equals(salePercentage, that.salePercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, salePercentage);
    }

    @Override
    public String toString() {
        return "ReadSaleCardDto{" +
                "id=" + id +
                ", year=" + year +
                ", salePercentage=" + salePercentage +
                '}';
    }
}
