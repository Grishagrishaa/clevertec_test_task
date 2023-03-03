package ru.clevertec.clevertecTaskRest.util.builder;

import lombok.Setter;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;

import static ru.clevertec.clevertecTaskRest.util.TestUtils.*;


import java.util.Collections;
import java.util.List;

public class ReceiptBuilder {
    private Double totalSum;
    private List<ReadProductDto> products;

    public ReceiptBuilder() {
        this.totalSum = 100.0;
        this.products = Collections.emptyList();
    }

    public static ReceiptBuilder randomValues(){
        ReceiptBuilder receiptBuilder = new ReceiptBuilder();

        receiptBuilder.setProducts(List.of(ReadProductDtoBuilder.randomValues().build(),
                                           ReadProductDtoBuilder.randomValues().build(),
                                           ReadProductDtoBuilder.randomValues().build(),
                                           ReadProductDtoBuilder.randomValues().build(),
                                           ReadProductDtoBuilder.randomValues().build()));
        receiptBuilder.setTotalSum(getRandomDouble());

        return receiptBuilder;
    }

    public static ReceiptBuilder defaultValues(){
        return new ReceiptBuilder();
    }

    public ReceiptBuilder setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
        return this;
    }

    public ReceiptBuilder setProducts(List<ReadProductDto> products) {
        this.products = products;
        return this;
    }

    public Receipt build(){
        return Receipt.Builder.create()
                .setTotalSum(totalSum)
                .setProductDtos(products)
                .build();
    }
}
