package ru.clevertec.clevertecTaskRest.json.jsonIO.reader;

import ru.clevertec.clevertecTaskRest.json.exceptions.JsonSerializationException;
import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.api.IReader;

import java.util.ArrayList;
import java.util.List;

public class JsonReaderFacade {
    private final List<IReader> readers;

    public JsonReaderFacade() {
        readers = new ArrayList<>();
        readers.add(new JsonProductDtoPageDtosReader());
        readers.add(new JsonSaleCardDtoPageDtosReader());
        readers.add(new JsonProductDtoReader());
        readers.add(new JsonReceiptReader());
        readers.add(new JsonSaleCardDtoReader());
    }

    public <T> T readValue(String json, Class<T> destinationType) {
        if ("null".equals(json)) return null;
        return readers.stream()
                .filter(iReader -> iReader.isReadable(destinationType))
                .findFirst()
                .map(readers -> (T) readers.deserialize(json))
                .orElseThrow(() -> new JsonSerializationException(String.format("Can't read %s", destinationType.getName())));
    }
}
