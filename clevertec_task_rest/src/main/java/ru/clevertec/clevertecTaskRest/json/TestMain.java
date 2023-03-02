package ru.clevertec.clevertecTaskRest.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.JsonProductDtoReader;
import ru.clevertec.clevertecTaskRest.json.jsonIO.reader.JsonReceiptReader;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;

import java.util.List;

public class TestMain {
    public static void main(String[] args) throws JsonProcessingException {

        JsonConverter jsonConverter = new JsonConverter();
//        String value = jsonConverter.writeValueAsString(ReadProductDto.Builder.create()
//                .setId(4L)
//                .setName("TestProduct4")
//                .setManufacturer("AUDI")
//                .setCost(2.0)
//                .setWeight(11)
//                .setCount(45L)
//                .build());
//        System.out.println(value);
//
        JsonProductDtoReader jsonProductDtoReader = new JsonProductDtoReader();
//
//        System.out.println(jsonReadProductDtoReader.canReadClass(ReadProductDto.class));
//
//        System.out.println(jsonReadProductDtoReader.deserialize(value));
//
//        String valCard = jsonConverter.writeValueAsString(ReadSaleCardDto.Builder.create()
//                .setYear(2021L)
//                .setSalePercentage(10)
//                .setId(2L)
//                .build());
//
//        System.out.println(valCard);
//
//        JsonReadSaleCardDtoReader jsonReadSaleCardDtoReader = new JsonReadSaleCardDtoReader();
//
//        System.out.println(jsonReadSaleCardDtoReader.canReadClass(ReadSaleCardDto.class));
//
//        System.out.println(jsonReadSaleCardDtoReader.deserialize(valCard));

//        JsonReadProductDtoPageDtosReader jsonReadProductDtoPageDtosReader = new JsonReadProductDtoPageDtosReader();
//
//        MyPage<ReadProductDto> page = MyPage.Builder.<ReadProductDto>create()
//                .setContent(List.of(ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build(),
//                        ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build(),
//                        ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build(),
//                        ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build(),
//                        ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build(),
//                        ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build(),
//                        ReadProductDto.Builder.create()
//                                .setId(4L)
//                                .setName("TestProduct4")
//                                .setManufacturer("AUDI")
//                                .setCost(2.0)
//                                .setWeight(11)
//                                .setCount(45L)
//                                .build()))
//                .setNumber(1)
//                .setTotalPages(2)
//                .setSize(10)
//                .setTotalElements(2L)
//                .setLast(true)
//                .setFirst(false)
//                .setNumberOfElements(20)
//                .build();

//        MyPage<ReadSaleCardDto> page = MyPage.Builder.<ReadSaleCardDto>create()
//                .setContent(List.of(
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build(),
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build(),
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build(),
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build(),
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build(),
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build(),
//                                ReadSaleCardDto.Builder.create()
//                                        .setSalePercentage(10)
//                                        .setId(1L)
//                                        .setYear(2022L)
//                                        .build()
//                        ))
//                .setNumber(1)
//                .setTotalPages(2)
//                .setSize(10)
//                .setTotalElements(2L)
//                .setLast(true)
//                .setFirst(false)
//                .setNumberOfElements(20)
//                .build();
//
        ObjectMapper objectMapper = new ObjectMapper();
//
//        System.out.println(objectMapper.writeValueAsString(page));
//
//        System.out.println(new JsonSaleCardDtoPageDtosReader().deserialize(objectMapper.writeValueAsString(page)));

        Receipt build = Receipt.Builder.create()
                .setProducts(List.of(ReadProductDto.Builder.create()
                                .setId(4L)
                                .setName("TestProduct4")
                                .setManufacturer("AUDI")
                                .setCost(2.0)
                                .setWeight(11)
                                .setCount(45L)
                                .build(),
                        ReadProductDto.Builder.create()
                                .setId(4L)
                                .setName("TestProduct4")
                                .setManufacturer("AUDI")
                                .setCost(2.0)
                                .setWeight(11)
                                .setCount(45L)
                                .build(),
                        ReadProductDto.Builder.create()
                                .setId(4L)
                                .setName("TestProduct4")
                                .setManufacturer("AUDI")
                                .setCost(2.0)
                                .setWeight(11)
                                .setCount(45L)
                                .build(),
                        ReadProductDto.Builder.create()
                                .setId(4L)
                                .setName("TestProduct4")
                                .setManufacturer("AUDI")
                                .setCost(2.0)
                                .setWeight(11)
                                .setCount(45L)
                                .build(),
                        ReadProductDto.Builder.create()
                                .setId(4L)
                                .setName("TestProduct4")
                                .setManufacturer("AUDI")
                                .setCost(2.0)
                                .setWeight(11)
                                .setCount(45L)
                                .build()))
                .setTotalSum(21.0)
                .build();

        System.out.println(objectMapper.writeValueAsString(build));



        System.out.println(jsonConverter.readValue(objectMapper.writeValueAsString(build), Receipt.class));

    }
}
