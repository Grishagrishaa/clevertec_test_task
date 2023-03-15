package ru.clevertec.clevertecTaskRest.conversion;

import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.dto.readDto.ReadSaleCardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SaleCardToReadSaleCardDto implements Converter<SaleCard, ReadSaleCardDto> {
    @Override
    public ReadSaleCardDto convert(SaleCard saleCard) {
        return ReadSaleCardDto.Builder.create()
                .setId(saleCard.getId())
                .setYear(saleCard.getYear())
                .setSalePercentage(saleCard.getSalePercentage())
                .build();
    }
}
