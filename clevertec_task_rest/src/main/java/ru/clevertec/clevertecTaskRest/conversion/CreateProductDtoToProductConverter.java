package ru.clevertec.clevertecTaskRest.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.dto.createDto.CreateProductDto;

@Component
public class CreateProductDtoToProductConverter implements Converter<CreateProductDto, Product> {
    @Override
    public Product convert(CreateProductDto dto) {
        return Product.Builder.create()
                .setName(dto.getName())
                .setManufacturer(dto.getManufacturer())
                .setCount(dto.getCount())
                .setCost(dto.getCost())
                .setExpirationDate(dto.getExpirationDate())
                .setWeight(dto.getWeight())
                .build();
    }
}
