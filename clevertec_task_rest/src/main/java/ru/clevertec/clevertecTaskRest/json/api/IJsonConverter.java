package ru.clevertec.clevertecTaskRest.json.api;

public interface IJsonConverter {

    String writeValueAsString(Object object);

    <T> T readValue(String json, Class<T> destinationType);
}
