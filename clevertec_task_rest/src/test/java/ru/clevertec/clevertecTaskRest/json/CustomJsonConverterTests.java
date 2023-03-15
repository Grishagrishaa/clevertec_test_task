package ru.clevertec.clevertecTaskRest.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;
import ru.clevertec.clevertecTaskRest.json.exceptions.JsonSerializationException;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;
import ru.clevertec.clevertecTaskRest.util.builder.MyPageBuilder;
import ru.clevertec.clevertecTaskRest.util.builder.ReadProductDtoBuilder;
import ru.clevertec.clevertecTaskRest.util.builder.ReceiptBuilder;
import ru.clevertec.clevertecTaskRest.util.builder.SaleCardBuilder;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class CustomJsonConverterTests {

    private final JsonConverter converter;
    private final ObjectMapper objectMapper;

    public CustomJsonConverterTests() {
        converter = new JsonConverter();
        objectMapper = new ObjectMapper();
    }
    @Nested
    class JsonWriteTests{
        @Test
        public void whenGivenObjectIsNotAnnotatedThenExceptionIsThrown() throws JsonSerializationException {
            assertThatExceptionOfType(JsonSerializationException.class)
                    .isThrownBy(() -> converter.writeValueAsString(new Object()));
        }

        @Test
        public void whenGivenObjectIsNullThenExceptionIsThrown() throws JsonSerializationException {

            assertThatExceptionOfType(JsonSerializationException.class)
                    .isThrownBy(() -> converter.writeValueAsString(null));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.json.CustomJsonConverterTests#provideReadProductDto")
        void writeReadProductDtoTest(ReadProductDto productDto) throws JsonProcessingException {
            String objectMapperString = objectMapper.writeValueAsString(productDto);
            String customConverterString = converter.writeValueAsString(productDto);

            assertThat(objectMapperString).isEqualTo(customConverterString);

        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.json.CustomJsonConverterTests#provideReadSaleCardDtos")
        void writeReceiptTest(ReadSaleCardDto saleCardDto) throws JsonProcessingException {

            String objectMapperString = objectMapper.writeValueAsString(saleCardDto);
            String customConverterString = converter.writeValueAsString(saleCardDto);

            assertThat(objectMapperString).isEqualTo(customConverterString);
        }
    }

    @Nested
    class JsonReaderTests{
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.json.CustomJsonConverterTests#provideReceipt")
        void shouldReadReceiptTest(Receipt receipt) throws JsonProcessingException {
            String value = objectMapper.writeValueAsString(receipt);

            assertThat(receipt).isEqualTo(converter.readValue(value, Receipt.class));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.json.CustomJsonConverterTests#provideReadProductDto")
        void shouldReadReadProductDto(ReadProductDto readProductDto) throws JsonProcessingException {
            String value = objectMapper.writeValueAsString(readProductDto);

            assertThat(readProductDto).isEqualTo(converter.readValue(value, ReadProductDto.class));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.json.CustomJsonConverterTests#provideReadSaleCardDtos")
        void shouldReadReadSaleCardDto(ReadSaleCardDto readSaleCardDto) throws JsonProcessingException {
            String value = objectMapper.writeValueAsString(readSaleCardDto);

            assertThat(readSaleCardDto).isEqualTo(converter.readValue(value, ReadSaleCardDto.class));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.json.CustomJsonConverterTests#provideReadProductDtoListAndPageable")
        void shouldReadPageDtosOfReadProductDtoTest(Pageable pageable, List<ReadProductDto> readProductDtoList) throws JsonProcessingException {
            MyPage<ReadProductDto> myPage = MyPageBuilder.fromSpringPage(
                    new PageImpl<>(readProductDtoList, pageable, readProductDtoList.size()));

            String value = objectMapper.writeValueAsString(myPage);

            assertThat(myPage).isEqualTo(converter.readValue(value, MyPage.class));
        }
    }

    static Stream<Arguments> provideReadProductDto(){
        return Stream.of(
                Arguments.of(ReadProductDtoBuilder.randomValues().build()),
                Arguments.of(ReadProductDtoBuilder.randomValues().build()),
                Arguments.of(ReadProductDtoBuilder.randomValues().build()),
                Arguments.of(ReadProductDtoBuilder.randomValues().build())
        );
    }

    static Stream<Arguments> provideReadProductDtoListAndPageable() {
        List<ReadProductDto> productList = provideReadProductDto()
                .map(Arguments::get)
                .map(array -> (ReadProductDto) array[0])
                .toList();
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), productList),
                Arguments.of(PageRequest.of(2, 2), productList),
                Arguments.of(PageRequest.of(3, 1), productList));
    }

    static Stream<Arguments> provideReceipt(){
        return Stream.of(
                Arguments.of(ReceiptBuilder.randomValues().build()),
                Arguments.of(ReceiptBuilder.randomValues().build()),
                Arguments.of(ReceiptBuilder.randomValues().build()),
                Arguments.of(ReceiptBuilder.randomValues().build())
        );
    }

    static Stream<Arguments> provideReadSaleCardDtos(){
        return Stream.of(
                Arguments.of(SaleCardBuilder.convertToDto(SaleCardBuilder.randomValues().build())),
                Arguments.of(SaleCardBuilder.convertToDto(SaleCardBuilder.randomValues().build())),
                Arguments.of(SaleCardBuilder.convertToDto(SaleCardBuilder.randomValues().build())),
                Arguments.of(SaleCardBuilder.convertToDto(SaleCardBuilder.randomValues().build()))
        );
    }

    static Stream<Arguments> provideReadSaleCardDtosListAndPageable(){
        List<ReadSaleCardDto> readSaleCardDtos = provideReadSaleCardDtos()
                .map(Arguments::get)
                .map(array -> (ReadSaleCardDto) array[0])
                .toList();
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), readSaleCardDtos),
                Arguments.of(PageRequest.of(2, 2), readSaleCardDtos),
                Arguments.of(PageRequest.of(3, 1), readSaleCardDtos));
    }
}