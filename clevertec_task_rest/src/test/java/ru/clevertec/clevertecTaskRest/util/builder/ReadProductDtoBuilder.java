package ru.clevertec.clevertecTaskRest.util.builder;

import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;


import static ru.clevertec.clevertecTaskRest.util.TestUtils.*;

public final class ReadProductDtoBuilder {
    private Long id;
    private String name;
    private String manufacturer;
    private Integer weight;
    private Double cost;
    private Long count;

    public ReadProductDtoBuilder() {
        this.id = 100L;
        this.name = "DEFAULT";
        this.manufacturer = "Default";
        this.weight = 100;
        this.cost = 100.0;
        this.count = 1L;
    }

    public static ReadProductDtoBuilder randomValues(){
        ReadProductDtoBuilder readProductDtoBuilder = new ReadProductDtoBuilder();

        readProductDtoBuilder.setId(getRandomLong());
        readProductDtoBuilder.setName(getRandomString());
        readProductDtoBuilder.setManufacturer(getRandomString());
        readProductDtoBuilder.setWeight(getRandomInt());
        readProductDtoBuilder.setCost(getRandomDouble());
        readProductDtoBuilder.setCount(getRandomLong());

        return readProductDtoBuilder;
    }

    public static ReadProductDtoBuilder defaultValues(){
        return new ReadProductDtoBuilder();
    }

    public ReadProductDtoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ReadProductDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ReadProductDtoBuilder setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public ReadProductDtoBuilder setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public ReadProductDtoBuilder setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public ReadProductDtoBuilder setCount(Long count) {
        this.count = count;
        return this;
    }

    public ReadProductDto build(){
        return ReadProductDto.Builder.create()
                .setId(id)
                .setName(name)
                .setManufacturer(manufacturer)
                .setWeight(weight)
                .setCost(cost)
                .setCount(count)
                .build();
    }
}
