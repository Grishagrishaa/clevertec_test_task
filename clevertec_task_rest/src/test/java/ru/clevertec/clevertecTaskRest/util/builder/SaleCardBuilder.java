package ru.clevertec.clevertecTaskRest.util.builder;

import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;

import java.time.LocalDateTime;

import static ru.clevertec.clevertecTaskRest.util.TestUtils.*;

public class SaleCardBuilder {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long year;
    private Integer salePercentage;

    public SaleCardBuilder() {
        this.id = 1L;
        this.createdDate = LocalDateTime.MIN;
        this.updatedDate = LocalDateTime.MIN;
        this.year = 2022L;
        this.salePercentage = 30;
    }


    public static SaleCardBuilder clone(SaleCard saleCardToClone){
        SaleCardBuilder saleCardBuilder = new SaleCardBuilder();

        saleCardBuilder.setId(saleCardToClone.getId());
        saleCardBuilder.setCreatedDate(saleCardToClone.getCreatedDate());
        saleCardBuilder.setUpdatedDate(saleCardToClone.getUpdatedDate());
        saleCardBuilder.setYear(saleCardToClone.getYear());
        saleCardBuilder.setSalePercentage(saleCardToClone.getSalePercentage());

        return saleCardBuilder;
    }

    public static SaleCardBuilder randomValues(){
        SaleCardBuilder saleCardBuilder = new SaleCardBuilder();

        saleCardBuilder.setId(getRandomLong());
        saleCardBuilder.setCreatedDate(LocalDateTime.now());
        saleCardBuilder.setUpdatedDate(LocalDateTime.now());
        saleCardBuilder.setYear(getRandomLong());
        saleCardBuilder.setSalePercentage(getRandomInt());

        return saleCardBuilder;
    }

    public static ReadSaleCardDto convertToDto(SaleCard saleCardToConvert){
        return ReadSaleCardDto.Builder.create()
                .setId(saleCardToConvert.getId())
                .setYear(saleCardToConvert.getYear())
                .setSalePercentage(saleCardToConvert.getSalePercentage())
                .build();
    }

    public SaleCardBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public SaleCardBuilder setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public SaleCardBuilder setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public SaleCardBuilder setYear(Long year) {
        this.year = year;
        return this;
    }

    public SaleCardBuilder setSalePercentage(Integer salePercentage) {
        this.salePercentage = salePercentage;
        return this;
    }

    public SaleCard build(){
        return SaleCard.Builder.create()
                .setId(id)
                .setYear(year)
                .setSalePercentage(salePercentage)
                .build();
    }
}
