package ru.clevertec.clevertecTaskRest.json.jsonIO.reader.api;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public interface IReader<T> {
    T deserialize(String json);

    boolean isReadable(Class<?> clazz);

    default Map<String, String> parseJson(String json){
        return Arrays.stream(json.replaceAll("[^a-zA-Z0-9,.:]", "")
                                 .split(","))
                     .map(node -> node.split(":"))
                     .collect(Collectors.toMap(p -> p[0], p -> p[1]));
    }

    default Map<String, String> parseJsonWithList(String json, String listValueName) {
        Map<String, String> valuesMap = Arrays.stream(json.substring(0, json.indexOf(listValueName)).replaceAll("[^a-zA-Z0-9,.:]", "").split(","))
                .map(node -> node.split(":"))
                .collect(Collectors.toMap(p -> p[0], p -> p[1]));
        valuesMap.put(listValueName, json.substring(json.indexOf(listValueName)).substring(json.indexOf(":")));
        return valuesMap;
    }
}
