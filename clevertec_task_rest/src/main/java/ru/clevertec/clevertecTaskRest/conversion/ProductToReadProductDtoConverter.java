package ru.clevertec.clevertecTaskRest.conversion;

import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.dto.readDto.ReadProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToReadProductDtoConverter implements Converter<Product, ReadProductDto> {

    @Override
    public ReadProductDto convert(Product product) {
        return ReadProductDto.Builder.create()
                .setId(product.getId())
                .setName(product.getName())
                .setManufacturer(product.getManufacturer())
                .setWeight(product.getWeight())
                .setExpirationDate(product.getExpirationDate())
                .setCost(product.getCost())
                .setCount(product.getCount())
                .build();
    }
}
