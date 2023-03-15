package ru.clevertec.clevertecTaskRest.json.jsonIO.writer;



import ru.clevertec.clevertecTaskRest.json.annotations.JsonElement;
import ru.clevertec.clevertecTaskRest.json.annotations.JsonSerializable;
import ru.clevertec.clevertecTaskRest.json.exceptions.JsonSerializationException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class JsonWriter {
    public String convertToJson(Object object) throws JsonSerializationException {
        return convertToJson(object, 1);
    }

    private String convertToJson(Object object, int level) throws JsonSerializationException {
        try {
            checkIfSerializable(object);
//            initializeObject(object);
            return getJsonString(object, level);
        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }

    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new JsonSerializationException("Can't serialize a null object");
        }

        Class<?> clazz = object.getClass();

        if (clazz.isPrimitive() || clazz.isArray() || clazz.isEnum()
            || clazz.equals(Byte.class) || clazz.equals(Short.class)
            || clazz.equals(Integer.class) || clazz.equals(Long.class)
            || clazz.equals(Float.class) || clazz.equals(Double.class)
            || clazz.equals(Boolean.class) || clazz.equals(String.class)
            || clazz.equals(Character.class) || Collection.class.isAssignableFrom(clazz)
            || Map.class.isAssignableFrom(clazz) ) {
            return;
        }

        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new JsonSerializationException("The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable");
        }

    }

//    private void initializeObject(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        Class<?> clazz = object.getClass();
//        for (Method method : clazz.getDeclaredMethods()) {
//            if (method.isAnnotationPresent(JsonInit.class)) {
//                method.setAccessible(true);
//                method.invoke(object);
//            }
//        }
//    }

    private String getJsonString(Object object, int level) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        LinkedHashMap<String, Object> jsonElementsMap = new LinkedHashMap<>();
        Set<String> objectFields = new HashSet<>();
        Set<String> arrayFields = new HashSet<>();

        if (Collection.class.isAssignableFrom(object.getClass()) || object.getClass().isArray()) {
            Collection<?> objectList;
            if (object.getClass().isArray()) {
                Object[] objectArray = (Object[]) object;
                objectList = Arrays.asList(objectArray);
            }
            else {
                objectList = (Collection<?>) object;
            }

            String arrayValue = fieldValuesToJsonArray(objectList);
            return "[" + arrayValue + "]";
        }

        if (Map.class.isAssignableFrom(object.getClass())) {
            Map<?, ?> fieldValueMap = (Map<?, ?>) object;
            StringJoiner mapValueBuilder = new StringJoiner(",");

            for (Map.Entry<?, ?> entry : fieldValueMap.entrySet()) {

                if (entry.getValue() == null) {
                    mapValueBuilder.add("\"" + entry.getKey() + "\":null");
                }
                else if (entry.getValue().getClass().equals(String.class) || entry.getValue().getClass().equals(Character.class) || entry.getValue().getClass().isEnum()) {
                    mapValueBuilder.add("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
                }
                else if (entry.getValue().getClass().isAnnotationPresent(JsonSerializable.class) || entry.getValue().getClass().isArray() || Collection.class.isAssignableFrom(entry.getValue().getClass()) || Map.class.isAssignableFrom(entry.getValue().getClass())) {
                    String entryValue = convertToJson(entry.getValue(), 2);
                    mapValueBuilder.add("\"" + entry.getKey() + "\":" + entryValue);
                }
                else {
                    mapValueBuilder.add("\"" + entry.getKey() + "\":" + entry.getValue());
                }
            }
            return "[" + mapValueBuilder + "]";
        }

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonElement.class)) {

                Class<?> fieldClass = field.getType();

                if (!fieldClass.isAnnotationPresent(JsonSerializable.class)) {

                    if (!fieldClass.isPrimitive() && !fieldClass.isArray() && !fieldClass.isEnum()
                        && !fieldClass.equals(Byte.class) && !fieldClass.equals(Short.class)
                        && !fieldClass.equals(Integer.class) && !fieldClass.equals(Long.class)
                        && !fieldClass.equals(Float.class) && !fieldClass.equals(Double.class)
                        && !fieldClass.equals(Boolean.class) && !fieldClass.equals(String.class)
                        && !fieldClass.equals(Character.class) && !Collection.class.isAssignableFrom(fieldClass)
                        && !Map.class.isAssignableFrom(fieldClass) ) {
                        continue;
                    }
                }

                String fieldName = getKey(field);
                Object fieldValue = field.get(object);

                if (!field.getAnnotation(JsonElement.class).required()) {
                    if (fieldValue == null) {
                        continue;
                    }

                    if (fieldValue instanceof Collection) {
                        if (((Collection<?>) fieldValue).isEmpty()) {
                            continue;
                        }
                    }

                    if (fieldValue instanceof Map) {
                        if (((Map<?, ?>) fieldValue).isEmpty()) {
                            continue;
                        }
                    }
                }

                if (field.getType().isArray()) {
                    if(level == 2) {
                        continue;
                    }
                    arrayFields.add(fieldName);
                    List<Object> fieldValueArray = Arrays.asList((Object[]) fieldValue);

                    String arrayValue = fieldValuesToJsonArray(fieldValueArray);

                    fieldValue = "[" + arrayValue + "]";
                }

                if (fieldValue != null && fieldValue.getClass().isAnnotationPresent(JsonSerializable.class)) {
                    if (level == 2) {
                        continue;
                    }
                    objectFields.add(fieldName);
                    fieldValue = convertToJson(fieldValue, 2);
                }

                // handle maps
                if (fieldValue instanceof Map) {
                    Map<?, ?> fieldValueMap = (Map<?, ?>) fieldValue;
                    StringJoiner mapValueBuilder = new StringJoiner(",");

                    for (Map.Entry<?, ?> entry : fieldValueMap.entrySet()) {

                        if (entry.getValue() == null) {
                            mapValueBuilder.add("\"" + entry.getKey() + "\":null");
                        }
                        else if (entry.getValue().getClass().equals(String.class) || entry.getValue().getClass().equals(Character.class) || entry.getValue().getClass().isEnum()) {
                            mapValueBuilder.add("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
                        }
                        else if (entry.getValue().getClass().isAnnotationPresent(JsonSerializable.class) || entry.getValue().getClass().isArray() || Collection.class.isAssignableFrom(entry.getValue().getClass()) || Map.class.isAssignableFrom(entry.getValue().getClass())) {
                            String entryValue = convertToJson(entry.getValue(), 2);
                            mapValueBuilder.add("\"" + entry.getKey() + "\":" + entryValue);
                        }
                        else {
                            mapValueBuilder.add("\"" + entry.getKey() + "\":" + entry.getValue());
                        }
                    }
                    objectFields.add(fieldName);
                    fieldValue = "[" + mapValueBuilder + "]";
                }

                jsonElementsMap.put(fieldName, fieldValue);
            }
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> {
                    if (entry.getValue() == null) {
                        return "\"" + entry.getKey() + "\":null";
                    }
                    if ((entry.getValue().getClass().equals(String.class) || entry.getValue().getClass().isEnum()) && !objectFields.contains(entry.getKey()) && !arrayFields.contains(entry.getKey())) {
                        return "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"";
                    }
                    return "\"" + entry.getKey() + "\":" + entry.getValue();
                })
                .collect(Collectors.joining(","));

        return "{" + jsonString + "}";
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class).key();
        return value.isEmpty() ? field.getName() : value;
    }

    private String fieldValuesToJsonArray(Collection<?> objectList) {
        return objectList.stream()
                .map(item -> {
                    if (item.getClass().equals(String.class) || item.getClass().equals(Character.class) || item.getClass().isEnum()) {
                        return "\"" + item + "\"";
                    }
                    if (item.getClass().isAnnotationPresent(JsonSerializable.class)) {
                        return convertToJson(item, 2);
                    }
                    return String.valueOf(item);
                }).collect(Collectors.joining(","));
    }
}
