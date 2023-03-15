package ru.clevertec.clevertecTaskRest.json.jsonIO.reader;

import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.api.IReader;
import ru.clevertec.clevertecTaskRest.service.dto.readDto.ReadProductDto;

import java.util.Map;

public class JsonProductDtoReader implements IReader<ReadProductDto> {

    @Override
    public ReadProductDto deserialize(String json) {
        Map<String, String> valuesMap = parseJson(json);

        return ReadProductDto.Builder.create()
                .setId(Long.parseLong(valuesMap.getOrDefault("id", null)))
                .setName(valuesMap.getOrDefault("name", null))
                .setManufacturer(valuesMap.getOrDefault("manufacturer", null))
                .setWeight(Integer.parseInt(valuesMap.getOrDefault("weight", null)))
                .setCost(Double.parseDouble(valuesMap.getOrDefault("cost", null)))
                .setCount(Long.parseLong(valuesMap.getOrDefault("count", null)))
                .build();
    }

    @Override
    public boolean isReadable(Class<?> clazz) {
        return clazz.getSimpleName().equalsIgnoreCase(ReadProductDto.class.getSimpleName());
    }
}
