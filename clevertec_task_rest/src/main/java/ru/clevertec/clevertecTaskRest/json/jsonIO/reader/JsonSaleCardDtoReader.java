package ru.clevertec.clevertecTaskRest.json.jsonIO.reader;

import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.api.IReader;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;

import java.util.Map;

public class JsonSaleCardDtoReader implements IReader<ReadSaleCardDto> {

    @Override
    public ReadSaleCardDto deserialize(String json) {
        Map<String, String> valuesMap = parseJson(json);

        return ReadSaleCardDto.Builder.create()
                .setId(Long.parseLong(valuesMap.getOrDefault("id", null)))
                .setSalePercentage(Integer.parseInt(valuesMap.getOrDefault("salePercentage", null)))
                .setYear(Long.parseLong(valuesMap.getOrDefault("year", null)))
                .build();
    }

    @Override
    public boolean isReadable(Class<?> clazz) {
        return clazz.getSimpleName().equalsIgnoreCase(ReadSaleCardDto.class.getSimpleName());
    }
}
