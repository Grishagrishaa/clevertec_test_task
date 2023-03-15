package ru.clevertec.clevertecTaskRest.json;


import ru.clevertec.clevertecTaskRest.json.api.IJsonConverter;
import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.JsonReaderFacade;
import ru.clevertec.clevertecTaskRest.json.jsonIO.writer.JsonWriter;

public class JsonConverter implements IJsonConverter {
    private final JsonWriter jsonWriter;
    private final JsonReaderFacade jsonReader;

    public JsonConverter() {
        jsonWriter = new JsonWriter();
        jsonReader = new JsonReaderFacade();
    }

    @Override
    public String writeValueAsString(Object object) {
        return jsonWriter.convertToJson(object);
    }

    @Override
    public <T> T readValue(String json, Class<T> destinationType) {
        return jsonReader.readValue(json, destinationType);
    }
}
