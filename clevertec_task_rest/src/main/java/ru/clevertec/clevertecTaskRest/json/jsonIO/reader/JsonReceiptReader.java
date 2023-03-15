package ru.clevertec.clevertecTaskRest.json.jsonIO.reader;

import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.api.IReader;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;

import java.util.Arrays;
import java.util.Map;

public class JsonReceiptReader implements IReader<Receipt> {
    private final JsonProductDtoReader readProductDtoReader = new JsonProductDtoReader();

    @Override
    public Receipt deserialize(String json) {
        Map<String, String> valuesMap = parseJsonWithList(json, "products");

        return Receipt.Builder.create()
                .setTotalSum(valuesMap.get("totalSum") != null ? Double.parseDouble(valuesMap.get("totalSum")) : null)
                .setProductDtos(valuesMap.get("products") != null ?
                        Arrays.stream(valuesMap.get("products").replaceAll("[^a-zA-Z0-9,.}:]", "").split("},"))
                                .map(readProductDtoReader::deserialize).toList() :
                        null)
                .build();
    }

    @Override
    public boolean isReadable(Class<?> clazz) {
        return clazz.getSimpleName().equalsIgnoreCase(Receipt.class.getSimpleName());
    }
}
