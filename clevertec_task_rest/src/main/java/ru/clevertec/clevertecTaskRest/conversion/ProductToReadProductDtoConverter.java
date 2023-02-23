package ru.clevertec.clevertecTaskRest.conversion;

import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToReadProductDtoConverter implements Converter<Product, ReadProductDto> {

    @Override
    public ReadProductDto convert(Product s) {
        return ReadProductDto.Builder.create()
                .setId(s.getId())
                .setName(s.getName())
                .setManufacturer(s.getManufacturer())
                .setWeight(s.getWeight())
                .setExpirationDate(s.getExpirationDate())
                .setCost(s.getCost())
                .setCount(s.getCount())
                .build();
    }
}
