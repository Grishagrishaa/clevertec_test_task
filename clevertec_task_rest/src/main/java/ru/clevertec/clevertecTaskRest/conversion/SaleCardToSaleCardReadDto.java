package ru.clevertec.clevertecTaskRest.conversion;

import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SaleCardToSaleCardReadDto implements Converter<SaleCard, ReadSaleCardDto> {
    @Override
    public ReadSaleCardDto convert(SaleCard s) {
        return ReadSaleCardDto.Builder.create()
                .setId(s.getId())
                .setYear(s.getYear())
                .setSalePercentage(s.getSalePercentage())
                .build();
    }
}
