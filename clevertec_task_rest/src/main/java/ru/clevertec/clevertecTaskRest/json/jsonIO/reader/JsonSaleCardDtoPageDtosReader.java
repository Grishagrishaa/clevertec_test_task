package ru.clevertec.clevertecTaskRest.json.jsonIO.reader;

import ru.clevertec.clevertecTaskRest.controllers.pagination.PageDtos;
import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.api.IReader;
import ru.clevertec.clevertecTaskRest.service.dto.readDto.ReadSaleCardDto;

import java.util.Arrays;
import java.util.Map;

public class JsonSaleCardDtoPageDtosReader implements IReader<PageDtos<ReadSaleCardDto>> {
    private final JsonSaleCardDtoReader readSaleCardDtoReader = new JsonSaleCardDtoReader();

    @Override
    public PageDtos<ReadSaleCardDto> deserialize(String json) {
        Map<String, String> valuesMap = parseJsonWithList(json, "content");

        return PageDtos.Builder.<ReadSaleCardDto>create()
                .setContent(valuesMap.get("content") != null ?
                                        Arrays.stream(valuesMap.get("content").replaceAll("[^a-zA-Z0-9,}:]", "").split("},"))
                                              .map(readSaleCardDtoReader::deserialize).toList() :
                            null)
                .setFirst(valuesMap.get("content") != null ? Boolean.parseBoolean(valuesMap.get("first")) : null)
                .setLast(valuesMap.get("last") != null ? Boolean.parseBoolean(valuesMap.get("last")) : null)
                .setNumber(valuesMap.get("number") != null ? Integer.parseInt(valuesMap.get("number")) : null)
                .setNumberOfElements(valuesMap.get("numberOfElements") != null ? Integer.parseInt(valuesMap.get("numberOfElements")) : null)
                .setTotalElements(valuesMap.get("totalElements") != null ? Long.parseLong(valuesMap.get("totalElements")) : null)
                .setTotalPages(valuesMap.get("totalPages") != null ? Integer.parseInt(valuesMap.get("totalPages")) : null)
                .setSize(valuesMap.get("size") != null ? Integer.parseInt(valuesMap.get("size")) : null)
                .build();
    }

    @Override
    public boolean isReadable(Class<?> clazz) {
        return clazz.getSimpleName().equalsIgnoreCase(PageDtos.class.getSimpleName());
    }
}
