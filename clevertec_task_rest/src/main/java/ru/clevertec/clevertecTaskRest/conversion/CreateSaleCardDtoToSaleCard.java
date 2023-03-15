package ru.clevertec.clevertecTaskRest.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.dto.CreateDto.CreateSaleCardDto;

@Component
public class CreateSaleCardDtoToSaleCard implements Converter<CreateSaleCardDto, SaleCard> {
    @Override
    public SaleCard convert(CreateSaleCardDto dto) {
        return SaleCard.Builder.create()
                .setSalePercentage(dto.getSalePercentage())
                .setYear(dto.getYear())
                .build();
    }
}
