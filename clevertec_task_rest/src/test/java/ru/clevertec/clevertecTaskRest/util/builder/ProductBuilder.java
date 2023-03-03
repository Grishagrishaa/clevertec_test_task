package ru.clevertec.clevertecTaskRest.util.builder;

import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;

import java.time.LocalDateTime;

import static ru.clevertec.clevertecTaskRest.util.TestUtils.*;

public class ProductBuilder {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String name;
    private String manufacturer;
    private LocalDateTime expirationDate;
    private Integer weight;
    private Double cost;
    private Long count;

    public ProductBuilder() {
        this.id = 1L;
        this.createdDate = LocalDateTime.MIN;
        this.updatedDate = LocalDateTime.MIN;
        this.name = "DefaultProduct";
        this.manufacturer = "DefaultManufacturer";
        this.expirationDate = LocalDateTime.MAX;
        this.weight = 1000;
        this.cost = 10.00;
        this.count = 10_000L;
    }

    public static ProductBuilder clone(Product productToClone){
        ProductBuilder productBuilder = new ProductBuilder();

        productBuilder.setId(productToClone.getId());
        productBuilder.setCreatedDate(productToClone.getCreatedDate());
        productBuilder.setUpdatedDate(productToClone.getUpdatedDate());
        productBuilder.setName(productToClone.getName());
        productBuilder.setManufacturer(productToClone.getManufacturer());
        productBuilder.setExpirationDate(productToClone.getExpirationDate());
        productBuilder.setWeight(productToClone.getWeight());
        productBuilder.setCost(productToClone.getCost());
        productBuilder.setCount(productToClone.getCount());

        return productBuilder;
    }

    public static ProductBuilder randomValues(){
        ProductBuilder productBuilder = new ProductBuilder();

        productBuilder.setId(getRandomLong());
        productBuilder.setCreatedDate(LocalDateTime.now());
        productBuilder.setUpdatedDate(LocalDateTime.now());
        productBuilder.setName(getRandomString());
        productBuilder.setManufacturer(getRandomString());
        productBuilder.setExpirationDate(LocalDateTime.now());
        productBuilder.setWeight(getRandomInt());
        productBuilder.setCost(getRandomDouble());
        productBuilder.setCount(getRandomLong());

        return productBuilder;
    }

    public static ReadProductDto convertToDto(Product productToConvert){
        return ReadProductDto.Builder.create()
                .setId(productToConvert.getId())
                .setName(productToConvert.getName())
                .setManufacturer(productToConvert.getManufacturer())
                .setCost(productToConvert.getCost())
                .setWeight(productToConvert.getWeight())
                .setExpirationDate(productToConvert.getExpirationDate())
                .setCount(productToConvert.getCount())
                .build();
    }

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public ProductBuilder setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public ProductBuilder setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ProductBuilder setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public ProductBuilder setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public ProductBuilder setCount(Long count) {
        this.count = count;
        return this;
    }

    public Product build(){
        return Product.Builder.create()
                .setId(id)
                .setName(name)
                .setManufacturer(manufacturer)
                .setCost(cost)
                .setWeight(weight)
                .setExpirationDate(expirationDate)
                .setCount(count)
                .build();

    }
}
